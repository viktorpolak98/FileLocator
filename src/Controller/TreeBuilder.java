package Controller;

import Interfaces.BuildingCallback;
import Model.Node;

import java.io.File;
import java.util.Objects;

public class TreeBuilder {
    public static void buildTree(File file, Node node, BuildingCallback callback) {
        if (file.isDirectory()) {
            for (File childFile : Objects.requireNonNull(file.listFiles())) {
                Node childNode = new Node(new File(childFile.getName()));
                node.addChild(childNode);
                buildTree(childFile, childNode, callback);
            }
        }
        callback.onBuilding(node);
    }
}
