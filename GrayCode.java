/**
 * 思路: 仔细观察Gray Code可以有一些规律，按照这些规律来生成即可！！ 。

这就是个规律： If we only look at the last 2 digits, we can see that in the first sequence, the second half is exact the reverse of the first half, that means, we can systematically generate the second half according to the first half.

/**
 * I agree with loick. I don't think it's a knowledge base problem. It's also my first time to hear about Gray Code. But after trying some small cases, I still figured out an algorithm for it.

From my intuition, the problem is like Hanoi. If you're able to solve n = 2 case, then you can kind of repeat it twice to achieve n = 3 case. Lets try to extend n = 2 case to n = 3 case first.

When n = 2, the sequence is
00 -> 01 -> 11 -> 10
If you want to extend it to n=3 directly without modifying old part, there are only two possible sequence, and they are not hard to find out.

000 -> 001 -> 011 -> 010 -> 110 -> 111 -> 101 -> 100

000 -> 001 -> 011 -> 010 -> 110 -> 100 -> 101 -> 111

So now, the problem is, which one should we choose. I would choose the first one for two reasons.

The last elements have similar form in both n=2 and n=3 case. They are 1 follows bunch of 0's. Since we hope to extend the same algorithm to n=4 n=5... cases. It's good to preserve some properties.

If we only look at the last 2 digits, we can see that in the first sequence, the second half is exact the reverse of the first half, that means, we can systematically generate the second half according to the first half.

That's how I figured out the algorithm. Hope that helps!
 */
 
 
 
 
public List<Integer> grayCode(int n) {
    List<Integer> rs=new ArrayList<Integer>();
    rs.add(0);
    for(int i=0;i<n;i++){
        int size=rs.size();
        for(int k=size-1;k>=0;k--)
            rs.add(rs.get(k) | 1<<i); //这里表示将1左移i位，所以这里其实是在生成第二部分。 第一部分不用生成，当前rs里面现有的所有元素本身就已经自动成为这一轮的是第一部分了，因为第一部分只是在原有的值的二进制表示的前面添加一个0，其对应的十进制的值其实和上一轮是完全一样的，所以只用生成第二部分即可。每次，就是对RS里面的数倒着来，然后每个前面加个1， “| 1<<i” 就是为了达到这样的功能。


    }
    return rs;
}
