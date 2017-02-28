public class Solution {
/*
思路： 这个题非常重要，非常值得学习，自己能够想出是用Backtracking，但是对于乘号的处理不知道怎么办，主要是乘号具有高的优先级，所以当前步选择的计算比较A+B，如果再下一步选择的是乘号和C的话，A+B结果就是不能用的，因为乘号具有高的优先级，会造成B需要和下一步的C进行先乘积运算，也就是正确的应该是A+C*B才是最后的效果，而不是简单的（A+C）×B。这道题用了非常巧的方法，也就是在当前步暂时把当前步选择的数字和运算暂时都加到中间计算结果pre中去，如果下一层运算中选择的是加号或者减号，则没有问题，可以继续在当前Pre结果上进行运算，但是如果下一步选择的是乘号，那么就把上一步的Pre里面先减去上面一步不该加的那部分，然后将不该加的那部分和当前步做乘积运算后，再结果再加到Pre，这个技巧非常非常巧（也就是让它先吃，吃完再吐出来）！！！！值得学习，看看下面这个例子就明白了。

if you have a sequence of 12345 and you have proceeded to 1 + 2 + 3, now your eval is 6 right? If you want to add a * between 3 and 4, you would take 3 as the digit to be multiplied, so you want to take it out from the existing eval. You have 1 + 2 + 3 * 4 and the eval now is (1 + 2 + 3) - 3 + (3 * 4). 

另外，这道题也要对0这样的特殊情况进行额外处理，总体思想是在从当前递归考察的Pos问题开始，如果pos位置上是0，那么这一层循环只需要截取第一个0作为前缀即可，然后Break。


This problem has a lot of edge cases to be considered:

overflow: we use a long type once it is larger than Integer.MAX_VALUE or minimum, we get over it.
0 sequence: because we can't have numbers with multiple digits started with zero, we have to deal with it too.
a little trick is that we should save the value that is to be multiplied in the next recursion.

Adding String is extremely expensive. Speed can be increased by 20% if you use StringBuilderinstead.
*/

    public List<String> addOperators(String num, int target) {
        List<String> res = new ArrayList<>();
       	StringBuilder sb = new StringBuilder();
        dfs(res, sb, num, 0, target, 0, 0);
        return res;
        
    }
    //res存储结果，pos是当前从num的哪个pos开始考察，target是最终要达到的目标，prev是到上一步为止现在计算的中间结果值是多少了，multi是为了保留上一步加入Prev的部分，以防备这一部分实际需要在下一层递归中需要和那一层的数进行乘积运算
    public void dfs(List<String> res, StringBuilder sb, String num, int pos, int target, long prev, long multi) { 
    	if(pos == num.length()) {
    		if(target == prev) res.add(sb.toString());//pos已经到num末尾，同时当前中间计算结果也是Target了，那么就找到了一个结果集，加入Res
    		return;
    	}
    	for(int i = pos; i < num.length(); i++) { //当前层循环从pos位置开始，对从pos开始依次截取不同长度的前缀，作为这一轮选择的运算数
    		if(num.charAt(pos) == '0' && i != pos) break; //如果pos位置上是0，那么这一层循环只需要截取第一个0作为前缀即可，然后Break.。
    		long curr = Long.parseLong(num.substring(pos, i + 1)); //注意这里很多用的是Long，这是这道题另外要注意的点，这样避免overflow的问题。
    		int len = sb.length(); //保存目前sb的长度，这个长度会后面用来支持回溯操作！
    		if(pos == 0) {
    			dfs(res, sb.append(curr), num, i + 1, target, curr, curr); //当开始的话，这一步，pos=0，这一步相当于初始步，稍微特殊处理一下，因为没有之前的步
    			sb.setLength(len);
    		} else { //当前步，可以有三种情况，分别进行处理即可，也就是在curr前面加上加号，减号，和乘号的情况，尤其是乘号的情况特别重要！！！
    			dfs(res, sb.append("+").append(curr), num, i + 1, target, prev + curr, curr); 
    			sb.setLength(len);//这一步其实是将sb重新设置为原来的值，也就是去掉上面这个dfs的影响，实际上就是回溯操作。
    			dfs(res, sb.append("-").append(curr), num, i + 1, target, prev - curr, -curr); 
    			sb.setLength(len);
    			dfs(res, sb.append("*").append(curr), num, i + 1, target, prev - multi + multi * curr, multi * curr); 
                //if you have a sequence of 12345 and you have proceeded to 1 + 2 + 3, now your eval is 6 right? If you want to add a * between 3 and 4, you would take 3 as the digit to be multiplied, so you want to take it out from the existing eval. You have 1 + 2 + 3 * 4 and the eval now is (1 + 2 + 3) - 3 + (3 * 4). 
    			sb.setLength(len);
    		}
    	}
    }
}
