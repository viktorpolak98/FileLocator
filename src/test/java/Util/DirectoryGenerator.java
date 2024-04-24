package Util;

import java.io.File;

public class DirectoryGenerator {
    public static void generateDirectories(int numDirectories){
        for (int i = 0; i < numDirectories; i++){
            generateDirectory();
        }
    }

    private static void generateDirectory(){
        String name = System.getenv("FileFolderHolderLocation") + NameGenerator.generateName();
        File file = new File(name);
        try{
            if(!file.mkdir()){
                generateDirectory();
            }
        } catch (SecurityException e){
            e.printStackTrace();
        }
    }
}
