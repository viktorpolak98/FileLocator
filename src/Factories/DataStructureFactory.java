package Factories;

import Structures.ArrayStructure;
import Structures.TreeStructure;
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

    public static IDataStructure build(String structureType){
        for (AvailableStructures structures : AvailableStructures.values()){
            if (structures.name().equals(structureType)){
                return build(structures);
            }
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
