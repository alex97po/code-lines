package com.pogorelov.codelines;

import com.pogorelov.codelines.service.LineCounter;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.File;

public class LineCounterTest {

    private final LineCounter lineCounter = new LineCounter();

    @Test
    public void test_case1() {
        File file = new File("src\\test\\resources\\test-directory\\TestClass1.java");
        int result = lineCounter.countLines(file);
        Assertions.assertThat(result).isEqualTo(5);
    }


    @Test
    public void test_case2() {
        File file = new File("src\\test\\resources\\test-directory\\subdirectory\\TestClass2.java");
        int result = lineCounter.countLines(file);
        Assertions.assertThat(result).isEqualTo(3);
    }
}
