package LogicModel;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node implements Serializable {
    private File file;
    private final List<Node> children;

    public Node(File file) {
        this.file = file;
        children = new ArrayList<>();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public void removeChild(Node child) {
        children.remove(child);
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }
}