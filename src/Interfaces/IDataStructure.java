package Interfaces;

import java.io.File;
import java.util.List;
import java.util.Vector;

public interface IDataStructure <T>{
    T build(File rootFile, BuildingCallback callback);

    void search(String searchQuery, FileFoundCallback fileFoundCallback);

    Vector<File> getPreviousSearch();
}
