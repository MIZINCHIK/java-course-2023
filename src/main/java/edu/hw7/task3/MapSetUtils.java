package edu.hw7.task3;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MapSetUtils {
    private MapSetUtils() {
        throw new IllegalStateException();
    }

    public static <K, V> void addToSetInMap(Map<K, Set<V>> map, V value, K key) {
        if (map.containsKey(key)) {
            map.get(key).add(value);
        } else {
            Set<V> newSet = new HashSet<>();
            newSet.add(value);
            map.put(key, newSet);
        }
    }

    public static <K, V> void removeFromSetInMap(Map<K, Set<V>> map, V value, K key) {
        var set = map.get(key);
        if (set == null) {
            return;
        }
        set.remove(value);
        if (set.isEmpty()) {
            map.remove(key);
        }
    }
}
