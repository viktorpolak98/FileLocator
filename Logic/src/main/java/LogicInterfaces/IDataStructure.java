package LogicInterfaces;

import java.io.File;
import java.util.Vector;

public interface IDataStructure {
    File getRoot();
    void build(File rootFile, BuildingCallback callback);
    void search(String searchQuery, FileFoundCallback fileFoundCallback);
    Vector<File> getLastSearch();
    boolean isDone();
}
