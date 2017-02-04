/**
 * 思路：数学题，记住就好，注意观察规律，写几个数字试试看，从最简单的几个数字写写，来发现规律！！！。
 * 
 * 
   //一个分析，看看在不知道数学定理之前，自己是怎么发现规律的


        /*Let's take a couple of example to check the input and expected output of the given problem.
        Example 1:
        Input: 267
        Step 1: 2+6+7 = 15
        Step 2: 1+5 = 6 (Expected output as this number has single digit)
        
        Example 2:
        Input: 7714
        Step 1: 7+7+1+4 = 19
        Step 2: 1+ 9 = 10
        Step 3: 1+0 = 1 (Expected output as this number has only 1 digit)
        
        By looking at the above examples the first solution that comes to mind is that we can take the input number and find the sum of individual digits by using recursion (or iteration). If the result is not a single digit code we ill process the result again and keep doing it until it returns a single digits. This methods is correct and works for all the valid inputs. The code for this algorithm can be writtes as follows:
        public int addDigits(int num) {
        while(num/10>0){
        num = sumDigits(num);
        }
        return num;
        }
        
        public int sumDigits(int n){
            if(n==0)
                return 0;
            return (n%10) + sumDigits(n/10);
        }
        Can we find anything better than the above solution. Let's take a couple of more example and see if we can deduce some pattern for the result:
        Example 3:
        Input: 10
        Step 1: 1+0 = 1 (Expected output)
        
        Example 4:
        Input: 11
        Step 1: 1+1 = 2 (Expected output)
        
        Example 5:
        Input: 12
        Step 1: 1+2 = 3 (Expected output)
        
        Example 6:
        Input: 18
        Step 1: 1+8 = 9 (Expected output)
        
        Arguments: In mathematics we have learnt that any number that is divisible by 9, the sum of the digits in the number is also divisible by 9. Also, here we know that the result of the problem is an integer lying in the range [0,9] .
        
        From the above arguments and samples, we can see that the result depends on the divisibility of a number by 9. The code can be written as follows:*/

public int addDigits(int num) {
    if(num<10)
        return num;
    else if(num%9 ==0)
        return 9;
    else
        return num%9;        
}  

/* The problem, widely known as digit root problem, has a congruence formula:
    
    https://en.wikipedia.org/wiki/Digital_root#Congruence_formula
    For base b (decimal case b = 10), the digit root of an integer is:
    
    dr(n) = 0 if n == 0
    dr(n) = (b-1) if n != 0 and n % (b-1) == 0
    dr(n) = n mod (b-1) if n % (b-1) != 0
    or
    
    dr(n) = 1 + (n - 1) % 9  -- 只要知道这个知识，这道题就什么问题都不是了！！！！
    Note here, when n = 0, since (n - 1) % 9 = -1, the return value is zero (correct).
    
    From the formula, we can find that the result of this problem is immanently periodic, with period (b-1).
    
    Output sequence for decimals (b = 10):
    
    ~input: 0 1 2 3 4 ...
    output: 0 1 2 3 4 5 6 7 8 9 1 2 3 4 5 6 7 8 9 1 2 3 ....
    
    Henceforth, we can write the following code, whose time and space complexities are both O(1).
    
    class Solution {
    public:
        int addDigits(int num) {
            return 1 + (num - 1) % 9;
        }
    };



 */
class Solution {
    public int addDigits(int num) {
            return 1 + (num - 1) % 9;
    }
}

