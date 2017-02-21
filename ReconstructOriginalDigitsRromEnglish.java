/*思路： 比较有意思的题，自己想出来了，就是发现每个0-9的数其实有的数字自己有唯一的一个字符可以作为Key（比如zero就有唯一的r字符，其他谁都没有）。但是一个数字如果没有的话这样的字符的话（比如seven 7 和6都有s），也可以和别人一起算(也就是7和6一起算) ，但是别人可能会有唯一的Key（比如6就有唯一的x），这样一旦别人的数目知道了(比如我通过统计x的数目直接可以知道6的数量)，和（7和6出现的总数量）减去别人的数目（6的数量），就是当前这个数的数目了（也就是7的数量了）。比较有意思。

The idea is:

for zero, it's the only word has letter 'z',
for two, it's the only word has letter 'w',
......
so we only need to count the unique letter of each word, Coz the input is always valid.          

*/

public String originalDigits(String s) {
    int[] count = new int[10];
    for (int i = 0; i < s.length(); i++){
        char c = s.charAt(i);
        if (c == 'z') count[0]++; //直接就是0的个数
        if (c == 'w') count[2]++; //2的个数
        if (c == 'x') count[6]++; // 6的个数
        if (c == 's') count[7]++; //7-6 -- 7和6的总个数
        if (c == 'g') count[8]++; //8的个数
        if (c == 'u') count[4]++;  //4的个数
        if (c == 'f') count[5]++; //5-4 ，4和5的总个数
        if (c == 'h') count[3]++; //3-8 3和8的总个数
        if (c == 'i') count[9]++; //9-8-5-6 总个数
        if (c == 'o') count[1]++; //1-0-2-4 总个数
    }
    count[7] -= count[6]; //7的个数已经确定
    count[5] -= count[4]; //5的个数已经确定
    count[3] -= count[8]; //3的个数已经确定
    count[9] = count[9] - count[8] - count[5] - count[6];
    count[1] = count[1] - count[0] - count[2] - count[4];
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i <= 9; i++){
        for (int j = 0; j < count[i]; j++){
            sb.append(i);
        }
    }
    return sb.toString();
}
