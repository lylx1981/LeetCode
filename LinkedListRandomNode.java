/*
思路：就是用Heap，把第一行的所有元素先进最小堆，然后开始往外Pop出K个即可，每Pop出一个，如果该元素的同列下一行元素还存在的话，就把那个元素继续加入最小堆即可。如果这样做的话感觉没有同时使用每行每列都是有序的这个性质。


引言：众所周知，想要面试一个统计学家和软件工程师的合体——数据工程师——是件很难的事情。我在面试中常使用的方法是：提出即需要算法设计，又需要一些概率论知识的问题，来考察面试者的功底。下面就是在硅谷非常流行的例子：

“给出一个数据流，这个数据流的长度很大或者未知。并且对该数据流中数据只能访问一次。请写出一个随机选择算法，使得数据流中所有数据被选中的概率相等。”

当面对这样一个问题的时候，我们首先应该做的是：镇静。你的面试官并没有玩你，相反他可能特别想雇你。他可能正在为无尽的分析请求烦恼，他的ETL流水线已经不在工作，已有的机器学习模型也不再适合。他正想要你这样一个聪明人进来帮忙，他希望你答出来。

第二件要做的事情是：不要在没有深入思考的情况下盲目作答。假设你的面试官读过Daniel Tunkelang的关于数据工程师的面试建议，那么这个面试题很可能就是他工作中实际遇到的问题。所以如果像下面一样随便回答，很可能会令你的面试官失望。

“我会首先将输入存到一个列表中，统计出数据流中数据的个数，在读取结束之后随机选取一个”（大哥， 你没看见题目已经说了，数据流长度很大或者未知么，不怕你的内存装不下？）

第三件要做的事情是：从小例子开始分析。大部分的人都更容易解决具体问题（而不是抽象问题），最开始你设计的小例子可能和最后的问题之间相去甚远，但是却能启发你对问题的理解，给你灵感。

 

蓄水池算法

如前面所说，对这个问题我们首先从最简单的例子出发：数据流只有一个数据。我们接收数据，发现数据流结束了，直接返回该数据，该数据返回的概率为1。看来很简单，那么我们试试难一点的情况：假设数据流里有两个数据。

我们读到了第一个数据，这次我们不能直接返回该数据，因为数据流没有结束。我们继续读取第二个数据，发现数据流结束了。因此我们只要保证以相同的概率返回第一个或者第二个数据就可以满足题目要求。因此我们生成一个0到1的随机数R,如果R小于0.5我们就返回第一个数据，如果R大于0.5，返回第二个数据。

接着我们继续分析有三个数据的数据流的情况。为了方便，我们按顺序给流中的数据命名为1、2、3。我们陆续收到了数据1、2和前面的例子一样，我们只能保存一个数据，所以必须淘汰1和2中的一个。应该如何淘汰呢？不妨和上面例子一样，我们按照二分之一的概率淘汰一个，例如我们淘汰了2。继续读取流中的数据3，发现数据流结束了，我们知道在长度为3的数据流中，如果返回数据3的概率为1/3,那么才有可能保证选择的正确性。也就是说，目前我们手里有1,3两个数据，我们通过一次随机选择，以1/3的概率留下数据3，以2/3的概率留下数据1.那么数据1被最终留下的概率是多少呢？

数据1被留下：（1/2）*(2/3) = 1/3
数据2被留下概率：（1/2）*(2/3) = 1/3
数据3被留下概率：1/3
这个方法可以满足题目要求，所有数据被留下返回的概率一样！

因此，我们做一下推论：假设当前正要读取第n个数据，则我们以1/n的概率留下该数据，否则留下前n-1个数据中的一个。以这种方法选择，所有数据流中数据被选择的概率一样。简短的证明：假设n-1时候成立，即前n-1个数据被返回的概率都是1/n-1,当前正在读取第n个数据，以1/n的概率返回它。那么前n-1个数据中数据被返回的概率为：(1/(n-1))*((n-1)/n)= 1/n，假设成立。

这就是所谓的蓄水池抽样算法。它在分析一些大数据集的时候非常有用。你可以在这里找到Greg写的关于蓄水池抽样的算法介绍。本文后面会介绍一下在Cloudera ML中使用的两种：分布式蓄水池抽样和加权分布式蓄水池抽样。


*/

/*When I first got this question, I went through some articles, but it is painful for me to understand abstract notations like i, k, m, n, n-1, k+1...

After I read this one: http://blog.jobbole.com/42550/, it comes with a simple example and I understood suddenly, and write the code by myself. I translate it to English, so more people can benefit from it.

Start...
When we read the first node head, if the stream ListNode stops here, we can just return the head.val. The possibility is 1/1.

When we read the second node, we can decide if we replace the result r or not. The possibility is 1/2. So we just generate a random number between 0 and 1, and check if it is equal to 1. If it is 1, replace r as the value of the current node, otherwise we don't touch r, so its value is still the value of head.

When we read the third node, now the result r is one of value in the head or second node. We just decide if we replace the value of r as the value of current node(third node). The possibility of replacing it is 1/3, namely the possibility of we don't touch r is 2/3. So we just generate a random number between 0 ~ 2, and if the result is 2 we replace r.*/


public class Solution {
    
    ListNode head;
    Random random;
    
    public Solution(ListNode head) {
        this.head = head;                
    }
    
    public int getRandom() {
        
        ListNode c = head;
        int r = c.val;
        for(int i=1;c.next != null;i++){
            
            c = c.next;
            if(randInt(0,i) == i) r = c.val; //根据蓄水池算法就是我从头到尾一直只保存一个r, 然后从Head来读整个流，每次以1/i的概率来替换当前的r,直到最后末尾，返回r即可。                    
        }
        
        return r;
    }
    
    private int randInt(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }
}
