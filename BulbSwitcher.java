/*
思路：其实是数学题，一个灯到底开还是关，主要是看被Switch了几个回合，那么这个取决于bulb i有几个除数，因为在除数的那些轮，它都会被Switch。如果i有偶数个除数（因为除数基本上都是成对出现的，比如6的除数是2和3一对，1和6一对），那么灯就是关的，否则就是开的。发现能具有奇数个除数的数就是平方数（比如4，除数有1，2，4）。所以<n的具有平方数的那些数所对应的灯都应该是开的状态，最后只要计算在n内有几个平方数存在就行了，那么直接就对n求其平方根，就是对应的几个平方数了。

*/
public class BulbSwitch {

    /**
     * Math.
     * A bulb ends up on if it's switched an odd number of times.
     * Bulb i is switched in round d iff d divides i.
     * So bulb i ends up on iff it has an odd number of divisors.
     * Divisors come in pairs, except when i is a square.
     * So just count the square numbers from 1 to n.
     * Possible square roots range from 1 to x, where x^2 <= n.
     * So x = int(sqrt(n)).
     */
    public int bulbSwitch(int n) {
        return (int) Math.sqrt(n);
    }

}
