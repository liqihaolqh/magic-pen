package com.github.magicpen.core.resource.impl

import com.github.magicpen.core.resource.AbstractResourceService
import io.vertx.core.Context
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.MongoClient
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import io.vertx.kotlin.coroutines.awaitResult

class MongoResourceService : AbstractResourceService() {
    private lateinit var mongoClient: MongoClient

    override fun init(vertx: Vertx?, context: Context?) {
        super.init(vertx, context)
        mongoClient = MongoClient.createShared(vertx, json {
            obj(
                "connection_string" to "mongodb://server.juzhen.ltd:31462",
                "db_name" to "magicpen-dev",
                "username" to "root",
                "password" to "JaG3GwWq_TDHAJcHXqGd4MTJ",
                "authSource" to "admin",
                "use_object_id" to false
            )
        })
    }

    override suspend fun findAll(resourceName: String, namespace: String): List<JsonObject> {
        return awaitResult { h -> mongoClient.find(resourceName, JsonObject(), h) }
    }

    override suspend fun findById(resourceName: String, id: String, namespace: String): JsonObject {
        return awaitResult { h -> mongoClient.findOne(resourceName, JsonObject().put("_id", id), JsonObject(), h) }
    }

    override suspend fun save(resourceName: String, id: String, data: JsonObject, namespace: String): String {
        val forSave = data.copy().put("_id", id)
        return awaitResult { h -> mongoClient.save(resourceName, forSave, h) }
    }
}