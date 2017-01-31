/*
思路： 自己想的太复杂了，觉得字符串衔接的Solution太简单，一直在尽力找Bit相关的可以压缩传输的Idea.但是看起来，解法很简单，就是衔接字符串。一个Trick是，对于每个字符串，要有一个办法如何把字符串分割开，因为 any possible characters out of 256 valid ascii characters。 这样的话，就存在问题，就是一个字符串里面也可能包含“ ”空格等等。

这里我们可以维护一个StringBuilder，读出每个input string的长度，append一个特殊字符，例如'/'，再append string。这样再decode的时候我们就可以利用java的String.indexOf(char，startIndex)来算出自startIndex其第一个'/'的位置，同时计算出接下来读取的string长度，用String.substring()读出字符串以后我们更新index，来进行下一次读取。这样，任意的字符串都可以被透明传输了。 */

public class Codec {
    
    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        for(String s : strs) {
            sb.append(s.length()).append('/').append(s); 
        }
        return sb.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        List<String> ret = new ArrayList<String>();
        int i = 0;
        while(i < s.length()) {
            int slash = s.indexOf('/', i); 读到下一个/的位置
            int size = Integer.valueOf(s.substring(i, slash));//把该位置前面的长度信息读出来
            ret.add(s.substring(slash + 1, slash + size + 1));//直接从当前/的下一个位置开始读Size个字符，就是所要的字符串
            i = slash + size + 1;
        }
        return ret;
    }
}
