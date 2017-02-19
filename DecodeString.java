

public class Solution {
    public String decodeString(String s) {
        String res = "";
        Stack<Integer> countStack = new Stack<>();
        Stack<String> resStack = new Stack<>();
        int idx = 0;
        while (idx < s.length()) {
            //如果是数字位，要把整个数字解析出来
            if (Character.isDigit(s.charAt(idx))) {
                int count = 0;
                while (Character.isDigit(s.charAt(idx))) {
                    count = 10 * count + (s.charAt(idx) - '0');
                    idx++;
                }
                countStack.push(count);
            }
            //遇见[的话，就让当前res进Stack，并重新设置为”“
            else if (s.charAt(idx) == '[') {
                resStack.push(res);
                res = "";
                idx++;
            }
            //遇见]的话，就从Stack里拿出Count，对当前res的值进行Clone预算
            else if (s.charAt(idx) == ']') {
                //同时从两个Stack里面Pop出当前Res要复制的次数，以及在res之前的前缀
                StringBuilder temp = new StringBuilder (resStack.pop());
                int repeatTimes = countStack.pop();
                for (int i = 0; i < repeatTimes; i++) {
                    temp.append(res); //把res复制多次后加在temp后
                }
                res = temp.toString();
                idx++;
            }
            else {
                res += s.charAt(idx++); //遇到的就是一个字母，直接添加到res即可。
            }
        }
        return res;
    }
}
