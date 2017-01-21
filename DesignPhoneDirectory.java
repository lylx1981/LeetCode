/**  
     * 思路：用queue和hashset。这题也许只是考查怎么设计才能使得速度提高到O(1)，所有的速度都是O(1);
     * 用Queue的好处是之前释放的号码下一次应该被优先分配
     * LeetCode推荐的JAVA答案也是这个思路。貌似C++的实现比较麻烦，总是会导致Time Limit Excceeds (TLE)
     */
     
public class PhoneDirectory {  
  
    /** Initialize your data structure here 
        @param maxNumbers - The maximum numbers that can be stored in the phone directory. */  
    private int maxNumbers;  
    private HashSet<Integer> signout;  
    private Queue<Integer> queue;  
    public PhoneDirectory(int maxNumbers) {  
        this.maxNumbers = maxNumbers;  
        signout= new HashSet<Integer>();  
        queue = new LinkedList<Integer>();  
        for(int i=0; i<maxNumbers; i++) {  
            queue.offer(i);  
        }  
    }  
      
    /** Provide a number which is not assigned to anyone. 
        @return - Return an available number. Return -1 if none is available. */  
    public int get() {  
        Integer num = queue.poll();  
        if(num == null) {  
            return -1;  
        }  
        signout.add(num);  
        return num;  
    }  
      
    /** Check if a number is available or not. */  
    public boolean check(int number) {  
        if(number<0 || number>=this.maxNumbers){  
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
