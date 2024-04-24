package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;


public class Cache implements Serializable {
    private final long serialVersionUID = 1L;
    private final LinkedList<Vector<File>> cache;
    private final int MAX_SIZE;

    public Cache(int size, LinkedList<Vector<File>> cache){
        MAX_SIZE = size;
        this.cache = cache;
    }

    public void add(Vector<File> files){
        cache.addLast(files);
        if (cache.size() > MAX_SIZE){
            cache.removeFirst();
        }
    }
    public void clearCache(){
        cache.clear();
    }

    public Vector<File> getAllAsSingleList(){
        Vector<File> fileArrayList = new Vector<>();
        for (List<File> list : cache) {
            fileArrayList.addAll(list);
        }

        return fileArrayList;
    }
}
