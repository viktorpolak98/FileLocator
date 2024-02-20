package TestUtil.Util;

import java.io.File;
import java.io.IOException;

public class TextFileGenerator {
    public static void generateFiles(int numFiles){
        for (int i = 0; i < numFiles; i++){
            generateFile();
        }
    }

    private static void generateFile(){
        String name = System.getenv("FileFolderHolderLocation") + NameGenerator.generateName() + ".txt";
        File file = new File(name);
        try{
            if(!file.createNewFile()){
                generateFile();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
