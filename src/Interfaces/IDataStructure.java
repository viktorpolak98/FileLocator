package Interfaces;

import java.io.File;
import java.util.List;

public interface IDataStructure <T>{
    T build(File rootFile, BuildingCallback callback);

    void search(String searchQuery, FileFoundCallback fileFoundCallback);

    List<File> getPreviousSearch();
}
