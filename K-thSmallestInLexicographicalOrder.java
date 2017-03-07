/*思路： 非常巧的一道题，感觉和386题很像，觉得最傻的方法就是服用386题的Solution，罗列出所有的字典序后，从后往前找K个。
这道题用了更巧的方法，重点是给定cur,和cur+1，怎么确定其中涉及几步。原理是在Denary Tree中一层一层的计算他们之间的Step。

好好看看！！，另外这种树叫做denary tree。

Original idea comes from
http://bookshadow.com/weblog/2016/10/24/leetcode-k-th-smallest-in-lexicographical-order/

Actually this is a denary tree (each node has 10 children). Find the kth element is to do a k steps preorder traverse of the tree.
0_1477293053966_upload-40379731-118a-4753-bed9-1cb372790d4b

Initially, image you are at node 1 (variable: curr),
the goal is move (k - 1) steps to the target node x. (substract steps from k after moving)
when k is down to 0, curr will be finally at node x, there you get the result.

we don't really need to do a exact k steps preorder traverse of the denary tree, the idea is to calculate the steps between curr and curr + 1 (neighbor nodes in same level), in order to skip some unnecessary moves.

Main function
Firstly, calculate how many steps curr need to move to curr + 1.

if the steps <= k, we know we can move to curr + 1, and narrow down k to k - steps.

else if the steps > k, that means the curr + 1 is actually behind the target node x in the preorder path, we can't jump to curr + 1. What we have to do is to move forward only 1 step (curr * 10 is always next preorder node) and repeat the iteration.

calSteps function

how to calculate the steps between curr and curr + 1?
Here we come up a idea to calculate by level.
Let n1 = curr, n2 = curr + 1.
n2 is always the next right node beside n1's right most node (who shares the same ancestor "curr")
(refer to the pic, 2 is right next to 1, 20 is right next to 19, 200 is right next to 199) --- 这里最重要，也就是第一层n1是1，n2是2， 进入下一层循环(两者都乘以10)，n1现在是10了，n2是20了，每一层都计算一下，这也就是所谓的按层来计算，这个地方是最巧的。.

so, if n2 <= n, what means n1's right most node exists, we can simply add the number of nodes from n1 to n2 to steps.

else if n2 > n, what means n (the biggest node) is on the path between n1 to n2, add (n + 1 - n1) to steps.

organize this flow to "steps += Math.min(n + 1, n2) - n1; n1 *= 10; n2 *= 10;"

Here is the code snippet:

*/



public int findKthNumber(int n, int k) {
    int curr = 1;
    k = k - 1;
    while (k > 0) {
        int steps = calSteps(n, curr, curr + 1);
        if (steps <= k) {
            curr += 1;
            k -= steps;
        } else {
            curr *= 10;
            k -= 1;
        }
    }
    return curr;
}
//use long in case of overflow //这个是此题的核心函数，最巧！
public int calSteps(int n, long n1, long n2) {
    int steps = 0;
    while (n1 <= n) { //对数的每一层进行考察，计算步数并加入到Step里。重要！！
        steps += Math.min(n + 1, n2) - n1;
        n1 *= 10; //这里就是进入下一层
        n2 *= 10; //这里就是进入下一层
    }
    return steps;
}
