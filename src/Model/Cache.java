package Model;

import Model.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Cache implements Serializable {
    private final long serialVersionUID = 1L;
    private final LinkedList<List<Node>> cache;
    private final int MAX_SIZE;

    public Cache(int size, LinkedList<List<Node>> cache){
        MAX_SIZE = size;
        this.cache = cache;
    }

    public void add(List<Node> nodes){
        cache.addLast(nodes);
        if (cache.size() > MAX_SIZE){
            cache.removeFirst();
        }
    }
    public void clearCache(){
        cache.clear();
    }
    public List<Node> getAllAsSingleList(){
        ArrayList<Node> nodeArrayList = new ArrayList<>();
        for (List<Node> list : cache) {
            nodeArrayList.addAll(list);
        }

        return nodeArrayList;
    }
}
