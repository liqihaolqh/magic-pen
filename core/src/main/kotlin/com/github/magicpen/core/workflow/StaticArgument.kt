package com.github.magicpen.core.workflow

class StaticArgument(private val value: Any) : Argument {
    override fun getValue(context: Context): Any = value
}