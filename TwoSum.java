public class TwoSum {
	/*
	 * @param numbers : An array of Integer
	 * 
	 * @param target : target = numbers[index1] + numbers[index2]
	 * 
	 * @return : [index1 + 1, index2 + 1] (index1 < index2) numbers=[2, 7,
	 * 11, 15], target=9 return [1, 2]
	 
	 * Company Tags: LinkedIn, Uber, Airbnb, Facebook, Amazon, Microsoft, Apple, Yahoo, Dropbox, Bloomberg, Yelp, Adobe
	 * Tags: Array, Hash Table
	 * Similar Problems: (M) 3Sum, (M) 4Sum, (M) Two Sum II - Input array is sorted, (E) Two Sum III - Data structure
	 * design
	 */
     
         
    /*
    This problem is pretty straightforward. We can simply examine every possible pair of 
    numbers in this integer array.
    Time complexity in worst case: O(nˆ2).
    */

    public static int[] twoSum(int[] numbers, int target) {
        int[] ret = new int[2];
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[i] + numbers[j] == target) {
                    ret[0] = i;
                    ret[1] = j;
                }
            }
        }
        return ret;
    }
    
    
    /*
    Use HashMap to store the target value.
    Time complexity depends on the put and get operations of HashMap which is normally O(1).
    Time complexity of this solution is O(n). 
    */
    public int[] twoSum(int[] numbers, int target) {
        int[] result = new int[2];
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < numbers.length; i++) {
            if (map.containsKey(target - numbers[i])) {
                result[1] = i + 1;
                result[0] = map.get(target - numbers[i]);
                return result;
            }
            map.put(numbers[i], i + 1);
        }
        return result;
    }
 
}

