package com.pogorelov.codelines.controller;

import com.pogorelov.codelines.service.FileNavigator;
import com.pogorelov.codelines.util.ConstantUtils;
import com.pogorelov.codelines.util.ResourceNotFound;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EngineCLI {

    private final InteractionProvider asker;
    private final FileNavigator fileNavigator;

    public EngineCLI(InteractionProvider asker, FileNavigator lineCounter) {
        this.fileNavigator = lineCounter;
        this.asker = asker;
    }

    public void interactWithClient() {
        asker.interact(ConstantUtils.GREETINGS);
        askForInputPathAndProcess();
    }

    private void askForInputPathAndProcess() {
        String path = asker.ask(ConstantUtils.PRINT_PATH);
        try {
            String result = fileNavigator.processPath(path);
            asker.interact(result);
            log.info("For path = {} result output = {}", path, result);
            askIfNewRequestWanted();
        } catch (ResourceNotFound e) {
            asker.interact(String.format(ConstantUtils.FILE_NOT_FOUND, path));
            askForInputPathAndProcess();
        }
    }

    private void askIfNewRequestWanted() {
        String choice = asker.ask(ConstantUtils.NEW_REQUEST_WANTED);
        if (choice.equals("Y")) {
            log.info("User choise = {} must be Y and app should ask for another path", choice);
            askForInputPathAndProcess();
        }
        log.info("User printed = {} and app should finish", choice);
    }

}
