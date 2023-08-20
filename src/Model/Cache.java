package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Cache implements Serializable {
    private final long serialVersionUID = 1L;
    private final LinkedList<List<File>> cache;
    private final int MAX_SIZE;

    public Cache(int size, LinkedList<List<File>> cache){
        MAX_SIZE = size;
        this.cache = cache;
    }

    public void add(List<File> files){
        cache.addLast(files);
        if (cache.size() > MAX_SIZE){
            cache.removeFirst();
        }
    }
    public void clearCache(){
        cache.clear();
    }

    public List<File> getAllAsSingleList(){
        ArrayList<File> fileArrayList = new ArrayList<>();
        for (List<File> list : cache) {
            fileArrayList.addAll(list);
        }

        return fileArrayList;
    }
}
