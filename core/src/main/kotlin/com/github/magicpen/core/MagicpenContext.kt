package com.github.magicpen.core

import com.github.magicpen.core.workflow.RuntimeVertical
import io.vertx.core.CompositeFuture
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject

class MagicpenContext {
    val vertx = Vertx.vertx()

    fun init():Future<*> {
        val future1 = this.vertx.deployVerticle(RuntimeVertical::class.qualifiedName)
        val future2 = this.vertx.deployVerticle("com.github.magicpen.core.workflow.WorkflowVertical")
        val future3 = this.vertx.deployVerticle("com.github.magicpen.core.resource.impl.MongoResourceService")

        return CompositeFuture.all(future1,future2,future3)
    }

    fun executeWorkflow(id: String) {
        this.vertx.eventBus().send("command.workflow.execute", JsonObject().put("id", id))
    }
}
