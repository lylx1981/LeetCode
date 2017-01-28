/*
    思路：用2层循环就行了，凡是遇见一样的元素，就Count++，直接遇到下一个不一样的元素的时候Count回归1，重新开始计数。没计数完一个元素就把等到的Count最终直和元素自己重新附到当前生成的String中去就行了。因为每次生成的String都和之前的完全不一样，所以每次都重新生成一个StringBuilder对象。 另外，这里，重要要学会的是StringBuilder的使用，以及 char [] oldChars = oldString.toCharArray();，也就是如何将一个String转化为Char类型的数组再开始判断。

*/


 public class Solution {
    public String countAndSay(int n) {
        String oldString = "1";
        while (--n > 0) {
            StringBuilder sb = new StringBuilder();
            char [] oldChars = oldString.toCharArray();

            for (int i = 0; i < oldChars.length; i++) {
                int count = 1;
                while ((i+1) < oldChars.length && oldChars[i] == oldChars[i+1]) {
                    count++;
                    i++;
                }
                sb.append(String.valueOf(count) + String.valueOf(oldChars[i]));
            }
            oldString = sb.toString();
        }

        return oldString;
    }
}
