import java.util.Map;
import java.util.concurrent.Callable;

public class ReadMap extends Thread implements Callable<Boolean> {

    Map<Integer, Integer> map;
    int tries;

    public ReadMap(Map<Integer, Integer> map, int tries, String name) {
        this.map = map;
        this.tries = tries;
        super.setName(name);
    }

    @Override
    public Boolean call() throws Exception {
        boolean result = true;
        for (int i = 0; i < tries; i++) {
            while (!map.containsKey(i)) {
            }
            result = result & (map.get(i) != null);
        }
        System.out.printf("%s закочнил читать все элементы %s\n", getName(), result);
        return result;
    }
}
