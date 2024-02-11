package Structures;

import Interfaces.BuildingCallback;
import Interfaces.FileFoundCallback;
import Interfaces.IDataStructure;

import java.io.File;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ArrayStructure implements IDataStructure {
    private ThreadPoolExecutor executor;
    private int threadCount;
    private final Vector<File> previousResultList;
    private List<File> rootList;

    public ArrayStructure(){
        previousResultList = new Vector<>();
    }

    @Override
    public ArrayStructure build(File rootFile, BuildingCallback callback){
        rootList = new ArrayList<>();

        populateArray(rootFile, rootList, callback);

        return this;
    }

    @Override
    public Vector<File> search(String searchQuery, FileFoundCallback fileFoundCallback) {
        threadCount = Runtime.getRuntime().availableProcessors();
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        searchConcurrently((File[]) rootList.toArray(), searchQuery, fileFoundCallback);

        //busy waiting: change later
        while (executor.getActiveCount()!= 0){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();

        return new Vector<>(previousResultList);
    }

    private void searchConcurrently(File[] files, String searchQuery, FileFoundCallback callback){
        int searchField = files.length / threadCount;

        for(int i = 0; i < threadCount; i++){
            int start = searchField * i;
            int end = searchField * (i + 1);
            File[] subFiles = Arrays.copyOfRange(files, start, end);
            executor.submit(() -> searchSubArray(subFiles, searchQuery, callback));
        }
    }

    private void searchSubArray(File[] files, String searchQuery, FileFoundCallback callback){
        for (File file : files) {
            if (file.getName().matches(searchQuery)){
                callback.onFileFound(file);
                previousResultList.add(file);
            }
        }
    }

    private void populateArray(File rootFile, List<File> files, BuildingCallback callback){
        for(File file: Objects.requireNonNull(rootFile.listFiles())){
            files.add(file);
            if (file.isDirectory()){
                populateArray(file, files, callback);
            }
        }
        callback.onBuilding(rootFile.getName());
    }
}
