package main.java.Factories;

import main.java.Structures.ArrayStructure;
import main.java.Structures.TreeStructure;
import main.java.Interfaces.IDataStructure;
import main.java.Model.AvailableStructures;

public class DataStructureFactory {

    public static IDataStructure buildEmptyStructure(IDataStructure structure){
        if (structure instanceof ArrayStructure){
            return new ArrayStructure();
        }

        if (structure instanceof TreeStructure){
            return new TreeStructure();
        }

        return buildEmptyStructure(AvailableStructures.TreeStructure); //Default
    }

    public static IDataStructure buildEmptyStructure(String structureType){
        for (AvailableStructures structures : AvailableStructures.values()){
            if (structures.name().equals(structureType)){
                return buildEmptyStructure(structures);
            }
        }

        return buildEmptyStructure(AvailableStructures.TreeStructure); //Default
    }

    public static IDataStructure buildEmptyStructure(AvailableStructures structureType){
        return switch (structureType) {
            case ArrayStructure -> new ArrayStructure();
            case TreeStructure -> new TreeStructure();
        };
    }
}
