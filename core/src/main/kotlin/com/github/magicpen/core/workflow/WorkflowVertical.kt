package com.github.magicpen.core.workflow

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WorkflowVertical : AbstractVerticle() {
    private lateinit var workflowParser: WorkflowParser

    override fun start(startPromise: Promise<Void>) {
        workflowParser = WorkflowParser(vertx)
        vertx.eventBus().consumer<JsonObject>("command.workflow.execute") {
            val workflowId = it.body().getString("id")
            vertx.eventBus()
                .request<JsonObject>(
                    "resource-service", JsonObject().put("id", workflowId),
                    DeliveryOptions().addHeader("action", "findById").addHeader("resourceName", "workflow")
                ) {
                    if (!it.succeeded()) {
                        return@request
                    }
                    val workflow = workflowParser.parse(it.result().body())
                    GlobalScope.launch(vertx.dispatcher()) {
                        workflow.execute()
                    }
                }
        }.completionHandler {
            startPromise.complete()
        }
    }
}