package Controller;

import LogicInterfaces.BuildingCallback;
import LogicInterfaces.FileFoundCallback;
import LogicInterfaces.IDataStructure;
import ViewModel.Cache;

import java.io.*;
import java.util.LinkedList;
import java.util.Vector;

public class MainController {
    private final Cache cache;
//    private final RecentSearchesGUI recentSearchesGUI;
    private final IDataStructure dataStructure;

    public MainController(int size, IDataStructure dataStructure){
        this.dataStructure = dataStructure;
        cache = createCache(size);
//        new FolderSearcherGUI(this);
//        recentSearchesGUI = new RecentSearchesGUI(this);
    }

    public Vector<File> getRecentSearches(){
        return cache.getAllAsSingleList();
    }

    public void saveCache() {
        try {
            FileOutputStream fos = new FileOutputStream("utilFiles\\cache.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(cache);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Cache createCache(int size) {
        Cache savedCache = new Cache(size, new LinkedList<>());

        File file = new File("utilFiles/cache.txt");
        if (!file.exists()) {
            return savedCache;
        }
        try {
            FileInputStream fis = new FileInputStream("utilFiles\\cache.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            savedCache = (Cache) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return savedCache;
    }

    public void createDataStructure(File rootFile, BuildingCallback callback){
        dataStructure.build(rootFile, callback);
    }

    public void search(String query, FileFoundCallback callback){
        Vector<File> previousSearch = dataStructure.search(query, callback);
        updateRecentSearches(previousSearch);
    }

    private void updateRecentSearches(Vector<File> previousSearch){
        cache.add(previousSearch);

//        recentSearchesGUI.recentSearches(previousSearch);
    }

}
