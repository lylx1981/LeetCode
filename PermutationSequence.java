/*/
//思路： 思路：基本山自己想出来了，就是注意发现模式，思路和下面的英文分析一样，非常清晰。就是一步步向内定位，每一轮中第一个字母变一次，后面n-1个字符都经历了一次全排列，也就是跨越了（n-1）!个。另外，代码写的非常简洁易懂，值得学习。

另外这个题刚开始有一个k--的地方是特别要注意的地方，原因是当k正好整除(n-1)!的时候，会约到下一个Block的第一个去，而真正我们找的是上一个Block的最后一个，所以k--是用来避免这种情况的，这是这道题唯一的一点注意，这个和另外一道数学题一模一样，也是在做定位的时候要有类似这里k--的情况。


I'm sure somewhere can be simplified so it'd be nice if anyone can let me know. The pattern was that:

say n = 4, you have {1, 2, 3, 4}

If you were to list out all the permutations you have

1 + (permutations of 2, 3, 4)

2 + (permutations of 1, 3, 4)

3 + (permutations of 1, 2, 4)

4 + (permutations of 1, 2, 3)


We know how to calculate the number of permutations of n numbers... n! So each of those with permutations of 3 numbers means there are 6 possible permutations. Meaning there would be a total of 24 permutations in this particular one. So if you were to look for the (k = 14) 14th permutation, it would be in the

3 + (permutations of 1, 2, 4) subset.

To programmatically get that, you take k = 13 (subtract 1 because of things always starting at 0) and divide that by the 6 we got from the factorial, which would give you the index of the number you want. In the array {1, 2, 3, 4}, k/(n-1)! = 13/(4-1)! = 13/3! = 13/6 = 2. The array {1, 2, 3, 4} has a value of 3 at index 2. So the first number is a 3.

Then the problem repeats with less numbers.

The permutations of {1, 2, 4} would be:

1 + (permutations of 2, 4)

2 + (permutations of 1, 4)

4 + (permutations of 1, 2)

But our k is no longer the 14th, because in the previous step, we've already eliminated the 12 4-number permutations starting with 1 and 2. So you subtract 12 from k.. which gives you 1. Programmatically that would be...

k = k - (index from previous) * (n-1)! = k - 2*(n-1)! = 13 - 2*(3)! = 1

In this second step, permutations of 2 numbers has only 2 possibilities, meaning each of the three permutations listed above a has two possibilities, giving a total of 6. We're looking for the first one, so that would be in the 1 + (permutations of 2, 4) subset.

Meaning: index to get number from is k / (n - 2)! = 1 / (4-2)! = 1 / 2! = 0.. from {1, 2, 4}, index 0 is 1


so the numbers we have so far is 3, 1... and then repeating without explanations.


{2, 4}

k = k - (index from pervious) * (n-2)! = k - 0 * (n - 2)! = 1 - 0 = 1;

third number's index = k / (n - 3)! = 1 / (4-3)! = 1/ 1! = 1... from {2, 4}, index 1 has 4

Third number is 4


{2}

k = k - (index from pervious) * (n - 3)! = k - 1 * (4 - 3)! = 1 - 1 = 0;

third number's index = k / (n - 4)! = 0 / (4-4)! = 0/ 1 = 0... from {2}, index 0 has 2

Fourth number is 2


Giving us 3142. If you manually list out the permutations using DFS method, it would be 3142. Done! It really was all about pattern finding.
*/



public class Solution {
public String getPermutation(int n, int k) {
    int pos = 0;
    List<Integer> numbers = new ArrayList<>();
    int[] factorial = new int[n+1];
    StringBuilder sb = new StringBuilder();
    
    // create an array of factorial lookup
    int sum = 1;
    factorial[0] = 1;
    for(int i=1; i<=n; i++){
        sum *= i;
        factorial[i] = sum;
    }
    // factorial[] = {1, 1, 2, 6, 24, ... n!}
    
    // create a list of numbers to get indices
    for(int i=1; i<=n; i++){
        numbers.add(i); //这个地方非常巧，把每个数字排进去
    }
    // numbers = {1, 2, 3, 4}
    
    k--; //注意这里的k--，是最容易出错的地方，看最上边的解释
    
    for(int i = 1; i <= n; i++){
        int index = k/factorial[n-i]; //计算现在要跨越几个Block
        sb.append(String.valueOf(numbers.get(index))); //当前在numbers里面,index位置上的那个数（index位置其实就是对应了第index+1个数，所以正好是已经跨越了index个block了！！），就是当前这一位要加的数
        numbers.remove(index); //把当前这个数从numbers去除，这一步特别重要，非常巧，这样numbers里面一直都是有序的，只是有数的缺失。注意numbers这里不是数组，是个List！！！！
        k-=index*factorial[n-i]; //因为跨越了index个Block，每个Block有factorial[n-i]个全排列，所以把他们从k中减去，然后从更新后的k值继续开始
    }
    
    return String.valueOf(sb);
}
}
