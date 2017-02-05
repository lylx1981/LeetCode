
    /** 思路：用Hashmap。一种方法是记录上次打印的时间，这样比较都要与上次打印时间+10进行比较。另一种稍微改进的方法，直接存入下次应该打印的时间。这样的话，直接与这个数比较即可，如果小于，就暂时不打印。如果到了等于，就更新其为新的值，也就是当前Timestamp+10.


    Instead of logging print times, I store when it's ok for a message to be printed again. Should be slightly faster, because I don't always have to add or subtract (e.g., timestamp < log[message] + 10) but only do in the true case. Also, it leads to a shorter/simpler longest line of code.


     */

public class Logger {

    private Map<String, Integer> ok = new HashMap<>();

    public boolean shouldPrintMessage(int timestamp, String message) {
        if (timestamp < ok.getOrDefault(message, 0)) //ok.getOrDefault(message, 0)就是把message现在对应的Value拿出来，后面那个0，就是个这个函数特有的参数，一般给0即可。
            return false;
        ok.put(message, timestamp + 10); 
        return true;
    }
}
