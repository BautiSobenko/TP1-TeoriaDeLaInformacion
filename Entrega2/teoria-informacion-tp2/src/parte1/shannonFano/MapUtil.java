package parte1.shannonFano;

import java.util.*;

// Clase que ordena los TreeMap en vez de orden por la Key se ordena por el Value

public class MapUtil {
    public static <Character, Double extends Comparable<? super Double>> Map<Character, Double> sortByValue(Map<Character, Double> map) {
        List<Map.Entry<Character, Double>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        Map<Character, Double> result = new LinkedHashMap<>();
        for (Map.Entry<Character,Double> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
