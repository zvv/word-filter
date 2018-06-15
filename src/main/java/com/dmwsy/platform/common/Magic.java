package com.dmwsy.platform.common;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class Magic {
    public static class ValueComparator implements Comparator {
        public Map map;

        public ValueComparator(Map map) {
            this.map = map;
        }

        public int compare(Object keyA, Object keyB) {
            Comparable valueA = (Comparable) map.get(keyA);
            Comparable valueB = (Comparable) map.get(keyB);
            return valueB.compareTo(valueA);
        }
    }

    public static class KeyComparator implements Comparator {
        public Map map;

        public KeyComparator(Map map) {
            this.map = map;
        }

        public int compare(Object keyA, Object keyB) {
            Comparable valueA = (Comparable) keyA;
            Comparable valueB = (Comparable) keyB;
            return valueB.compareTo(valueA);
        }
    }

    public static Map sortByValue(Map unsortedMap) {
        Map sortedMap = new TreeMap(new ValueComparator(unsortedMap));
        sortedMap.putAll(unsortedMap);
        return sortedMap;
    }

    public static Map sortByKey(Map unsortedMap) {
        Map sortedMap = new TreeMap(new KeyComparator(unsortedMap));
        sortedMap.putAll(unsortedMap);
        return sortedMap;
    }
}
