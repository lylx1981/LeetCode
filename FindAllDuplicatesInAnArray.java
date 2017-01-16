public class FindAllDuplicatesInAnArray {
    /*
    原理就是从前到后遍历数组，每碰到一个数m，就把index是m-1的位置的数的值，也就是num[m-1]变为负值，这个负值其实就是相当于一个计数器。下一次如果还是第二次遇到m值的话，一旦检测m-1位置，发现nums[m-1]是个负值的话，说明之前已经有过一个m了，那么m就出现了2次。而整个过程中，m-1对应的值nums[m-1]的数值大小一直没变（只不过有符号改变而已），不耽误对nums[m-1]这个值是否出现2次的判断，只要每次判断的时候，对其取绝对值就行了，比如Math.abs(nums[i]);
    */
    // when find a number i, flip the number at position i-1 to negative. 
    // if the number at position i-1 is already negative, i is the number that occurs twice.
    
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < nums.length; ++i) {
            int index = Math.abs(nums[i])-1;
            if (nums[index] < 0)
                res.add(Math.abs(index+1));
            nums[index] = -nums[index];
        }
        return res;
    }
}
