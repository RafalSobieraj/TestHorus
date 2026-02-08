package org.folderTests.model;

import org.folderTests.interfaces.Folder;
import org.folderTests.interfaces.MultiFolder;


import java.util.List;

public record MultiFolderImpl(String name, String size, List<Folder> getFolders) implements MultiFolder {


}
