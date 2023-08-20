package Interfaces;

import java.io.File;
import java.util.List;
@FunctionalInterface
public interface RecentSearchesCallback {
    void recentSearches(List<File> files);
}
