package org.folderTests.services;

import org.folderTests.interfaces.Cabinet;
import org.folderTests.interfaces.Folder;
import org.folderTests.model.MultiFolderImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class FileCabinet implements Cabinet {

    private final Path rootPath;
    private final List<Folder> folders;


    public FileCabinet(Path rootPath) {
        this.rootPath = rootPath;
        FolderService folderService = new FolderService();

        //tworzenie listy zawierającą foldery
        Folder root = folderService.map(rootPath);

        this.folders = new ArrayList<>(List.of(Objects.requireNonNull(root)));
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        return folders.stream()
                .flatMap(this::getAllFolders)
                .filter(folder -> folder.name().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {

        return folders.stream()
                .flatMap(this::getAllFolders)
                .filter(folder -> folder.size().equalsIgnoreCase(size))
                .toList();
    }

    @Override
    public int count(){
        try (Stream<Path> stream = Files.walk(rootPath)){
            return Math.toIntExact(stream
                    .skip(1)
                    .count());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Stream<Folder> getAllFolders(Folder folder) {
            if (folder instanceof MultiFolderImpl multiFolder){
                return Stream.concat(
                        Stream.of(folder),
                        multiFolder.getFolders().stream()
                                .flatMap(this::getAllFolders)
                );
            }
            return Stream.of(folder);
    }
}