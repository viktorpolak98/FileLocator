package Controller;

import Interfaces.FileFoundCallback;
import Model.Cache;
import Model.Node;
import View.FolderSearcherGUI;
import View.RecentSearchesGUI;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FolderController {
    private final Cache cache;
    private FolderSearcherGUI folderSearcherGUI;
    private RecentSearchesGUI recentSearchesGUI;

    public FolderController(int size){
        cache = createCache(size);
        folderSearcherGUI = new  FolderSearcherGUI(this);
        recentSearchesGUI = new RecentSearchesGUI(this);
    }

    public List<Node> getRecentSearches(){
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

    public void search(Node root, String query){
        FolderSearcher searcher = new FolderSearcher();
        updateRecentSearches(searcher);
        searcher.traverseConcurrently(root, query, folderSearcherGUI);
    }

    private void updateRecentSearches(FolderSearcher searcher){
        recentSearchesGUI.recentSearches(searcher.getPreviousSearch());
    }

}
