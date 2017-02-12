
    /*
    思路：自己想到的比较傻，想到先把一个数字的因子全部分解后，用类似Devide&Corque的方法，对左右两边递归求解其各种组合，然后再把左右两边的组合再组合起来。同时其实这样的方法是不符合题意的，因为题意要求，对于每个结果，其因子是递增顺序的。
    
    Leetcode的解法是利用DFS和Backtracking，而且是直接对数字n进行判断，从最小的factor也就是2开始依次向上递增。每遇到一个能够整除n的factor,就将其加入当前中国结果List，然后继续对剩余的量继续进行判断。
*/

public List<List<Integer>> getFactors(int n) {
    List<List<Integer>> result = new ArrayList<List<Integer>>();
    helper(result, new ArrayList<Integer>(), n, 2); //从最小的Factor是2开始判断即可。
    return result;
}

public void helper(List<List<Integer>> result, List<Integer> item, int n, int start){
    if (n <= 1) {
        if (item.size() > 1) {
            result.add(new ArrayList<Integer>(item)); //将当前结果加入最终结果集即可
        }
        return;
    }
    
    for (int i = start; i <= n; ++i) {
        if (n % i == 0) { //只需要对能够整除n的i判断即可，因为这些i将是n的因子。
            item.add(i);//将当前i将入到中间结果集中
            helper(result, item, n/i, i); //因为去除了i,那么原来的n还剩下n/i, 那么继续递归，同时注意现在最小Factor直接从i开始。
            item.remove(item.size()-1); //回溯步骤，去掉当前这个i，继续让i增长对更大的i进行判断@！
        }
    }
}
