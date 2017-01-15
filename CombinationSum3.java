public class CombinationSum3 {

  /*
  和Combination2差不多，可以从Combination2的代码改过来，主要有几点改变：
  1. 达到target的时候，size的条件也要满足。
  2. 如果当前值已经大于target,或者size已经超了，那么直接退出就行了。
     if (n - i >= 0) { // n < i can be skipped
     if (comb.size() > k) return; // no need to search in k+1 numbers
  3. 所有的值只能从1-9中选，不能大于9.
  4. //combinationSum3形参这里的n其实就是Combination2中的target参数
  */
  
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> ans = new ArrayList<>();
        backtrack(ans, new ArrayList<>(), k, 1, n);
        return ans;
    }

    private void backtrack(List<List<Integer>> ans, List<Integer> comb, int k, int start, int n) {
        if (comb.size() > k) return; // no need to search in k+1 numbers

        if (comb.size() == k && n == 0) { // combination found
            List<Integer> res = new ArrayList<>(comb); // make a copy of the list
            ans.add(res);
            return;
        }

        for (int i = start; i <= 9; i++) {
            if (n - i >= 0) { // n < i can be skipped
                comb.add(i);
                backtrack(ans, comb, k, i + 1, n - i); //i+1这里保证了每个元素只会选择一次，所以肯定会unique。
                comb.remove(comb.size() - 1);
            }
        }
    }
    
      public static void main(String[] args) {
        CombinationSum3 cs = new CombinationSum3();
        System.out.println(cs.combinationSum3(3, 7));
        System.out.println(cs.combinationSum3(3, 9));
    }
    
}
