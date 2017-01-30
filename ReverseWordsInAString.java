/**
 * 思路，第一种方法更原始，就是对字符串一个个字符处理，每处理出来一个Word，加入到当前Res最前面就行了。
 * 第二种方法用了更多String自己带的函数，直接把Words都拿出来了，然后对Words数组从后往前一个个加入到Res里面即可。
 */

class ReverseWords {
    public static void main(String[] args) {
        String given = "the sky is blue";
        String given2 = "    a    b";
        System.out.println(new ReverseWords().reverseWords(given));
    }

    /**
     * If space, continue
     * If not, get the word and insert to the front of result
     * note that result may not contain spaces before or after
     */
    public String reverseWords(String s) {
        if (s == null || s.length() == 0) return "";
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') continue;
            else {
                StringBuilder word = new StringBuilder();
                while (i < s.length()) {
                    c = s.charAt(i);
                    if (c == ' ') break;
                    word.append(c);
                    i++;
                }
                res = res.length() == 0 ? word.toString() : word.toString() + " " + res; // insert to front of res
                i--; // reset i
            }
        }
        return res;
    }

    /**
     * Trim input string
     * Split it with a space
     * Traversal backwards
     * Trim result to remove last space
     */
    public String reverseWordsB(String s) {
        if (s == null || s.length() == 0) return "";
        s = s.trim();
        StringBuilder res = new StringBuilder();
        String[] words = s.split(" ");
        for (int i = words.length - 1; i >= 0; i--) {
            if (!words[i].equals("")) {
                res.append(words[i]);
                if (i != 0) res.append(" ");
            }
        }
        return res.toString(); // remove last space
    }
}
