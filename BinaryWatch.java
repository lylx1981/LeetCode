
    /** 思路：比较巧的方法，与其想办法组合成每个合法的时间（貌似九章算法就是这么做的，代码很复杂），不如对所有时间的组合遍历一遍，只要其二进制表示中所有的1的个数等于输入的num, 则是一个解，加入到结果集合中。
    ×--------------注意Integer的Integer.bitCount函数，貌似很重要，要学会用，其可以统计一个数的二进制表示中1的个数。
    
    */
    
    
  public List<String> readBinaryWatch(int num) {
    List<String> times = new ArrayList<>();
    for (int h=0; h<12; h++)
        for (int m=0; m<60; m++)
            if (Integer.bitCount(m) + Integer.bitCount(h) == num) //判断1的个数是不是num
                times.add(String.format("%d:%02d", h, m));  //-- 看一下这个字符串的打印格式常识
    return times;        
}
