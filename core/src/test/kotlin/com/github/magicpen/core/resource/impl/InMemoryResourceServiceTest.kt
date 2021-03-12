package com.github.magicpen.core.resource.impl

import io.vertx.core.Vertx
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(VertxExtension::class)
internal class InMemoryResourceServiceTest {
    @BeforeEach
    fun deploy_verticle(vertx: Vertx, testContext: VertxTestContext) {
        vertx.deployVerticle(InMemoryResourceService(), testContext.succeeding { _ -> testContext.completeNow() })
    }

    @Test
    fun verticle_deployed(vertx: Vertx, testContext: VertxTestContext) {
        val query = """
            query {
                workflow{
                    displayName
                }
            }
        """.trimIndent()
        vertx.eventBus().request<String>("resource-service", query) {
            println(it.result())
            testContext.completeNow()
        }
    }
}