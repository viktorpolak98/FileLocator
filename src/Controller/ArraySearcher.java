package Controller;

import Interfaces.FileFoundCallback;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArraySearcher {
    private ExecutorService executor;
    private final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    public ArraySearcher(){
        executor = Executors.newFixedThreadPool(THREAD_COUNT);
    }

    public void searchConcurrently(File[] files, String searchQuery, FileFoundCallback callback){
        int searchField = files.length / THREAD_COUNT;

        for(int i = 0; i < THREAD_COUNT; i++){
            int start = searchField * i;
            int end = searchField * (i + 1);
            File[] subFiles = Arrays.copyOfRange(files, start, end);
            executor.submit(() -> searchSubArray(subFiles, searchQuery, callback));
        }

        executor.shutdown();
    }

    private void searchSubArray(File[] files, String searchQuery, FileFoundCallback callback){
        for (File file : files) {
            if (file.getName().matches(searchQuery)){
                callback.onFileFound(file);
            }
        }
    }
}
