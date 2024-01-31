package Structures;

import Interfaces.BuildingCallback;
import Interfaces.FileFoundCallback;
import Interfaces.IDataStructure;
import Model.Node;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TreeStructure implements IDataStructure<Node> {
    private final ExecutorService executor;
    private final Vector<File> previousResultList;
    private Node rootNode;

    public TreeStructure() {
        executor = Executors.newCachedThreadPool();
        previousResultList =  new Vector<>();
    }
    @Override
    public Node build(File rootFile, BuildingCallback callback) {
        if (!rootFile.isDirectory()){
            return null;
        }

        rootNode = new Node(rootFile);
        buildTree(rootNode, callback);

        return rootNode;
    }

    public void buildTree(Node node, BuildingCallback callback) {
        String path = node.getFile().getAbsolutePath() + "/";
        for (File childFile : Objects.requireNonNull(node.getFile().listFiles())) {
            Node childNode = new Node(new File(path + childFile.getName()));
            node.addChild(childNode);
            if(!childFile.isDirectory()){
                continue;
            }
            buildTree(childNode, callback);
        }
        callback.onBuilding(node.getFile().getName());
    }


    //TODO: Fix: Current thread is not owner
    @Override
    public void search(String searchQuery, FileFoundCallback fileFoundCallback) {
        previousResultList.clear();
        for (Node child : rootNode.getChildren()) {
            traverseFolder(child, searchQuery, fileFoundCallback);
        }
/*
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        notifyAll();*/
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
                    previousResultList.add(child.getFile());
                }
            }
        }
    }

    @Override
    public Vector<File> getPreviousSearch() {
        /*try {
            wait();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Done: " + previousResultList.size());
*/
        return previousResultList;
    }
}
