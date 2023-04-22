package Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArrayBuilder {
    public static List<File> buildArray(String rootFilePath){
        List<File> files = new ArrayList<>();
        File file = new File(rootFilePath);

        populateArray(file, files);

        return files;
    }

    private static void populateArray(File rootFile, List<File> files){
        for(File file: Objects.requireNonNull(rootFile.listFiles())){
            files.add(file);
            if (file.isDirectory()){
                populateArray(file, files);
            }
        }
    }
}
