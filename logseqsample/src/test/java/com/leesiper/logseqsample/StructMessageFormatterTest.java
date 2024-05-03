package com.leesiper.logseqsample;

import com.leesiper.logseqsample.utils.StructMessageFormatter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class StructMessageFormatterTest {
    Map<String, Object> dist = new HashMap<>();

    @Test
    void test_no_argKey() {
        var format = StructMessageFormatter.formatAssign(dist, "Hello {}", new Object[]{"World"});
        assertThat(format).isEqualTo("Hello World");
        assertThat(dist).size().isEqualTo(1);
        assertThat(dist).hasFieldOrPropertyWithValue("prop_6","World");
    }

    @Test
    void test_has_argKey() {
        var format = StructMessageFormatter.formatAssign(dist, "Hello world={}", new Object[]{"World"});
        assertThat(format).isEqualTo("Hello World");
        assertThat(dist).size().isEqualTo(1);
        assertThat(dist).hasFieldOrPropertyWithValue("world","World");
    }

    @Test
    void test_multiply_argKey() {

        var str = StructMessageFormatter.formatAssign(dist, "Hello world={}, Hi lang={}", new Object[]{"World","JAVA"});
        assertThat(str).isEqualTo("Hello World, Hi JAVA");
        assertThat(dist).size().isEqualTo(2);
        assertThat(dist).hasFieldOrPropertyWithValue("world","World");
        assertThat(dist).hasFieldOrPropertyWithValue("lang","JAVA");

    }
    @Test
    void test_no_argKey_and_one_argKey() {

        var str = StructMessageFormatter.formatAssign(dist, "Hello {}, Hi lang={}", new Object[]{"World","JAVA"});
        assertThat(str).isEqualTo("Hello World, Hi JAVA");
        assertThat(dist).size().isEqualTo(2);
        assertThat(dist).hasFieldOrPropertyWithValue("prop_6","World");
        assertThat(dist).hasFieldOrPropertyWithValue("lang","JAVA");

    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Hello world={} lang={}",
            "Hello world={},lang={}",
            "Hello world={}。lang={}",
            "Hello world={}.lang={}"
    })
    void test_delimiter(String tpl) {

        var format = StructMessageFormatter.formatAssign(dist, tpl, new Object[]{"World","JAVA"});
        assertThat(format).startsWith("Hello World");
        assertThat(format).endsWith("JAVA");
        assertThat(dist).size().isEqualTo(2);
        assertThat(dist).hasFieldOrPropertyWithValue("world","World");
        assertThat(dist).hasFieldOrPropertyWithValue("lang","JAVA");

    }
    @Test
    void test_no_arg() {

        var str = StructMessageFormatter.formatAssign(dist, "Hello World", new Object[]{});
        assertThat(str).isEqualTo("Hello World");
        assertThat(dist).size().isEqualTo(0);
    }

}
