package com.pogorelov.codelines.controller;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class InteractionProvider {

    private final Scanner scanner;
    private final PrintStream out;

    public InteractionProvider(InputStream in, PrintStream out) {
        scanner = new Scanner(in);
        this.out = out;
    }

    public String ask(String message) {
        out.println(message);
        return scanner.next();
    }

    public void interact(String message) {
        out.println(message);
    }

}
