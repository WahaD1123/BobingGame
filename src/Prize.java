import java.util.HashMap;
import java.util.Map;

public class Prize {
    private static Map<String, Integer> prizePool = new HashMap<>();
    static {
        prizePool.put("状元", 1);
        prizePool.put("对堂", 2);
        prizePool.put("三红", 4);
        prizePool.put("四进", 8);
        prizePool.put("二举", 16);
        prizePool.put("一秀", 32);
    }

    public boolean decPrize(String prizeName) {
        Integer count = prizePool.get(prizeName);
        if (count != null && count > 0) {
            prizePool.put(prizeName, count - 1);
            return true;
        }
        return false;
    }

    public boolean ALLNoPrize() {
        for (Integer count : prizePool.values()) {
            if (count > 0) {
                return false;
            }
        }
        return true;
    }
}
