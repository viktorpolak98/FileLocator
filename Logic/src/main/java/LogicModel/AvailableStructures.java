package LogicModel;

public enum AvailableStructures {
    ArrayStructure("array"),
    TreeStructure("tree");

    private AvailableStructures(String type){
        this.type = type;
    }

    private final String type;

    public String getType() {
        return type;
    }
}
