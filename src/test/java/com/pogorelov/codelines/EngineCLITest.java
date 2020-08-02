package com.pogorelov.codelines;

import com.pogorelov.codelines.controller.EngineCLI;
import com.pogorelov.codelines.controller.InteractionProvider;
import com.pogorelov.codelines.service.FileNavigator;
import com.pogorelov.codelines.util.ResourceNotFound;
import org.junit.Test;
import org.mockito.Mockito;

import static com.pogorelov.codelines.util.ConstantUtils.FILE_NOT_FOUND;
import static com.pogorelov.codelines.util.ConstantUtils.NEW_REQUEST_WANTED;
import static com.pogorelov.codelines.util.ConstantUtils.PRINT_PATH;

public class EngineCLITest {

    private final FileNavigator fileNavigator = Mockito.mock(FileNavigator.class);
    private final InteractionProvider interactionProvider = Mockito.mock(InteractionProvider.class);
    private final EngineCLI engineCLI = new EngineCLI(interactionProvider, fileNavigator);

    @Test
    public void test_shouldAskToRetypePath() {
        String incorrectPath = "incorrectPath";
        String correctPath = "correctPath";
        String expectedMessage = String.format(FILE_NOT_FOUND, incorrectPath);

        Mockito.when(interactionProvider.ask(PRINT_PATH)).thenReturn(incorrectPath).thenReturn(correctPath);
        Mockito.when(interactionProvider.ask(NEW_REQUEST_WANTED)).thenReturn("N");
        Mockito.doThrow(ResourceNotFound.class).when(fileNavigator).processPath(incorrectPath);

        engineCLI.interactWithClient();

        Mockito.verify(interactionProvider).interact(expectedMessage);
    }

    @Test
    public void test_shouldRepeatIfUserRequested() {
        Mockito.when(interactionProvider.ask(PRINT_PATH)).thenReturn("test").thenReturn("test");
        Mockito.when(interactionProvider.ask(NEW_REQUEST_WANTED)).thenReturn("Y").thenReturn("N");

        engineCLI.interactWithClient();

        Mockito.verify(interactionProvider, Mockito.times(2)).ask(PRINT_PATH);
        Mockito.verify(interactionProvider, Mockito.times(2)).ask(NEW_REQUEST_WANTED);
    }

}
