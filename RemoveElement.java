public class Solution {
    /** 
     *@param A: A list of integers
     *@param elem: An integer
     *@return: The new length after remove 
     */
     
    // 两个指针都从0开始，i指针是慢指针,j指针是快指针  
    public int removeElement(int[] A, int elem) {
        int i = 0;
        int j = 0;
        while (j < A.length){
            if (A[j] != elem){
                A[i] = A[j];
                i++;
            }
            j++;
        }
        return i;
    }
    
    // 头尾两个指针，感觉没有上面的方法直观，但是性能会稍微好一点，因为要挪动的次数会少一点

    /*
    // This should be a little faster as fewer elements are moved.

    ----- I'm splitter -----

    EDIT: Here is the explanation of the above code.

    It scans numbers from the left to the right, one by one.

    Once it finds a number that equals to elem, it swaps current element with the last element in the array and then dispose the last.

    The swapping can be optimized as overwrite current element by the last one and dispose the last.

    Now, we have removed the current number, and the length of the array is reduced by 1.

    To ensure we do not make wrong choices, we will continue scanning from the (new) current element.

    So it won't fail if the same number is swapped to the current position.
    
    */
    
    public int removeElement(int[] A, int elem) {
        int i = 0;
        int pointer = A.length - 1;
        while (i <= pointer){
            if (A[i] == elem){
                A[i] = A[pointer];
                pointer--;
            } else {
                i++;
            }
        }
        return pointer + 1;
    }
}
