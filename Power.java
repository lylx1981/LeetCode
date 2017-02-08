/*/
//思路： 题目本身不难，主要注意n可能是负数的情况，以及n是奇数或者偶数的情况，另外一个再一次使用的技巧就是不是一点一点计算，而是还是类似二分法的快速去计算，每次加倍。。

*/

//感觉这个方法最巧，最容易理解！！ 
double myPow(double x, int n) {
    if(n<0) return 1/x * myPow(1/x, -(n+1));
    if(n==0) return 1;
    if(n==2) return x*x;
    if(n%2==0) return myPow( myPow(x, n/2), 2); //加速了运算
    else return x*myPow( myPow(x, n/2), 2);
}

//double myPow 这个方法其实和上面差不多
double myPow(double x, int n) { 
    if(n==0) return 1;
    double t = myPow(x,n/2);
    if(n%2) return n<0 ? 1/x*t*t : x*t*t;
    else return t*t;
}

//double x
double myPow(double x, int n) { 
    if(n==0) return 1;
    if(n<0){
        n = -n;
        x = 1/x;
    }
    return n%2==0 ? myPow(x*x, n/2) : x*myPow(x*x, n/2);
}

//iterative one
double myPow(double x, int n) { 
    if(n==0) return 1;
    if(n<0) {
        n = -n;
        x = 1/x;
    }
    double ans = 1;
    while(n>0){
        if(n&1) ans *= x;
        x *= x;
        n >>= 1;
    }
    return ans;
}
bit operation
