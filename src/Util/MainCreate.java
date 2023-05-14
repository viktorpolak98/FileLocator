package Util;

public class MainCreate {
    public static void main(String[] args) throws RootException {
//        TextFileGenerator.generateFiles(1);
//        DirectoryGenerator.generateDirectories(1);
        String path = System.getenv("FileFolderHolderLocation");
        new Shuffler(path);

    }
}
