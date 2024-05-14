package Util;

public class MainCreate {
    public static void main(String[] args) throws RootException {
        String path = System.getenv("FileFolderHolderLocation");
        if (path == null){
            System.exit(1);
        }
        TextFileGenerator.generateFiles(10000);
        DirectoryGenerator.generateDirectories(5000);
        new Shuffler(path);
    }
}
