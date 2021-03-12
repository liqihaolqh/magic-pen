package com.github.magicpen.core.workflow

import io.vertx.core.AbstractVerticle

class RuntimeVertical : AbstractVerticle() {
    override fun start() {
        vertx.deployVerticle(EventDispatchVertical::class.qualifiedName)
    }
}