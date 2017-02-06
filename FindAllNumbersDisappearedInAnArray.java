/* 思路： 自己想到的是用位操作，但是最后发现位操作还是算用来Extra Space了。Leetcode的解法比较巧，把元素是否出现过用负号进行标记，同时和数组Index联系起来，比如当前是在第i位，A[i]=5的话，说明5出现了，因为index是从0开始的，所以让5对应5-1=4的位置，现在就把4位置上的那个数置换为负数就行了。

然后再来一遍遍历，只要当前Index上的数是负数，那么（i+1）就是一个从未在数组里出现的数。非常巧的方法，不只一道题出现过这个Idea。




The basic idea is that we iterate through the input array and mark elements as negative using nums[nums[i] -1] = -nums[nums[i]-1]. In this way all the numbers that we have seen will be marked as negative. In the second iteration, if a value is not marked as negative, it implies we have never seen that index before, so just add it to the return list.

*/


    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> ret = new ArrayList<Integer>();
        
        for(int i = 0; i < nums.length; i++) {
            int val = Math.abs(nums[i]) - 1;
            if(nums[val] > 0) {
                nums[val] = -nums[val];
            }
        }
        
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] > 0) {
                ret.add(i+1);
            }
        }
        return ret;
    }
