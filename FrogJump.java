/*思路： 非常巧，就从前往后一次判断每个石头就可以了，同时建立一个Map，Map中Key是石头ID，Value是一个Set，是从当前石头能跳几步。不停的更新Map，然后只要能Reach到最后一个节点，那么就返回True就可以了。自己还在想DP，没想出来。

Use map to represent a mapping from the stone (not index) to the steps that can be taken from this stone.

so this will be

[0,1,3,5,6,8,12,17]

{17=[], 0=[1], 1=[1, 2], 3=[1, 2, 3], 5=[1, 2, 3], 6=[1, 2, 3, 4], 8=[1, 2, 3, 4], 12=[3, 4, 5]} 这个Set是逐渐构造出来的。

Notice that no need to calculate the last stone.

On each step, we look if any other stone can be reached from it, if so, we update that stone's steps by adding step, step + 1, step - 1. If we can reach the final stone, we return true. No need to calculate to the last stone.

Here is the code:*/

    public boolean canCross(int[] stones) {
        if (stones.length == 0) {
         return true;
        }
        
        HashMap<Integer, HashSet<Integer>> map = new HashMap<Integer, HashSet<Integer>>(stones.length);
        map.put(0, new HashSet<Integer>());
        map.get(0).add(1); //0这个石头，现在能从它跳1步
        for (int i = 1; i < stones.length; i++) {
         map.put(stones[i], new HashSet<Integer>() );
        }
        
        for (int i = 0; i < stones.length - 1; i++) {
         int stone = stones[i];
         for (int step : map.get(stone)) {
          int reach = step + stone; //看看现在从这个石头能跳到哪里，Step就是每个在当前Strone对应能跳多少步的Set里。
          if (reach == stones[stones.length - 1]) {
           return true;
          }
          HashSet<Integer> set = map.get(reach);//把能跳到的那个石头的Set取出来，更新其Set
          if (set != null) {
              set.add(step); //因为是现在跳了Step步跳到了Reach，那么这一步也应该加入到Reach这个石头的Set里
              if (step - 1 > 0) set.add(step - 1); //同理，只要step-1>0, step-1步也应该加入到其中
              set.add(step + 1); //step+1也是。
          }
         }
        }
        
        return false;
    } 
