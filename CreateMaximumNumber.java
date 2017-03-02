/*思路： 还是比较难的。大体思想是先从每个数组里找出最大的数（要试验所有可能情况，第一个细节是这里，遍历所有可能的情况，也就是从nums1里拿i个位组成的最大的数，从nums2里就要求具有n-i位的组成的最大数），然后合并。当然里面有比较多的细节。


Many of the posts have the same algorithm. In short we can first solve 2 simpler problem

Create the maximum number of one array
Create the maximum number of two array using all of their digits.
For an long and detailed explanation see my blog here.

The algorithm is O((m+n)^3) in the worst case. It runs in 22 ms.*/


public int[] maxNumber(int[] nums1, int[] nums2, int k) {
    int n = nums1.length;
    int m = nums2.length;
    int[] ans = new int[k];
    for (int i = Math.max(0, k - m); i <= k && i <= n; ++i) {
        //第一个细节是这里，遍历所有可能的情况，也就是从nums1里拿i个位组成的最大的数，从nums2里就要求具有k-i位的组成的最大数。因为要求的最大数的位数不一样，结果是完全不相同的。因为nums2具有m长度，所以这里k-i的值最大也只能是m,所以如果k>m的情况下，i就必须是Math.max(0, k - m)，不然的话，i如果还从0开始，就要在nums2里面找k-0=k个数是不可能的（因为现在k>m）。
        int[] candidate = merge(maxArray(nums1, i), maxArray(nums2, k - i), k); //对两个数组进行合并
        if (greater(candidate, 0, ans, 0)) ans = candidate;
    }
    return ans;
}
private int[] merge(int[] nums1, int[] nums2, int k) {
    int[] ans = new int[k];
    for (int i = 0, j = 0, r = 0; r < k; ++r)
        ans[r] = greater(nums1, i, nums2, j) ? nums1[i++] : nums2[j++];
        //这里的Greater的函数非常关键和细节，非常重要，如果i,j当前位直接不一样，那还好，就按i,j对应的数值比较即可。而如果i,j位的数值一样（也就是nums1[i] == nums2[j），这个时候当前r位是要取nums1[i]还是nums2[j],主要看他们各自后面的位所代表的数字谁大，谁大就取谁，这个功能完全在greater函数里实现了，这个函数非常重要。见下面的描述：It is just like merger sort, but we should pay attention to the case: the two digital are equal. we should find the digits behind it to judge which digital we should choose now.In other words,we should judge which subarry is bigger than the other.
    return ans;
}

//注意这个函数的意思是从nums1的i位开始比较，nums2的j位开始比较，判断两者大小。所以，i,j为0时，就是从头开始比较
public boolean greater(int[] nums1, int i, int[] nums2, int j) {
    while (i < nums1.length && j < nums2.length && nums1[i] == nums2[j]) { //如果两者相等就一直比较他们后面的位
        i++;
        j++;
    }
    return j == nums2.length || (i < nums1.length && nums1[i] > nums2[j]);//j == nums2.length说明一直比较到最后一直都是相等的情况，这个时候至少nums2已经比较完了，这个时候，nums1 至少是比nums2大，所以返回True。(i < nums1.length && nums1[i] > nums2[j]);说明中途就断了，这个时候明显nums1[i]是个大的，所以返回True。
}

//对于一个数组，求含k个位的最大的数
private int[] maxArray(int[] arr, int k) {
	int n = arr.length;
	int[] res = new int[k];  	

	int last = -1; //position of previous digit 记录上一次的位是从哪里取的
	for(int i = 0; i < k ; i++) {// k positions to fetch 现在开始取k个位
		for(int j = last + 1; j + (k - i - 1) < n; j++) { //必须从last+1开始，因为last+1前面的位已经不能再取了，以为要保持相对顺序，同时j + (k - i - 1)必须小于n,也就是j只能在last+1后面的有限个位置上取，因为当前取了第i个后（i从0开始），后面还需要再取k-i-1个，j的位置取得太靠后了，后面就没法取了，所以这里要保证至少后面必须还要剩k-i-1个。
			if(res[i] < arr[j]) {
				res[i] = arr[j]; //对于每个符合约束的j,如果其位置大于当前正在考察的第i位要取的数，则用arr[j]替代res[i]
				last = j; //j现在也同时付给last
			}
		}
	}
	
	return res;
}
