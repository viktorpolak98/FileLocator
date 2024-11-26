package LogicInterfaces;

import java.io.File;
import java.util.Vector;

public interface IDataStructure {
    File getRoot();
    void build(File rootFile, BuildingCallback callback);
    Vector<File> search(String searchQuery, FileFoundCallback fileFoundCallback);
    boolean isDone();
}
