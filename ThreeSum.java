public class ThreeSum {

  /*
  首先给数组进行排序
  然后 大循环是遍历每一个数组的元素
  大循环里的循环是 当确定一个数组元素的时候  创建两个指针 一个指向当前元素后面的元素 一个指向末尾的元素 然后进行夹逼(要会利用排序过的数组)
  夹逼的过程 当当前的三个数之和大于0 则移动末尾的指针(这样才能让三个数之和变小)
  当当前的三个数之和小于0 则移动元素后面的指针

  记住要处理几个数字相同的情况 需要用小循环把这些相同的给跳过去
  跳过重复数字的逻辑 重点看一下 

  */
   public List<List<Integer>> threeSum(int[] num) {
	List<List<Integer>> rst = new ArrayList<>();//    必须写成这样才可以过Leetcode	
	//ArrayList<ArrayList<Integer>> rst = new ArrayList<ArrayList<Integer>>();
	if(num == null || num.length < 3) {
		return rst;
	}
	Arrays.sort(num);
	for (int i = 0; i < num.length - 2; i++) {
		if (num[i] > 0) {
			break; //提前结束，因为前两项已经〉0， 不需要再继续判断了
		}
		if (i != 0 && num[i] == num[i - 1]) {
			continue; // to skip duplicate numbers; e.g [0,0,0,0]
		}

		int left = i + 1;
		int right = num.length - 1;
		if (num[i] + num[left] > 0) {
			break; //提前结束，因为前两项已经〉0， 不需要再继续判断了 			
		}
		while (left < right) {
			int sum = num[left] + num[right] + num[i];
			if (sum == 0) {
				ArrayList<Integer> tmp = new ArrayList<Integer>();
				tmp.add(num[i]);
				tmp.add(num[left]);
				tmp.add(num[right]);
				rst.add(tmp);
				left++;
				right--;
				while (left < right && num[left] == num[left - 1]) { // to skip duplicates
					left++;
				}
				while (left < right && num[right] == num[right + 1]) { // to skip duplicates
					right--;
				}
			} 
			//可能会出现左右摇摆的情况（也就是sum一会比target大，然后又比target小），但是一旦start>=end，循环就退出了。
			else if (sum < 0) {
				left++;
			} else {
				right--;
			}
		}
	}
	return rst;
   } 
}
