public class RemoveDuplicatesFromSortedArray {
	/**
	 * @param A: a array of integers
	 * @return : return an integer
	 */ 


	// 第一种解法，快慢指针，快指针一直与慢指针对应的值相比较。
	public int removeDuplicates(int[] A) {
        //注意边界情况，比如数组是空指针，或者空数组。数组个数为1的情况，已经可以被下面的正常代码所覆盖。
		if (A == null || A.length == 0) {
			return 0; 
		}

		int slow = 0;
		for (int i = 1; i < A.length; i++) {
			if (A[i] != A[slow]) {
				A[++slow] = A[i];
			}
		}
		return slow + 1;
	}

	//另一种解法    
	//2个指针快慢遍历，当fast扫到2个相邻的不一样时候 slow 才动，也就是比较快指针相邻的两个值  

		public int removeDuplicates(int[] A) {

			if (A == null || A.length <= 1) {
				return A == null ? 0 : A.length;
			}
			int index = 1; //index为慢指针
			//i是快指针
			for (int i = 1; i < A.length; ++i) {
				if (A[i] != A[i - 1]) {//快扫描到不同时候 慢才加
					A[index++] = A[i];
				}
			}
			return index;
		}
	
		//本题变形，如果只要求返回Unique Count，不要求数组里面的值进行调整的话，更简单。        
		public  int countUnique(int[] A) {
			int count = 0;
			for (int i = 0; i < A.length - 1; i++) {
				if (A[i] == A[i + 1]) {
					count++;
				}
			}
			return (A.length - count);
		}           

	}
