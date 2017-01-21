
    /**  
     * 思路：用queue和hashset。这题也许只是考查怎么设计才能使得速度提高到O(1)，所有的速度都是O(1);
     * 用Queue的好处是之前释放的号码下一次应该被优先分配
     * LeetCode推荐的JAVA答案也是这个思路。貌似C++的实现比较麻烦，总是会导致Time Limit Excceeds (TLE)
     * 
     * 另外注意这道题主要考察对各种数据结构性能的认知，很多选择不对的数据结构最后都造成了TLE
     * 
     * 另外可以参看下面Leetcode有关的讨论，为什么要用Queue，为什么不也用一个HashSet来存储available numers
     * 
     * some disussion on LeetCode Discuss:


        Quesiton: why uses a quenue not a hashset for the avaialbe nums? why i cannot just get the first element in a HashSet storing the available numbers?
        
        Answer: How do you get "the first element", and how do you know it's O(1) if using HashSet? In particular, JAVA doc said that "Returns an iterator over the elements in this set. The elements are returned in no particular order."
        The order is not guaranteed so you can't get "the first element" without a queue."
        
        In fact, somebody used two HashSet, and got a TLE.


     */
     
public class PhoneDirectory {  
  
    /** Initialize your data structure here 
        @param maxNumbers - The maximum numbers that can be stored in the phone directory. */  
    private int maxNumbers;  
    private HashSet<Integer> signout;  //存放已经被分配的
    private Queue<Integer> queue;   //存放availabble的
    
    public PhoneDirectory(int maxNumbers) {  
        this.maxNumbers = maxNumbers;  
        signout= new HashSet<Integer>();  
        queue = new LinkedList<Integer>();  
        for(int i=0; i<maxNumbers; i++) {  
            queue.offer(i);  //Quene用offer方法来加入元素，要记住
        }  
    }  
      
    /** Provide a number which is not assigned to anyone. 
        @return - Return an available number. Return -1 if none is available. */  
    public int get() {  
        Integer num = queue.poll();  //quene用poll方法来取出元素，要记住
        if(num == null) {  
            return -1;  
        }  
        signout.add(num);  
        return num;  
    }  
      
    /** Check if a number is available or not. */  
    public boolean check(int number) {  
        if(number<0 || number>=this.maxNumbers){  //这个边界条件一定要加，从而保证Check一直是O（1）的
            return false;  
        }  
        return !signout.contains(number);  
    }  
      
    /** Recycle or release a number. */  
    public void release(int number) {  
        if(check(number)) return;  
        queue.offer(number);  
        signout.remove(number);  
    }  
}  
