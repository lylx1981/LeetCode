public class NextPermutation {
    /**
     * @param num: an array of integers
     * @return: return nothing (void), do not return anything, modify num in-place instead
     */
/*     
规则是这样：
如果一个数列是降序排列的（从后往前看就是升序） 那它就是不能再置换了 只能将他从新按升序排列
所以每一遍都从后往前 一共扫描三遍 
第一遍先找到那个打破升序排列的数字(从后往前),记下这个位置的数叫做a, 假设其index是ind
第二遍从后往前找到第一个比a大的数字,然后置换二者
第三遍就是把从ind之后把剩下的数列按升序排列（从前往后看）,  这样的数字才最接近之前的数字
*/ 
    public void reverse(int[] num, int start, int end) {
        for (int i = start, j = end; i < j; i++, j--) {
            int temp = num[i];
            num[i] = num[j];
            num[j] = temp;
        }
    }
    
    public void nextPermutation(int[] num) {
        // find the last increase index
        int index = -1;
        for (int i = num.length - 2; i >= 0; i--) {
            if (num[i] < num[i + 1]) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            reverse(num, 0, num.length - 1);
            return;
        }
        
        // find the first bigger one
        int biggerIndex = index + 1;
        for (int i = num.length - 1; i > index; i--) {
            if (num[i] > num[index]) {
                biggerIndex = i;
                break;
            }
        }
        
        // swap them to make the permutation bigger
        int temp = num[index];
        num[index] = num[biggerIndex];
        num[biggerIndex] = temp;
        
        // reverse the last part
        reverse(num, index + 1, num.length - 1);
    }
}
