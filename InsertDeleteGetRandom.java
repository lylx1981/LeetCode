public class RandomizedSet {
    /*
      主要思路是使用ArrayList用来支持快速有效的Remove和Add操作，
      同时使用Hashmap来快速记录位置信息从而能快速找到一个元素
    */
    ArrayList<Integer> nums; // Arraylist用来存放元素
    HashMap<Integer, Integer> locs; // 键值对中K是一个元素的值，V是该元素在nums里出现的位置
    
    java.util.Random rand = new java.util.Random();
    /** Initialize your data structure here. */
    public RandomizedSet() {
        nums = new ArrayList<Integer>();
        locs = new HashMap<Integer, Integer>();
    }
    
    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        boolean contain = locs.containsKey(val);
        if ( contain ) return false;
        locs.put( val, nums.size());
        nums.add(val);
        return true;
    }
    
    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        boolean contain = locs.containsKey(val);
        if ( ! contain ) return false;
        int loc = locs.get(val);
        //如果删除的是一个nums里不在最后一个位置的元素，则该元素删除后，把当前最后一个元素挪到该位置上
        if (loc < nums.size() - 1 ) { // not the last one than swap the last one with this val
            int lastone = nums.get(nums.size() - 1 );
            nums.set( loc , lastone );
            locs.put(lastone, loc);
        }
        locs.remove(val); //同时对于删除这个元素，也同时删除其在Locs里面的键值对
        nums.remove(nums.size() - 1);
        return true;
    }
    
    /** Get a random element from the set. */
    public int getRandom() {
        return nums.get( rand.nextInt(nums.size()) );
    }
}
