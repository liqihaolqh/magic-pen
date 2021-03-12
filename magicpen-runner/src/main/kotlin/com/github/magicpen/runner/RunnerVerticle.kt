package com.github.magicpen.runner

import com.github.magicpen.api.Action
import com.github.magicpen.api.Param
import io.vertx.core.AbstractVerticle
import io.vertx.core.Promise
import io.vertx.core.json.JsonObject
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaSetter

class RunnerVerticle : AbstractVerticle() {

    override fun start(startPromise: Promise<Void>) {
        vertx.eventBus().consumer<JsonObject>("command.workflow.action.execute:java") {
            val className = it.headers()["actionName"]
            val actionClass = Thread.currentThread().contextClassLoader.loadClass(className).kotlin
            if (!actionClass.isSubclassOf(Action::class)) {
                it.fail(400, "")
                return@consumer
            }

            val body = it.body()
            val instance = actionClass.createInstance() as Action
            actionClass.memberProperties.forEach {
                val annotation = it.findAnnotation<Param>()
                if (annotation != null) {
                    if (it is KMutableProperty<*>) {
                        it.isAccessible = true
                        it.setter.call(instance, body.getValue(annotation.name))
                    }
                }
            }
            instance.execute()

            it.reply("")
        }
    }
}