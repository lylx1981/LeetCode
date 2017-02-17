/*思路：这道题自己想了几种办法，一种是遍历一遍得到最大深度，然后既然最大深度知道了，那么就可以再次调用之前的Nested List Weight Sum的解，层数从最大深度开始，每进一层循环，层数减1即可。第二种方法是还是遍历一次数组，每检查一个单个元素把当前元素，以及当前层数作为一个K,V键值对加入到一个Set里，然后同时记录最大深度，最后对Set里每个元素进行检查，用最大深度减去V的值就是K元素对应的乘数。

看了Leetcodes上面的解法后，觉得很巧妙，见如下分析：

下面这个方法就比较巧妙了，由史蒂芬大神提出来的，这个方法用了两个变量unweighted和weighted，非权重和跟权重和，初始化均为0，然后如果nestedList不为空开始循环，先声明一个空数组nextLevel，遍历nestedList中的元素，如果是数字，则非权重和加上这个数字，如果是数组，就加入nextLevel，这样遍历完成后，第一层的数字和保存在非权重和unweighted中了，其余元素都存入了nextLevel中，此时我们将unweighted加到weighted中，将nextLevel赋给nestedList，这样再进入下一层计算，由于上一层的值还在unweighted中，所以第二层计算完将unweighted加入weighted中时，相当于第一层的数字和被加了两次，这样就完美的符合要求了*/


public int depthSumInverse(List<NestedInteger> nestedList) {
    int unweighted = 0, weighted = 0;
    while (!nestedList.isEmpty()) {
        List<NestedInteger> nextLevel = new ArrayList<>();
        for (NestedInteger ni : nestedList) {
            if (ni.isInteger())
                unweighted += ni.getInteger();
            else
                nextLevel.addAll(ni.getList());
        }
        weighted += unweighted;
        nestedList = nextLevel;
    }
    return weighted;
}
