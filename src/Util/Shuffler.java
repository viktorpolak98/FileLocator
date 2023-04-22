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
            try {
                Files.move(file.toPath(), folders.get(folderIndex).toPath(), StandardCopyOption.ATOMIC_MOVE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void assignFolder(ArrayList<File> folders){
        for (int i = 0; i < NUM_DIRECTORIES; i++){
            int folderSize = folders.size();
            int index = random.nextInt(folderSize + 1) - 1;
            if (index == i){
                index++;
            }
            moveFolder(folders.get(i).toPath(), folders.get(index).toPath());
            folders.remove(i);
        }
    }

    private void moveFolder(Path source, Path target){
        try {
            Files.move(source, target, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<File> getArrayListOfFiles(){
        ArrayList<File> files = new ArrayList<>();
        for (File file : Objects.requireNonNull(root.listFiles())){
            if (file.isFile()){
                files.add(file);
            }
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
