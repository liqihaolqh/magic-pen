package com.github.magicpen.core.workflow

interface Argument {
    fun getValue(context: Context): Any
}