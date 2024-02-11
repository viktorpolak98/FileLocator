package Interfaces;

import java.io.File;
import java.util.Vector;

public interface IDataStructure {
    IDataStructure build(File rootFile, BuildingCallback callback);

    Vector<File> search(String searchQuery, FileFoundCallback fileFoundCallback);
}
