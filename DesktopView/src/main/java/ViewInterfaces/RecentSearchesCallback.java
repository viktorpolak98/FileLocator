package ViewInterfaces;

import java.io.File;
import java.util.Vector;

@FunctionalInterface
public interface RecentSearchesCallback {
    void recentSearches(Vector<File> files);
}
