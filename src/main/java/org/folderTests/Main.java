package org.folderTests;

import org.folderTests.services.FileCabinet;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {

        //ścieżka do testowych folderów
        Path path =  Paths.get("src/main/resources/tests");
        FileCabinet fileCabinet = new FileCabinet(path);

        //testowe nazwy
        System.out.println(fileCabinet.findFolderByName("subfolder3").isPresent());
        System.out.println(fileCabinet.findFoldersBySize("LARGE"));
        System.out.println(fileCabinet.count());
    }

}