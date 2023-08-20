package Factories;

import Controller.ArrayStructure;
import Controller.TreeStructure;
import Interfaces.IDataStructure;
import Model.AvailableStructures;

public class DataStructureFactory {

    public static IDataStructure build(IDataStructure structure){
        if (structure instanceof ArrayStructure){
            return new ArrayStructure();
        }

        if (structure instanceof TreeStructure){
            return new TreeStructure();
        }

        return null;
    }

    public static IDataStructure build(AvailableStructures structureType){
        return switch (structureType) {
            case ArrayStructure -> new ArrayStructure();
            case TreeStructure -> new TreeStructure();
        };
    }
}
