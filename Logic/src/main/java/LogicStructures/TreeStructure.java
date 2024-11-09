package LogicStructures;

import LogicInterfaces.BuildingCallback;
import LogicInterfaces.FileFoundCallback;
import LogicInterfaces.IDataStructure;
import LogicModel.Node;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

public class TreeStructure implements IDataStructure {
    private ThreadPoolExecutor executor;
    private final Vector<File> previousResultList;
    private Node rootNode;

    public TreeStructure() {
        previousResultList =  new Vector<>();
    }

    @Override
    public boolean isDone(){
        return executor.getActiveCount() == 0;
    }

    @Override
    public void build(File rootFile, BuildingCallback callback) {
        if (!rootFile.isDirectory()){
            return;
        }

        rootNode = new Node(rootFile);
//        buildTree(rootNode, callback);
        buildTreeWithQueue(rootNode, callback);
    }

    public void buildTree(Node node, BuildingCallback callback) {
        String path = node.getFile().getAbsolutePath() + "/";
        for (File childFile : Objects.requireNonNull(node.getFile().listFiles())) {
            Node childNode = new Node(new File(path + childFile.getName()));
            node.addChild(childNode);
            if(childFile.isDirectory()){
                buildTree(childNode, callback);
            }
        }
        callback.onBuilding(node.getFile().getName());
    }

    public void buildTreeWithQueue(Node rootNode, BuildingCallback callback){
        Queue<Node> queue = new LinkedList<>();
        queue.add(rootNode);

        while (!queue.isEmpty()){
            Node root = queue.remove();
            String path = root.getFile().getAbsolutePath() + "/";

            for (File f : Objects.requireNonNull(root.getFile().listFiles())){
                Node childNode = new Node(new File(path + f.getName()));
                root.addChild(childNode);
                if (f.isDirectory()){
                    queue.add(childNode);
                }
            }
        }
        callback.onBuilding(rootNode.getFile().getName());
    }

    @Override
    public Vector<File> search(String searchQuery, FileFoundCallback fileFoundCallback) {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        previousResultList.clear();
        for (Node child : rootNode.getChildren()) {
            traverseFolder(child, searchQuery, fileFoundCallback);
        }

        //busy waiting: change later
        while (!isDone()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();

        return new Vector<>(previousResultList);
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
}