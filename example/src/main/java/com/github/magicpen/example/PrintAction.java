package com.github.magicpen.example;

import com.github.magicpen.api.Action;
import com.github.magicpen.api.Param;

public class PrintAction implements Action {
    @Param(name = "value")
    private String value;

    @Override
    public void execute() {
        System.out.println(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
