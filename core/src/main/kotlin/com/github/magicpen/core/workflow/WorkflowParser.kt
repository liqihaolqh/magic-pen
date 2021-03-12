package com.github.magicpen.core.workflow

import io.vertx.core.Vertx
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject

class WorkflowParser(private val vertx: Vertx) {
    fun parse(data: JsonObject): Workflow {
        val actions = data.getJsonArray("actions", JsonArray()).map {
            it as JsonObject
            val actionName = it.getString("actionName")
            val arguments: MutableMap<String, Argument> = mutableMapOf()
            it.getJsonObject("arguments").forEach { (k, v) ->
                arguments[k] = StaticArgument(v)
            }
            ActionInstance(vertx, actionName, arguments)
        }
        return Workflow(actions)
    }
}