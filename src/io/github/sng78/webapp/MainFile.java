package io.github.sng78.webapp;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        File file = new File(".\\src");
        printListFilesAndFolders(file);
    }

    public static void printListFilesAndFolders(File folder) {
        File[] files = folder.listFiles();
        if (files == null) {
            System.out.println("The folder is empty!!!");
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("[" + file.getName() + "]");
                printListFilesAndFolders(file);
            } else {
                System.out.println(file.getName());
            }
        }
    }
}
