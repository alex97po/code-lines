package com.pogorelov.codelines.service;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
public class LineCounter {

    public int countLines(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[fileInputStream.available()];
            fileInputStream.read(buffer);
            log.info("Read file {} into byte array with size {}", file.getName(), buffer.length);
            String content = new String(buffer);
            int codeLines = count(content);
            log.info("Counted code lines for file {}. Number of code lines: {}", file.getName(), codeLines);
            return codeLines;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int count(String content) {
        boolean inString = false;
        boolean inLineComment = false;
        boolean inBlockComment = false;
        boolean emptyLine = true;
        char prevChar = 0;
        char ch;
        int lines = 0;

        for (int i = 0; i < content.length(); i++) {
            ch = content.charAt(i);
            if (inBlockComment) {
                //check for block comment closed
                if (prevChar == '*' && ch == '/') {
                    inBlockComment = false;
                }
            } else {
                if (inString) {
                    //check if string is closed however not with masked (shielded) quotes
                    if (prevChar != '\\' && ch == '"') {
                        inString = false;
                    }
                } else if (!inLineComment) {
                    //check if block or line comment as well as string started or empty line
                    if (prevChar == '/' && ch == '*') {
                        inBlockComment = true;
                    } else if (prevChar == '/' && ch == '/') {
                        inLineComment = true;
                    } else if (ch == '"') {
                        inString = true;
                        emptyLine = false;
                    } else if (!" \t\r\n/" .contains(String.valueOf(ch))) {
                        emptyLine = false;
                    }
                }
                //check if new line sign is not inside the "broken" line
                if (ch == '\n') {
                    if (!emptyLine || prevChar == '/') {
                        lines++;
                        emptyLine = !inString;
                    }
                    inLineComment = false;
                }
            }
            log.debug("prev={}, {}; ch={}, {}; inString={}, inLineComment={}, inBlockComment={}, emptyLine={}; "
                    + "lines={}", prevChar, (int)prevChar, ch, (int)ch, inString, inLineComment, inBlockComment,
                    emptyLine, lines);
            prevChar = ch;
        }
        return lines + (!emptyLine || prevChar == '/' ? 1 : 0);
    }
}
