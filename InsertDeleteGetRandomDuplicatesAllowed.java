public class RandomizedCollection {
    /*
      与之前的“ Insert Delete GetRandom O(1)”是姐妹题，
      这个题里面允许元素有重复，所以主要的改动在locs这个Hashmap里，原来（K，V）键值对里的V的值是K在ArrayList nums里的位置
      而现在因为可能有重复元素，所以V现在是一个Set类型，存储所有K在nums出现的位置
    */
    ArrayList<Integer> nums;
	HashMap<Integer, Set<Integer>> locs; //存储位置信息，K就是某个值，V是K出现在nums里的具体位置，是一个Set对象
	java.util.Random rand = new java.util.Random();
	
    /** Initialize your data structure here. */
    public RandomizedCollection() {
        nums = new ArrayList<Integer>();
	    locs = new HashMap<Integer, Set<Integer>>();
    }
    
    /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
    public boolean insert(int val) {
        boolean contain = locs.containsKey(val);
        // 这里是原作者写的为什么要用LinkedHashSet的原因：
        /*I modified the code by replacing HashSet with LinkedHashSet because the set.iterator() might be costly when a number has too many duplicates. Using LinkedHashSet can be considered as O(1) if we only get the first element to remove.*/
        
	    if ( ! contain ) locs.put( val, new LinkedHashSet<Integer>() ); //如果是第一次加入，则需要在locs里新加一个元素
	    locs.get(val).add(nums.size()); // 同时在新加元素的V位置上的Set元素中加入新加元素的位置，也就是当前nums数组的大小(因为是加到nums最后面)，注意虽然这个位置现在还是越界的，但是下面以行代码直接就把Val加入后，val的位置就是刚才上面设置的这个值。        
	    nums.add(val);
	    return ! contain ;
    }
    
    /** Removes a value from the collection. Returns true if the collection contained the specified element. */
    public boolean remove(int val) {
        boolean contain = locs.containsKey(val);
	    if ( ! contain ) return false;
	    int loc = locs.get(val).iterator().next(); //这里取到的是val所对应的位置Set里面的1个位置
	    locs.get(val).remove(loc); // 把这个位置从V的位置Set 中删除
	    if (loc < nums.size() - 1 ) { //如果删除的位置是在nums的中间，那么还要把最后一个元素挪到删除元素的位置上去，同时更新最后一个元素对应的位置Set
	       int lastone = nums.get( nums.size()-1 );
	       nums.set( loc , lastone );
	       locs.get(lastone).remove( nums.size()-1);
	       locs.get(lastone).add(loc);
	    }
	    nums.remove(nums.size() - 1); 	
   
	    if (locs.get(val).isEmpty()) locs.remove(val); //如果一个元素对应的位置Set空了，证明这个元素已经在nums里不存在了，则在Locs里删除相应的键值对
	    return true;
    }
    
    /** Get a random element from the collection. */
    public int getRandom() {
        return nums.get( rand.nextInt(nums.size()) ); //因为是对整个Size求Random，所以自动概率就线形的与元素的重复次数一致了。
    }
}
