package com.github.magicpen.example;

import com.github.magicpen.core.MagicpenContext;

public class Main {
    public static void main(String[] args) {
        var magicpen = new MagicpenContext();
        magicpen.getVertx().deployVerticle("com.github.magicpen.runner.RunnerVerticle");
        magicpen.init().onSuccess((arg) -> {
            magicpen.executeWorkflow("0b58c38f-e8fc-4b59-87b5-c7ce082537ce");
        }).onFailure((Throwable::printStackTrace));
    }
}
