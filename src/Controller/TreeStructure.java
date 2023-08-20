package Controller;

import Interfaces.BuildingCallback;
import Interfaces.FileFoundCallback;
import Interfaces.IDataStructure;
import Model.Node;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TreeStructure implements IDataStructure<Node> {
    private final ExecutorService executor;
    private final List<File> previousResultList;
    private Node rootNode;

    public TreeStructure() {
        executor = Executors.newCachedThreadPool();
        previousResultList =  new ArrayList<>();
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
        for (File childFile : Objects.requireNonNull(node.getFile().listFiles())) {
            Node childNode = new Node(new File(childFile.getName()));
            node.addChild(childNode);
            buildTree(childNode, callback);
        }
        callback.onBuilding(node.getFile().getName());
    }

    @Override
    public void search(String searchQuery, FileFoundCallback fileFoundCallback) {
        previousResultList.clear();
        for (Node child : rootNode.getChildren()) {
            traverseFolder(child, searchQuery, fileFoundCallback);
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
                    previousResultList.add(child.getFile());
                }
            }
        }
    }

    @Override
    public List<File> getPreviousSearch() {
        try {
            wait();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        return previousResultList;
    }
}
