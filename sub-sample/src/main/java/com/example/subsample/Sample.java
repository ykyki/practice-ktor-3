package com.example.subsample;

import org.jetbrains.annotations.NotNull;

public final class Sample {
    private final static String PREFIX = "sub-sample-012345:";

    @NotNull
    public String get(String input) {
        return PREFIX + input;
    }
}
