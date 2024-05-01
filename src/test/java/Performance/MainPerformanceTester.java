package Performance;

public class MainPerformanceTester {
    public static void main(String[] args) {
        TestBuildSpeed testBuildSpeed = new TestBuildSpeed();

        System.out.println("Build speeds for ArrayStructure\n");
        for (int i = 0; i < 5; i++){
            testBuildSpeed.testTreeStructureBuildWithRecursion();
            testBuildSpeed.testTreeStructureBuildWithQueue();
        }

        System.out.println("\nBuild speeds for ArrayStructure\n");
        for (int i = 0; i < 5; i++){
            testBuildSpeed.testArrayStructureBuildWithRecursion();
            testBuildSpeed.testArrayStructureBuildWithQueue();
        }

    }
}
