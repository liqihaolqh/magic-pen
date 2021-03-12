package com.github.magicpen.core.resource.impl

import com.github.magicpen.core.resource.AbstractResourceService
import io.vertx.core.json.JsonObject
import java.util.*

/**
 * 数据保存在内存中，便于调试和单元测试。
 */
class InMemoryResourceService : AbstractResourceService() {
    override suspend fun findAll(resourceName: String, namespace: String): List<JsonObject> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(resourceName: String, id: String, namespace: String): JsonObject {
        TODO("Not yet implemented")
    }

    override suspend fun save(resourceName: String, id: String, data: JsonObject, namespace: String): String {
        TODO("Not yet implemented")
    }

}