package org.folderTests.services;

import org.folderTests.enums.FolderSize;
import org.folderTests.interfaces.Folder;
import org.folderTests.model.MultiFolderImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class FolderService {

    public Folder map(Path path) {

        return new MultiFolderImpl(path.getFileName().toString(), getTotalSize(path), getSubFolders(path));
    }

    public List<Folder> getSubFolders(Path path){
        try (Stream<Path> stream = Files.list(path)){
            return stream.
                    filter(Files::isDirectory)
                    .map(this::map)
                    .filter(Objects::nonNull)
                    .toList();
        }catch (IOException e){
            throw new RuntimeException();
        }
    }

    public String getTotalSize(Path path) {
        try (Stream<Path> stream = Files.walk(path)) {
            return FolderSize.getSizeToString(stream
                    .filter(Files::isRegularFile)
                    .mapToLong(pathSize -> {
                        try {
                            return Files.size(pathSize);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }).sum());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
