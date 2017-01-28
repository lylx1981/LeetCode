/*
    思路：直接就从后往前判断就行了，只要就是要注意第一次如果一直遇到空格的情况，就暂时一直向左移就行了，直到遇到第一个字符，再开始计数即可。自己这道题想的太复杂了，在琢磨要不要对Word进行更进一步的定义，比如首字符必须是大写，长度至少为2，等等，结果发现好像答案并没有要求这些。

*/


 public int lengthOfLastWord(String s) {
        int length = 0;
        char[] chars = s.toCharArray();
        for (int i = s.length() - 1; i >= 0; i--) {
            if (length == 0) { //主要是第一次这里稍微处理一下即可。
                if (chars[i] == ' ') {
                    continue;
                } else {
                    length++;
                }
            } else {
                if (chars[i] == ' ') {
                    break;
                } else {
                    length++;
                }
            }
        }

        return length;
    }
