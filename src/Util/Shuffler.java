package Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class Shuffler {
    private final File root;
    Random random = new Random();
    private final int NUM_DIRECTORIES;

    public Shuffler(String path) throws RootException {
        root = new File(path);
        checkRootPath();
        ArrayList<File> folders = getArrayListOfFolders();
        NUM_DIRECTORIES = folders.size();
        moveFileToFolder(folders);
        assignFolder(folders);
    }

    private void moveFileToFolder(ArrayList<File> folders) {
        ArrayList<File> files = getArrayListOfFiles();
        for (File file : files) {
            int folderIndex = random.nextInt(NUM_DIRECTORIES);
            String path = folders.get(folderIndex).getAbsolutePath();
            path += "/";
            path += file.getName();
            file.renameTo(new File(path));
        }
    }

    private void assignFolder(ArrayList<File> folders){
        for(int i = NUM_DIRECTORIES - 1; i > 0; i--){
            int index = random.nextInt(i);
            String path = folders.get(index).getAbsolutePath();
            path += "/";
            path += folders.get(i).getName();
            folders.get(i).renameTo(new File(path));

        }
    }

    private ArrayList<File> getArrayListOfFiles(){
        ArrayList<File> files = new ArrayList<>();
        for (File file : Objects.requireNonNull(root.listFiles())){
            if (!file.isFile()){
                continue;
            }
            files.add(file);
        }

        return files;
    }


    private ArrayList<File> getArrayListOfFolders(){
        ArrayList<File> folders = new ArrayList<>();

        for (File file : Objects.requireNonNull(root.listFiles())){
            if (file.isDirectory()){
                folders.add(file);
            }
        }
        return folders;
    }

    private void checkRootPath() throws RootException {
        try{
            checkFileExists();
            checkFileIsDirectory();
        } catch (RootException e){
            throw new RootException(e.getMessage());
        }

    }

    private void checkFileExists() throws RootException {
        if (!root.exists()){
            throw new RootException("The path: " + root.getAbsolutePath() + " does not exist\n");
        }
    }

    private void checkFileIsDirectory() throws RootException {
        if (!root.isDirectory()){
            throw new RootException("The path: " + root.getAbsolutePath() + " is not a directory\n");
        }
    }
}
