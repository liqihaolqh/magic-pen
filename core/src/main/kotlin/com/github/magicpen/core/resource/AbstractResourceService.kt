package com.github.magicpen.core.resource

import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

private const val DEFAULT_NAMESPACE = "default"

abstract class AbstractResourceService : AbstractVerticle() {
    protected var readOnly = false

    override fun start(startPromise: Promise<Void>) {
        vertx.eventBus().consumer<JsonObject>("resource-service") {
            GlobalScope.coroutineContext
            val resourceName = it.headers()["resourceName"]
            val namespace = it.headers()["namespace"] ?: DEFAULT_NAMESPACE
            GlobalScope.launch(vertx.dispatcher()) {
                when (it.headers()["action"]) {
                    "find" -> {
                        it.reply(JsonArray(findAll(resourceName, namespace)))
                    }
                    "findById" -> {
                        val id = it.body().getString("id")
                        it.reply(findById(resourceName, id, namespace))
                    }
                    "save" -> {
                        val id = it.body().getString("id") ?: UUID.randomUUID().toString()
                        it.reply(json {
                            obj("id" to save(resourceName, id, it.body(), namespace))
                        })
                    }
                }
            }
        }.completionHandler {
            startPromise.complete()
        }
    }

    protected abstract suspend fun findAll(
        resourceName: String,
        namespace: String = DEFAULT_NAMESPACE
    ): List<JsonObject>

    protected abstract suspend fun findById(
        resourceName: String,
        id: String,
        namespace: String = DEFAULT_NAMESPACE
    ): JsonObject

    protected abstract suspend fun save(
        resourceName: String,
        id: String,
        data: JsonObject,
        namespace: String = DEFAULT_NAMESPACE
    ): String
}
