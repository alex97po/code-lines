package com.pogorelov.codelines.service;

import com.pogorelov.codelines.util.ConstantUtils;
import com.pogorelov.codelines.util.ResourceNotFound;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Objects;

@Slf4j
public class FileNavigator {

    private final LineCounter lineCounter;

    public FileNavigator(LineCounter lineCounter) {
        this.lineCounter = lineCounter;
    }

    public String processPath(String path) throws ResourceNotFound {
        File file = new File(path);
        if (!file.exists()) {
            log.error("Invalid input file path: {}", path);
            throw new ResourceNotFound();
        }
        int indent = 0;
        StringBuilder stringBuilder = new StringBuilder();
        processFile(file, indent, stringBuilder);
        return stringBuilder.toString();
    }

    private void processFile(File file, int indent, StringBuilder stringBuilder) {
        if (file.isFile()) {
            printFile(file, indent, stringBuilder);
        } else {
           printDirectoryTree(file, indent, stringBuilder);
        }
    }

    private int printFile(File file, int indent, StringBuilder sb) {
        if (file.getName().endsWith(ConstantUtils.JAVA_FILE_SUFFIX)) {
            int codeLines = lineCounter.countLines(file);
            log.info("File {} considered as java file and code lines counted = {}", file.getName(), codeLines);
            String fileInfo = String.format(ConstantUtils.FILE_COUNT, file.getName(), codeLines);
            sb.append(getIndentString(indent));
            sb.append("\"");
            sb.append(fileInfo);
            sb.append("\"");
            sb.append("\n");
            return codeLines;
        }
        log.info("File {} doesn't have java extension, therefore code counted wasn't invoked", file.getName());
        return 0;
    }

    private int printDirectoryTree(File folder, int indent, StringBuilder sb) {
        File[] files = folder.listFiles();
        int totalCountInFolder = 0;
        StringBuilder folderSb = new StringBuilder();
        StringBuilder fileSb = new StringBuilder();
        if (Objects.nonNull(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    totalCountInFolder += printDirectoryTree(file, indent + 1, folderSb);
                } else {
                    totalCountInFolder += printFile(file, indent + 1, fileSb);
                }
            }
        }
        log.info("Counted code lines in folder {}. Total amount = {}", folder.getName(), totalCountInFolder);
        sb.append(getIndentString(indent));
        sb.append(folder.getName());
        sb.append(" : ");
        sb.append(totalCountInFolder);
        sb.append("\n");
        sb.append(folderSb);
        sb.append(fileSb);
        return totalCountInFolder;
    }

    private String getIndentString(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("|  ");
        }
        return sb.toString();
    }
}
