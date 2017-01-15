class FindCelebrity {

/*
下面这种方法是网上比较流行的一种方法，设定候选人res为0，原理是先遍历一遍，对于遍历到的人i，若候选人res认识i，则将候选人res设为i. 因为会便利所有的元素，所以如果有名人的话，同时利用题目说明的假设也就是最多只会有一个名人，所以在便利元素的时候，肯定会遇到这个名人，名人肯定会赋值到res去，但接下来因为名人一个人都不认识，res会一直保持名人这个Candidate。

当然也有可能不存在名人，那么下一步就是具体再真的判断一次这个人到底是不是名人就行了。

完成一遍遍历后，我们来检测候选人res是否真正是名人，我们如果判断不是名人，则返回-1，如果并没有冲突，返回res，.
代码有点是C++的，而且不符合规范，但是大体思想已经是这样了。
*/

  public   int findCelebrity(int n) {
        int res = 0;
        for (int i = 0; i < n; ++i) {
            if (knows(res, i)) res = i;
        }
        for (int i = 0; i < n; ++i) {
        //如果Cadidate认识某个人，或者某个人不认识cadidate,都说明candidate其实不是名人
            if (res != i && (knows(res, i) || !knows(i, res))) return -1; 
        }
        return res;
    }
};
