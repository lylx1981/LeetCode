/*
思路：数学题，自己想到的是用最小堆之类的，然后定义一下两个数字之间的字典序关系，然后都加入最小堆后，依次从中拿出即可。Leetcode的答案更巧，最核心的就是观察到给定一个数N,它的下一个数有三种情况，要么是比当前位数多加一位0的那个数，要么是当前数+1的那个数，要么是比现在当前数/10后再+1的那个数（要注意尾数都是9的情况要特殊处理，如果/10后尾数是9，那就继续一直/10，直到尾数不为9为止）。

下面这个分析很好，代码就是一模一样写出来的。
The basic idea is to find the next number to add.
Take 45 for example: if the current number is 45, the next one will be 450 (450 == 45 * 10)(if 450 <= n), or 46 (46 == 45 + 1) (if 46 <= n) or 5 (5 == 45 / 10 + 1)(5 is less than 45 so it is for sure less than n).
We should also consider n = 600, and the current number = 499, the next number is 5 because there are all "9"s after "4" in "499" so we should divide 499 by 10 until the last digit is not "9".
It is like a tree, and we are easy to get a sibling, a left most child and the parent of any node.

。
Lexicographical 字典
*/


public List<Integer> lexicalOrder(int n) {
        List<Integer> list = new ArrayList<>(n);
        int curr = 1;
        for (int i = 1; i <= n; i++) {
            list.add(curr);
            if (curr * 10 <= n) {
                curr *= 10;
            } else if (curr % 10 != 9 && curr + 1 <= n) {
                curr++;
            } else {
                while ((curr / 10) % 10 == 9) {
                    curr /= 10;
                }
                curr = curr / 10 + 1; //注意最后一次循环只是进行了判定然后就退出来循环，但是没有做最后一次的/10，所以这里再做一次/10操作。
            }
        }
        return list;
    }
