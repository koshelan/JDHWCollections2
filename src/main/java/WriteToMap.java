import java.util.Map;
import java.util.concurrent.Callable;

public class WriteToMap extends Thread implements Callable<Boolean> {

    Map<Integer, Integer> map;
    int[] array;
    boolean fromBeginningToEnd;

    public WriteToMap(Map<Integer, Integer> map, int[] array, boolean fromBeginningToEnd, String name) {
        this.map = map;
        this.array = array;
        this.fromBeginningToEnd = fromBeginningToEnd;
        super.setName(name);
    }

    @Override
    public Boolean call() throws Exception {
        if (fromBeginningToEnd) {
            write(0, array.length, 1);
        } else {
            write(array.length - 1, -1, -1);
        }
        int result = map.size();
        System.out.printf("%s записал %s элементов \n",getName(),result);
        return result==array.length;
    }

    private void write(int beginning, int end, int addendum) {
        while (beginning != end) {
            map.put(beginning, array[beginning]);
            beginning += addendum;
        }
    }

}
