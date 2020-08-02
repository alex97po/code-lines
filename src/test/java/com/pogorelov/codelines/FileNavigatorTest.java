package com.pogorelov.codelines;

import com.pogorelov.codelines.service.FileNavigator;
import com.pogorelov.codelines.service.LineCounter;
import com.pogorelov.codelines.util.ResourceNotFound;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

public class FileNavigatorTest {

    private final LineCounter lineCounter = Mockito.mock(LineCounter.class);
    private final FileNavigator fileNavigator = new FileNavigator(lineCounter);

    @Test
    public void test_notJavaFilesOmitted() {
        Mockito.when(lineCounter.countLines(any())).thenReturn(1);
        String result = fileNavigator.processPath("src\\test\\resources\\test-directory");
        Assertions.assertThat(result).isNotBlank();
        Assertions.assertThat(result).doesNotContain("test.txt");
    }

    @Test
    public void test_subdirectoriesPresentInOutput() {
        Mockito.when(lineCounter.countLines(any())).thenReturn(1);
        String output = fileNavigator.processPath("src\\test\\resources\\test-directory");
        Assertions.assertThat(output).isNotBlank();
        Assertions.assertThat(output).contains("subdirectory");
    }

    @Test
    public void test_allJavaClassesProcessed() {
        Mockito.when(lineCounter.countLines(any())).thenReturn(1);
        String output = fileNavigator.processPath("src\\test\\resources\\test-directory");
        Assertions.assertThat(output).isNotBlank();
        Assertions.assertThat(output).contains("TestClass1.java : 1");
        Assertions.assertThat(output).contains("TestClass2.java : 1");
    }

    @Test(expected = ResourceNotFound.class)
    public void test_throwExceptionOnInvalidPath() {
        Mockito.when(lineCounter.countLines(any())).thenReturn(1);
        fileNavigator.processPath("invalidPath");
        Mockito.verifyNoInteractions(lineCounter);
    }

    @Test
    public void test_totalCountForDirectoryCalculated() {
        Mockito.when(lineCounter.countLines(any())).thenReturn(1);
        String output = fileNavigator.processPath("src\\test\\resources\\test-directory");
        Assertions.assertThat(output).isNotBlank();
        Assertions.assertThat(output).contains("subdirectory : 1");
        Assertions.assertThat(output).contains("test-directory : 2");
    }

}
