
    /** 思路，简单方法没什么说的。主要不用任何Loop的话，另一个方法就是找出int允许范围内最大的powerof3的数，然后n如果是一个power of 3的数，肯定应该是能够整除这个数的 （这种方法只适用于powerofK, K是一个质数的情况）。
     */

    
    //最简单的方法
    public boolean isPowerOfThree(int n) {
        if(n>1)
            while(n%3==0) n /= 3;
        return n==1;
    }

    //simply hard code it since we know maxPowerOfThree = 1162261467:
    
    public boolean isPowerOfThree(int n) {
        return n > 0 && (1162261467 % n == 0);
    }
