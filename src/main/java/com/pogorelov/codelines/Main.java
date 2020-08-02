package com.pogorelov.codelines;

import com.pogorelov.codelines.controller.EngineCLI;
import com.pogorelov.codelines.controller.InteractionProvider;
import com.pogorelov.codelines.service.FileNavigator;
import com.pogorelov.codelines.service.LineCounter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info("Application started");
        InteractionProvider interactionProvider = new InteractionProvider(System.in, System.out);
        FileNavigator fileNavigator = new FileNavigator(new LineCounter());
        EngineCLI cli = new EngineCLI(interactionProvider, fileNavigator);
        cli.interactWithClient();
        log.info("Application finished");
    }
}
