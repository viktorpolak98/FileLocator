package Structures;

import Interfaces.BuildingCallback;
import Interfaces.FileFoundCallback;
import Interfaces.IDataStructure;

import java.io.File;
import java.text.FieldPosition;
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
    public void build(File rootFile, BuildingCallback callback){
        rootList = new ArrayList<>();

        populateArray(rootFile, rootList, callback);

    }

    @Override
    public Vector<File> search(String searchQuery, FileFoundCallback fileFoundCallback) {
        threadCount = Runtime.getRuntime().availableProcessors();
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);

        searchConcurrently(searchQuery, fileFoundCallback);

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

    private void searchConcurrently(String searchQuery, FileFoundCallback callback){
        //if threadCount is larger than size of list set search field to 1
        int searchField = Math.abs(Math.max((rootList.size() / threadCount), 1));

        int i = 0;
        while (i < rootList.size()){
            int start = searchField * i;
            int end = searchField * (i + 1);
            executor.submit(() -> searchSubArray(start, end, searchQuery, callback));
            i++;
        }
    }

    private void searchSubArray(int start, int end, String searchQuery, FileFoundCallback callback){
        int i = start;
        while(i < end){
            File file = rootList.get(i);
            if (file.getName().contains(searchQuery)){
                callback.onFileFound(file);
                previousResultList.add(file);
            }
            i++;
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
