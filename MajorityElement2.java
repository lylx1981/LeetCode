public class Solution {
    /**
     * @param nums: A list of integers
     * @return: The majority number that occurs more than 1/3
     */
    /*   投票法，注意是找the majority for “more than” ceiling of [n/3]，所以最多只有2个元素，所以有两个投票变量就可以了，找数组中最多的两个元素
    另外注意找到最多的两个元素之后，需要再次计算它们具体的个数，以判断它们是否满足 “more than” ceiling of [n/3]这个条件。
     For those who aren't familiar with Boyer-Moore Majority Vote algorithm,
    I found a great article (http://goo.gl/64Nams) that helps me to understand this fantastic algorithm!!
    Please check it out!

    The essential concepts is you keep a counter for the majority number X. If you find a number Y that is not X, the current counter should deduce 1. The reason is that if there is 5 X and 4 Y, there would be one (5-4) more X than Y. This could be explained as "4 X being paired out by 4 Y".

    And since the requirement is finding the majority for more than ceiling of [n/3], the answer would be less than or equal to two numbers.
    So we can modify the algorithm to maintain two counters for two majorities.
    */
    public int majorityNumber(ArrayList<Integer> nums) {
        int candidate1 = 0, candidate2 = 0;
        int count1, count2;
        count1 = count2 = 0;
        for (int i = 0; i < nums.size(); i++) {
            if (candidate1 == nums.get(i)) {
                count1 ++;
            } else if (candidate2 == nums.get(i)) {
                count2 ++;
            } else if (count1 == 0) {
                candidate1 = nums.get(i);
                count1 = 1;
            } else if (count2 == 0) {
                candidate2 = nums.get(i);
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }
        count1 = count2 = 0;
        //上面只是找出最多的两个元素，但是它们具体有多少个需要重新计算，因为上面的投票过程已经把count信息弄乱了，所以需要再过一遍数组
        for (int i = 0; i < nums.size(); i++) {
            if (nums.get(i) == candidate1) {
                count1++;
            } else if (nums.get(i) == candidate2) {
                count2++;
            }
        }    
        return count1 > count2 ? candidate1 : candidate2; //感觉九章的这一步不太对，没有判断是否大于1/3
        //应该是下面的代码才对(需要适当修改下面的代码) 
        if (count1 > len / 3)	result.add(number1);
        if (count2 > len / 3)	result.add(number2);
        return result;
    }
}
