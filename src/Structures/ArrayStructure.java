package Structures;

import Interfaces.BuildingCallback;
import Interfaces.FileFoundCallback;
import Interfaces.IDataStructure;
import Model.Node;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ArrayStructure implements IDataStructure<List<File>> {
    private ExecutorService executor;
    private final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();
    private final List<File> previousResultList;
    private List<File> rootList;

    public ArrayStructure(){
        executor = Executors.newFixedThreadPool(THREAD_COUNT);
        previousResultList = new ArrayList<>();
    }

    @Override
    public List<File> build(File rootFile, BuildingCallback callback){
        rootList = new ArrayList<>();

        populateArray(rootFile, rootList, callback);

        return rootList;
    }

    @Override
    public void search(String searchQuery, FileFoundCallback fileFoundCallback) {
        searchConcurrently((File[]) rootList.toArray(), searchQuery, fileFoundCallback);
    }

    @Override
    public List<File> getPreviousSearch() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return previousResultList;
    }

    private void searchConcurrently(File[] files, String searchQuery, FileFoundCallback callback){
        int searchField = files.length / THREAD_COUNT;

        for(int i = 0; i < THREAD_COUNT; i++){
            int start = searchField * i;
            int end = searchField * (i + 1);
            File[] subFiles = Arrays.copyOfRange(files, start, end);
            executor.submit(() -> searchSubArray(subFiles, searchQuery, callback));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        notifyAll();
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
            callback.onBuilding(file.getName());
            if (file.isDirectory()){
                populateArray(file, files, callback);
            }
        }
    }

}
