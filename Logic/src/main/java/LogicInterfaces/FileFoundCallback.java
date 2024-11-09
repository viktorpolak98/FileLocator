package LogicInterfaces;

import java.io.File;
@FunctionalInterface
public interface FileFoundCallback {
    void onFileFound(File file);
}