package com.github.magicpen.core.workflow

import io.vertx.core.AbstractVerticle
import io.vertx.core.json.JsonObject

class EventDispatchVertical : AbstractVerticle() {
    override fun start() {
        vertx.eventBus().consumer<JsonObject>("application.event") {
            val body = it.body()
            val workflowId = body.getString("workflowId")
        }
    }
}