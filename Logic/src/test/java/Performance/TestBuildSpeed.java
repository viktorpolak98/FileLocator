package Performance;

import LogicFactories.DataStructureFactory;
import LogicInterfaces.BuildingCallback;
import LogicModel.AvailableStructures;
import LogicModel.Node;
import LogicStructures.ArrayStructure;
import LogicStructures.TreeStructure;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestBuildSpeed {
    //This class is used to compare the performance of different implementations of build structure methods

    public void testArrayStructureBuildWithRecursion(){
        ArrayStructure arrayStructure = (ArrayStructure) DataStructureFactory.buildEmptyStructure(AvailableStructures.ArrayStructure);
        String path = System.getenv("FileFolderHolderLocation");
        File root = new File(path);
        EmptyClassThatDoesNothing buildingCallback = new EmptyClassThatDoesNothing();
        List<File> rootList = new ArrayList<>();
        long start = System.currentTimeMillis();
        arrayStructure.populateArray(root, rootList, buildingCallback);
        double time = System.currentTimeMillis() - start;
        System.out.printf("Time recursion: %.3f \n", time/1000);
    }

    public void testArrayStructureBuildWithQueue(){
        ArrayStructure arrayStructure = (ArrayStructure) DataStructureFactory.buildEmptyStructure(AvailableStructures.ArrayStructure);
        String path = System.getenv("FileFolderHolderLocation");
        File root = new File(path);
        EmptyClassThatDoesNothing buildingCallback = new EmptyClassThatDoesNothing();
        List<File> rootList = new ArrayList<>();
        long start = System.currentTimeMillis();
        arrayStructure.populateArrayQueue(root, rootList, buildingCallback);
        double time = System.currentTimeMillis() - start;
        System.out.printf("Time queue: %.3f \n", time/1000);
    }

    public void testTreeStructureBuildWithRecursion(){
        TreeStructure treeStructure = (TreeStructure) DataStructureFactory.buildEmptyStructure(AvailableStructures.TreeStructure);
        File rootFile = new File(System.getenv("FileFolderHolderLocation"));
        Node rootNode = new Node(rootFile);
        EmptyClassThatDoesNothing buildingCallback = new EmptyClassThatDoesNothing();
        long start = System.currentTimeMillis();
        treeStructure.buildTree(rootNode, buildingCallback);
        double time = System.currentTimeMillis() - start;
        System.out.printf("Time recursion: %.3f \n", time/1000);
    }

    public void testTreeStructureBuildWithQueue(){
        TreeStructure treeStructure = (TreeStructure) DataStructureFactory.buildEmptyStructure(AvailableStructures.TreeStructure);
        File rootFile = new File(System.getenv("FileFolderHolderLocation"));
        Node rootNode = new Node(rootFile);
        EmptyClassThatDoesNothing buildingCallback = new EmptyClassThatDoesNothing();
        long start = System.currentTimeMillis();
        treeStructure.buildTreeWithQueue(rootNode, buildingCallback);
        double time = System.currentTimeMillis() - start;
        System.out.printf("Time queue: %.3f \n", time/1000);
    }

    private class EmptyClassThatDoesNothing implements BuildingCallback {
        //Empty class used to not have to deal with implementing build methods without a BuildingCallback
        @Override
        public void onBuilding(String name) {
            //Empty and only used for testing purposes
        }
    }
}
