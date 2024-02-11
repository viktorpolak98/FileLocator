package Interfaces;

import java.io.File;
import java.util.Vector;

public interface IDataStructure {
    IDataStructure build(File rootFile, BuildingCallback callback);

    //TODO: Change to return File(s)
    Vector<File> search(String searchQuery, FileFoundCallback fileFoundCallback);
}
