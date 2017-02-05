/**
 * 思路：因为只能包含2，3，5，我就一直用2，3，5去除这个数，如果最后到1，则说明就是一个Ugly数，否则就不是。理解题意的细节性是关键。
 * 
 * 注意题意，/Ugly的定义”only include 2, 3, 5“， 是只包含2，3，5，也就是说包含，2，3，5同时包含别的数的时候是不能叫做Ugly数的。8是Ugly数，是因为8可以写成2×2×2.
 * 
 * 看看下面关于质数分解的基本常识：
 * 
 * 另外，prime factors是素（质）数因子的意思，或质因子的意思。
 * 
 * （1）只能被“1”和它本身整除的数叫素数，如：2、3、5、7、11……。
（3）“1”既不是素数也不是合数。
 因子，如果整数a能被整数b整除，那就有一个整数q，使得 a=bq,则b和q都称为a的一个因子.素数做因子的叫做素数因子
 
 In number theory, the prime factors of a positive integer are the prime numbers that divide that integer exactly.[1] The prime factorization of a positive integer is a list of the integer's prime factors, together with their multiplicities ; the process of determining these factors is called integer factorization （也叫质因数分解）. 
 
 讲到一个数的分解质因子的时候，一般涉及到的数都不是质数，因为质数只有1和自己是自己的因子。
 
 http://www.mathsisfun.com/prime-factorization.html
 
 * 
 * 
   */



 public boolean isUgly(int num) {
        if (num <= 0) return false;

        while (num % 2 == 0) num /= 2;
        while (num % 3 == 0) num /= 3;
        while (num % 5 == 0) num /= 5;

        return num == 1;
    }
