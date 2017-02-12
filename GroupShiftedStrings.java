
    /*
    思路：比较简单但是有意思的一道题，就是对每个字符串计算其模式即可（也就是其Key，如果两个字符串拥有相同的Key,name他们就应该属于一组 ）。有很多中方法计算模式，其中一种就是让字符串中每两个字符进行比较，求出其字符对应的Offset，然后把这些Offset拼接起来就是一个Key。
*/

public List<List<String>> groupStrings(String[] strings) {
    List<List<String>> ret = new ArrayList<List<String>>();
    HashMap<String, List<String>> map = new HashMap<String, List<String>>();
    for(String s : strings) {
        String key = "";
        for(int i = 1; i < s.length(); i++) {
            int offset = s.charAt(i) - s.charAt(i - 1); //计算i和i-1之间的offset,如果是offset小于0，就是第一个字符比第二个字符还大的情况（比如na），这样的话，让其offset+26即可。然后其实na(n>a)其实是和mz(m<z)应该属于一个Group的
            key += offset < 0 ? offset + 26 : offset;
        }
        if(!map.containsKey(key)) map.put(key, new ArrayList<String>());
        map.get(key).add(s); //把这个字符串放入Key对应的List里
    }
    for(List<String> ss : map.values()) {
        Collections.sort(ss); //注意这里题目的要求，每个Group里面的每个字符串是从小到大排列好的，所以这里要排序
        ret.add(ss);
    }
    return ret;
}
