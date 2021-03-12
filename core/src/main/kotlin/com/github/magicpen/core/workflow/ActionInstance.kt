package com.github.magicpen.core.workflow

import io.vertx.core.Vertx
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.eventbus.Message
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import io.vertx.kotlin.coroutines.awaitResult

class ActionInstance(
    private val vertx: Vertx,
    actionName: String,
    private val arguments: Map<String, Argument>
) {
    private val actionName = ActionName(actionName)

    suspend fun execute(context: Context) {
        val params = JsonObject()
        arguments.forEach { (k, v) ->
            params.put(k, v.getValue(context))
        }

        val message = awaitResult<Message<JsonObject>> { h ->
            vertx.eventBus().request(
                "command.workflow.action.execute:${actionName.prefix}",
                params,
                DeliveryOptions().addHeader("actionName", actionName.name),
                h
            )
        }
    }
}

private class ActionName(fullName: String) {
    val prefix: String = fullName.substringBefore(":")
    val name: String = fullName.substringAfter(":")
}