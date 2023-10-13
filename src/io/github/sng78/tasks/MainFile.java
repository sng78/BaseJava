package io.github.sng78.tasks;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        File path = new File("./src");
        System.out.println("[" + path.getName() + "]");
        printListFilesAndFolders(path, "");
    }

    public static void printListFilesAndFolders(File folder, String indent) {
        File[] files = folder.listFiles();
        if (files == null) {
            System.out.println("The folder is empty!!!");
            return;
        }
        indent += "\t";
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println(indent + "[" + file.getName() + "]");
                printListFilesAndFolders(file, indent);
            } else {
                System.out.println(indent + file.getName());
            }
        }
    }
}
