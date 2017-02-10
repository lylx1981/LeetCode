
/*
思路： 这道题简单的解法就是Hashtable，滑动窗口依次判断每10个字符的字符串，把这个遇到的字符串加入到Hashmap里，如果一旦已经加入过，就说明是多余一次，就输入到结果集合中去。

这里的一个优化操作是如何用一个二进制数去表示一个10个字符的字符串，这样可以大大减少需要存储的空间。用到了所以Bit Mask的方法，其实用1个二进制说的不同0和1的表示来代表各种不同的状态。这里就是用来代表出现的不同字符，比如A，C，G,T。我们发现这几个字符的的最后3个Bit是不一样的，所以我们可以用他们的最后三位来表示他们即可。一个10个字符，那么就需要30位，一个整数是32位，所以一个整数就够用了。



0x stands for hexadecimal number, and each number or letter stands for 4 bits

3 = 0011 (binary)

F = 15 (base 10) = 1111 (binary)

0x3FFFFFFF = 0011 1111 1111 1111 1111 1111 1111 1111

Every A,C,G,T is represented in 3 bits, and a substring contains 10 letters, 3*10 = 30, but total bits of int is 32, there are 2 left, we use 0x3FFFFFFF to remove the leftmost 2 bits.

另外可以看下面的这个关于Bit Mask的介绍： http://blog.csdn.net/xiao__gui/article/details/11701893

Java位运算在程序设计中的使用：位掩码（BitMask）

在Java中，位运算符有很多，例如与(&)、非(~)、或(|)、异或(^)、移位(<<和>>)等。这些运算符在日常编码中很少会用到。

在下面的一个例子中，会用到位掩码（BitMask），其中包含大量的位运算。不只是在Java中，其他编写语言中也是可以使用的。
例如，在一个系统中，用户一般有查询(Select)、新增(Insert)、修改(Update)、删除(Selete)四种权限，四种权限有多种组合方式，也就是有16中不同的权限状态（2的4次方）。
一般情况下会想到用四个boolean类型变量来保存：

上面用四个boolean类型变量来保存每种权限状态。下面是另外一种方式，使用位掩码的话，用一个二进制数即可，每一位来表示一种权限，0表示无权限，1表示有权限。


// 是否允许查询，二进制第1位，0表示否，1表示是  
    public static final int ALLOW_SELECT = 1 << 0; // 0001  
      
    // 是否允许新增，二进制第2位，0表示否，1表示是  
    public static final int ALLOW_INSERT = 1 << 1; // 0010  
      
    // 是否允许修改，二进制第3位，0表示否，1表示是  
    public static final int ALLOW_UPDATE = 1 << 2; // 0100  
      
    // 是否允许删除，二进制第4位，0表示否，1表示是  
    public static final int ALLOW_DELETE = 1 << 3; // 1000  

*/
public class RepeatedDNASequence {

    /**
     * Hash Table. Bit Manipulation.
     * To optimize space usage, map string to other key that won't collide.
     * Design a hash function according to observation.
     * A: 0x41, C: 0x43, G: 0x47, T: 0x54, last 3 bits are different.
     * 10 chars, each 3 bits, 10 x 3 = 30 bits < 32
     * <p>
     * Key: an int to record the bit mask of current substring,
     * Value: a boolean, true means showed up before, false means already added
     * Update the map
     */
    public List<String> findRepeatedDnaSequences(String s) {
        if (s == null || s.length() < 10) {
            return Collections.emptyList();
        }
        List<String> res = new ArrayList<>();
        Map<Integer, Boolean> map = new HashMap<>();
        for (int t = 0, i = 0; i < s.length(); i++) {
            t = (t << 3 & 0x3FFFFFFF) | (s.charAt(i) & 7);

//t << 3 & 0x3FFFFFFF的是起到滑动窗口的作用。<<3 起到将原来的数左移3位的作用，& 0x3FFFFFFF起到再将最左边的2个Bit去掉不用的作用，因为一个整数一共32个Bits，我们只需要30Bits。这两个操作之后，现在就是一个30位的Bit，同样只包含有最近的9个数的位，而最低的三个位是0. 特别注意这里0x3FFFFFFF是必须要放到后面做的一个操作以保证数字是30位，否则&后做的话，自动又将数字变为32位了！
//(s.charAt(i) & 7， 7的二进制表示是111，所以这里的意思实际就是将s.charAt(i）的最后三位取出来。最后|符号就是把先加入的这个字符的三位继续加入到原来的t里。

            if (map.containsKey(t)) {
                if (map.get(t)) { //如果map里面已经包含t了，说明重复了，那么就对s就从i-9,到i的位置把这10个字符组成的字符串拿出来加入到结果集合中即可。
                    res.add(s.substring(i - 9, i + 1));
                    map.put(t, false);
                }
            } else {
                map.put(t, true);
            }
        }
        return res;
    }

    /** Leetcode上的方法，貌似更简单。
     */
public List<String> findRepeatedDnaSequences(String s) {
    Set<Integer> words = new HashSet<>();
    Set<Integer> doubleWords = new HashSet<>();
    List<String> rv = new ArrayList<>();
    char[] map = new char[26];
    //map['A' - 'A'] = 0;
    map['C' - 'A'] = 1;
    map['G' - 'A'] = 2;
    map['T' - 'A'] = 3;

    for(int i = 0; i < s.length() - 9; i++) {
        int v = 0;
        for(int j = i; j < i + 10; j++) {
            v <<= 2;
            v |= map[s.charAt(j) - 'A'];
        }
        if(!words.add(v) && doubleWords.add(v)) {
            rv.add(s.substring(i, i + 10));
        }
    }
    return rv;
}


    /** 这就是最传统的Hashmap方法，没有上面的优化。
     * Hash Table. O(n) Time & Space.
     * HashSet with previous appeared results.
     */
    public List<String> findRepeatedDnaSequencesB(String s) {
        if (s == null || s.length() < 10) {
            return Collections.emptyList();
        }
        List<String> res = new ArrayList<>();
        Set<String> set = new HashSet<>();
        for (int i = 0; i < s.length() - 10; i++) {
            String sub = s.substring(i, i + 10);
            if (set.contains(sub)) {
                res.add(s);
            }
            set.add(s);
        }
        return res;
    }
}
