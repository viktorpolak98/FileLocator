package Util;

public class MainCreate {
    public static void main(String[] args) throws RootException {
        TextFileGenerator.generateFiles(300);
        DirectoryGenerator.generateDirectories(100);
//        String path = System.getenv("FileFolderHolderLocation");
//        new Shuffler(path);

    }
}
