/*/
//回溯法，backtracking，当前path中每加一个元素就进入下一层递归（同时更新target值以便传入下一个递归中的target去），当target逐渐递减到0时，说明当前path中的就是一个solution,把它加入到Result里。每从一个递归中回来，就从当前path里面把最后一个元素删去（也就是所谓的backtracking操作）

另外注意这一句：result.add(new ArrayList<Integer>(path)); 这一句的意思是重新生成一个新的ArrayList，里面和path中的内容一模一样。所以这里是把新生成的Arraylist加入到result里，而不是把path加入到result里。注意这个用法非常重要！！

*/


  public class Solution {
  public  List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (candidates == null) {
            return result;
        }
        ArrayList<Integer> path = new ArrayList<Integer>();
        Arrays.sort(candidates);
        helper(candidates, target, path, 0, result);
        return result;
    }

    void helper(int[] candidates, int target, ArrayList<Integer> path, int index,
        List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<Integer>(path));
            return;
        }
        for (int i = index; i < candidates.length; i++) {
            if (candidates[i] > target) {
                break;
            }
            path.add(candidates[i]);
            helper(candidates, target - candidates[i], path, i, result);
            path.remove(path.size() - 1);
          
        }
    }
}
