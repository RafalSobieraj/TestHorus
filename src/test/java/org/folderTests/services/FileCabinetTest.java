package org.folderTests.services;


import org.folderTests.interfaces.Folder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class FileCabinetTest {

    FolderService folderService = new FolderService();
    Path rootPath = Paths.get("src/main/resources/tests");
    FileCabinet fileCabinet = new FileCabinet(rootPath);
    List<Folder> folders;

    @BeforeEach
    void setUp() {

        Folder folder = folderService.map(rootPath);
        folders = new ArrayList<>(List.of(Objects.requireNonNull(folder)));
    }

    @Test
    void shouldReturnFolderByName() {

        var result = fileCabinet.findFolderByName("subfolder1");

        assertTrue(result.isPresent());
    }

    @Test
    void shouldReturnEmptyResult() {

        var result = fileCabinet.findFolderByName("test");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldThrowException() {

        assertThrows(RuntimeException.class, () -> {
            FileCabinet test = new FileCabinet(Path.of("testPath"));
            test.findFolderByName("test");
        });

    }

    @Test
    void shouldReturnListWithSmallSizeFolders() {

        var result = fileCabinet.findFoldersBySize("SMALL");

        assertEquals(4, result.size());
    }

    @Test
    void  shouldCountAllFoldersAndFiles() {

        int result = fileCabinet.count();

        assertEquals(13, result);

    }
}