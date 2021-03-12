package com.github.magicpen.core.resource.impl

import io.vertx.core.Vertx
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.impl.logging.LoggerFactory
import io.vertx.core.json.JsonObject
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import io.vertx.kotlin.core.json.array
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(VertxExtension::class)
internal class MongoResourceServiceTest {
    private val logger = LoggerFactory.getLogger(MongoResourceServiceTest::class.java)

    @BeforeEach
    fun deploy_verticle(vertx: Vertx, testContext: VertxTestContext) {
        vertx.deployVerticle(MongoResourceService(), testContext.succeeding { _ -> testContext.completeNow() })
    }

    @Test
    fun save(vertx: Vertx, testContext: VertxTestContext) {
        vertx.eventBus()
            .request<JsonObject>(
                "resource-service",
                json {
                    obj(
                        "displayName" to "测试",
                        "actions" to array(
                            obj(
                                "actionName" to "java:com.github.magicpen.example.PrintAction",
                                "arguments" to obj("value" to "你好呀")
                            )
                        )
                    )
                },
                DeliveryOptions().addHeader("action", "save")
                    .addHeader("resourceName", "workflow")
            ) {
                logger.info(it.result().body())
                testContext.completeNow()
            }
    }

    @Test
    fun find(vertx: Vertx, testContext: VertxTestContext) {
        vertx.eventBus()
            .request<JsonObject>(
                "resource-service", JsonObject(),
                DeliveryOptions().addHeader("action", "find")
                    .addHeader("resourceName", "workflow")
            ) {
                logger.info(it.result().body())
                testContext.completeNow()
            }
    }

    @Test
    fun findById(vertx: Vertx, testContext: VertxTestContext) {
        vertx.eventBus()
            .request<JsonObject>(
                "resource-service", JsonObject().put("id", "2fd9b5e0-3cb6-48b6-81ae-0d207f2bd90d"),
                DeliveryOptions().addHeader("action", "findById")
                    .addHeader("resourceName", "workflow")
            ) {
                logger.info(it.result().body())
                testContext.completeNow()
            }
    }
}