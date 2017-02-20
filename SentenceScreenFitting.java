/*思路： 这道题比较费劲，Leetcode最受欢迎的解法不是自己想到的，自己想的是和下面这个解法类似，有点像存储各种模式的意思，就是对于每个sentence里面的Word，我计算一下以这个Word开始一行的话，那么下一行应该开始的是哪个Word。把这个结果记录在nextIndex中。另外一个，如果Cols很大，可能造成以当前Worrd开始写这一行的话，可能写到Sentence末尾以后这一行还没有结束，那么我就要重新在从sentence的第0个Word开始写，同时我记录一下一共跨越了几次Stentence的最后一个Word。

对每个在Sentence上的Word都进行了上述计算后，相当于构建了一个模式，那么接下来就从第0行开始，同时从setence的第0个Word开始，每个Word对应的Times就加到结果Res上，同时，通过nextIndex也就知道下一行是以谁开头，然后继续计算下一行以那个Word开头的情况，一直到所有行都考察完毕，返回res即可,非常巧！

First off, we can easily come up with a brute-force solution. The basic idea of optimized solution is that

sub-problem: if there's a new line which is starting with certain index in sentence, what is the starting index of next line (nextIndex[]). BTW, we compute how many times the pointer in current line passes over the last index (times[]).
relation : ans += times[i], i = nextIndex[i], for _ in 0..<row. where i indicates what is the first word in the current line.
Time complexity : O(n*(cols/lenAverage)) + O(rows), where n is the length of sentence array, lenAverage is the average length of the words in the input array.

Well, It's not a typical "DP" problem and I am not even sure it is a "DP" problem. ( ͡° ͜ʖ ͡°)*/

public int wordsTyping(String[] sentence, int rows, int cols) {
        int[] nextIndex = new int[sentence.length];
        int[] times = new int[sentence.length];
        for(int i=0;i<sentence.length;i++) {
            int curLen = 0;//记录当前长度，每次都从0开始
            int cur = i; //起始这一行第一个Word从i这个Word开始
            int time = 0;
            while(curLen + sentence[cur].length() <= cols) {
                curLen += sentence[cur++].length()+1; //+1是后面要多加一个空格
                if(cur==sentence.length) {
                    cur = 0; //重新从sentence的第一个元素开始写
                    time ++; //记录当前又跨越了sentence的最后一个Word一次，代表又完整多写了一次，注意对于这一行末尾又写了一半但是还没有跨越最后一个的情况，不用管它，因为这些写出来的会归在写下一行时遇到sentence的最后一个Word的里面。
                }
            }
            nextIndex[i] = cur;
            times[i] = time;
        }
        int ans = 0;
        int cur = 0;
        for(int i=0; i<rows; i++) {
            ans += times[cur];//这一行一共能贡献多少次倍数
            cur = nextIndex[cur]; //下一行应该是由哪个Word开始
        }
        return ans;
}
