package Interfaces;

import java.io.File;
@FunctionalInterface
public interface FileFoundCallback {
    void onFileFound(File file);
}