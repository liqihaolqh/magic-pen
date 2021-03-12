package com.github.magicpen.core.workflow

class Workflow(private val actions: List<ActionInstance>) {
    suspend fun execute() {
        val context = Context()
        actions.forEach {
            it.execute(context)
        }
    }
}