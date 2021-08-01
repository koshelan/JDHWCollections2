import java.util.*;
import java.util.concurrent.*;

public class Main {

    private static final ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static List<Callable<Boolean>> threadList = new ArrayList();
    private static long begin, end;
    private static List<Future<Boolean>> future;

    public static void main(String[] args) {
        System.out.println("Тестовый прого для создания потоков");
        doItWithSize(1);
        System.out.println("Тестирование разницы в производительности");
        doItWithSize(100);
        doItWithSize(100_000);
        doItWithSize(1_000_000);
        es.shutdown();
    }

    public static void doItWithSize(int size) {
        System.out.println("\nПопытка при массиве рамером: " + size);
        int[] array = new Random().ints(size).toArray();
        Map<Integer, Integer> concurrentHashMap = new ConcurrentHashMap();
        Map<Integer, Integer> synchronizedHashMap = Collections.synchronizedMap(new HashMap<>());
        System.out.println("Попытка с ConcurrentHashMap");
        runWithMap(concurrentHashMap, array);
        System.out.println("Попытка с synchronizedMap");
        runWithMap(synchronizedHashMap, array);
    }

    public static void runWithMap(Map<Integer, Integer> map, int[] array) {
        threadList.clear();
        threadList.add(new WriteToMap(map, array, true, "Поток-записи 1"));
        threadList.add(new WriteToMap(map, array, false, "Поток-записи 2"));
        threadList.add(new ReadMap(map, array.length, "Поток-чтения 1"));
        threadList.add(new ReadMap(map, array.length, "Поток-чтения 2"));
        begin = System.currentTimeMillis();
        try {
            future = es.invokeAll(threadList);
            future.forEach(f -> {
                while (!f.isDone()) {
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        end = System.currentTimeMillis();
        System.out.printf("это заняло %s мс\n", (end - begin));
    }

}
