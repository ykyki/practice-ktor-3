package com.example.subsample;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SampleTest {
    @Test
    void sample_get_foo() {
        Assertions.assertEquals("sub-sample-012345:foo", new Sample().get("foo"));
    }
}
