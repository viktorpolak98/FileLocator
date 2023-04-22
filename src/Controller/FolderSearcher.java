package Controller;

import Interfaces.FileFoundCallback;
import Model.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FolderSearcher {
    private final ExecutorService executor;
    private final ArrayList<Node> previousResultList;

    public FolderSearcher() {
        int THREAD_COUNT = Runtime.getRuntime().availableProcessors();
        executor = Executors.newFixedThreadPool(THREAD_COUNT);
        previousResultList =  new ArrayList<>();
    }

    public void traverseConcurrently(Node root, String searchQuery, FileFoundCallback callback) {
        previousResultList.clear();
        for (Node child : root.getChildren()) {
            traverseFolder(child, searchQuery, callback);
        }

        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        notifyAll();

    }

    private void traverseFolder(Node node, String searchQuery, FileFoundCallback callback) {
        if (!node.isLeaf()){
            List<Node> files = node.getChildren();
            searchChildren(searchQuery, callback, files);
        }
    }

    private void searchChildren(String searchQuery, FileFoundCallback callback, List<Node> nodes) {
        for (Node child : nodes) {
            if (child.getFile().isDirectory()) {
                executor.submit(() -> traverseFolder(child, searchQuery, callback));
            } else {
                if (child.getFile().getName().contains(searchQuery)) {
                    callback.onFileFound(child.getFile());
                    previousResultList.add(child);
                }
            }
        }
    }

    public List<Node> getPreviousSearch() {
        try {
            wait();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        return previousResultList;
    }

}

