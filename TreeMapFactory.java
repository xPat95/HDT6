import java.util.TreeMap;
import java.util.Map;

public class TreeMapFactory implements MapFactory {
    @Override
    public Map<String, Pokemon> createMap() {
        return new TreeMap<>();
    }
}