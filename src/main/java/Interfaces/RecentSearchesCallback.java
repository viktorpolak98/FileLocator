package main.java.Interfaces;

import java.io.File;
import java.util.Vector;

@FunctionalInterface
public interface RecentSearchesCallback {
    void recentSearches(Vector<File> files);
}
