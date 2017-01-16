public double findMedianSortedArrays(int A[], int B[]) {
        int len = A.length + B.length;
        if (len % 2 == 1) {
            //如果两个数组的和长度和是奇数，那么media所在的位置就是直接len / 2 + 1的位置，+1这个是主要考虑index所以从0开始 
            return findKth(A, 0, B, 0, len / 2 + 1); 
        }
        return (
            //如果两个数组的和长度是偶数，那么media根据题目的例子，其就是对靠近len/2的中间数取平均即可
            findKth(A, 0, B, 0, len / 2) + findKth(A, 0, B, 0, len / 2 + 1)
        ) / 2.0; //这个除以2.0，直接造成了可以以float数的类型计算出结果，而不是按整体计算
    }

    // find kth number of two sorted array 这个函数非常重要！！可以适用到其他题目
    // 更准确的说，应该是给定两个有序数组，同时给定各自需要开始考察的位置(A_start, B_start), 寻找两个数组合并在一起后的第k个元素。
    public static int findKth(int[] A, int A_start,
                              int[] B, int B_start,
                              int k){		
		//A_start B_start就是当前从哪个位置开始考虑，初始值都是从0开始，也就是从两个数组的0位置开始
		
		//如果当前A_start已经大于A.length了，则A数组就不再考虑了，结果就直接是B数组中从B_start位置开始的第k-1位置上的元素，它就是需要找的第k个元素了。
		if (A_start >= A.length) {
			return B[B_start + k - 1];
		}
		//同理如上
		if (B_start >= B.length) {
			return A[A_start + k - 1];
		}
		//k=1时，直接就是两个数组起始值最小的那个
		if (k == 1) {
			return Math.min(A[A_start], B[B_start]);
		}
		//分别计算对于两个数组从start位置开始，第k/2个元素的值，其位置也就是k/2-1
		//如果这个位置不存在，直接赋给Integet.Max_VALUE即可
		int A_key = A_start + k / 2 - 1 < A.length
		            ? A[A_start + k / 2 - 1]
		            : Integer.MAX_VALUE;
		int B_key = B_start + k / 2 - 1 < B.length
		            ? B[B_start + k / 2 - 1]
		            : Integer.MAX_VALUE; 
		//注意以上两个值，只可能有一个是被赋给Integet.Max_VALUE，因为Anyway，k的值计算是由当前两个数组长度除以2得到的，所以不可能造成两个都被赋给Max_VALUE的情况。
		//如果A_key < B_key，说明A数组里面的从A_start 到A_start + k / 2 - 1这个区间的所有元素(总数为2/k个)“必定”都在两个数组合并起来的前"k"的元素中(注意这里是前k个！！！！！，最重要的点就在这里), 因为最坏可能也就是B数组中的从B_start 到B_start + k / 2 - 1的那个k/2元素也都也两个数组合并起来的前k个元素中，那么他们加起来的结果仍然是k,符合条件。 所以，综上所述，必定现在需要找个第k个元素一定都比A数组中这k/2个元素大。
	
		if (A_key < B_key) {
		    	/*所以我只需要做的就是，剔除掉这k/2个元素，然后A数组继续从A_start + k / 2位置继续考察，而B数组仍然从当前的B_start位置考察，并且最重要的，我现在只需要找对于这两个新的考察位置开始的第k - k/2个元素就行了，因为刚才已经剔除掉k/2个了。*/
		    	//注意这里一次剃掉一半的k就达到了log的效果
			return findKth(A, A_start + k / 2, B, B_start, k - k / 2);
		} else {
		    //原理同上
			return findKth(A, A_start, B, B_start + k / 2, k - k / 2);
		}
	}
