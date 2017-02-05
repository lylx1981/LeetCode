
    /** 思路：Bit操作的另外一种应用，做加法。好好看看这道题。
     * 
     * 加法主要的是把两个数对应位相加，另外的是处理进位的问题。这里用到了两个主要的操作，& 和 ^, ^ 操作解决了假设不考虑进位的时候，对应位进行加法操作后每位应该呈现的值（比如1，0操作还是1，0和1操作还是1， 0和0还是0， 1和1是0--这里1和1会出现进行，没关系，后面的&操作会Take Care）。而&操作则是记录了进位的值（根据这个操作的性质，只有1，1的情况&操作结果才是1， 而1，1 正好对应了进位操作，也就是& 正好记录了进位操作，）。但是因为进位是进到高一位，那么将&操作完的值继续左移一位，那么该进位的地方就对准其该加上去的位置。只要进位不为0，那么就进入下一轮循环，让上一轮&操作完左移一位的那个值与上一轮^操作结果的值进行新一轮的^，直到 最后某次&操作后进位发生，也就是&操作后的结果是0，那么加法也就停止了，返回最终结果即可 。
     * 
     * 底下是英文的描述，参考即可
     * 
        The chosen answer from this post: adding-two-numbers-without-operator-clarification helps me understand how it works, and recursion proves to be more intuitive to me than iterative.
        Basically, with key points:
        
        exclusive or (^) handles these cases: 1+0 and 0+1
        AND (&) handles this case: 1+1, where carry occurs, in this case, we'll have to shift carry to the left, why? Think about this example: 001 + 101 = 110 (binary format), the least significant digits of the two operands are both '1', thus trigger a carry = 1, with this carry, their least significant digits: 1+1 = 0, thus we need to shift the carry to the left by 1 bit in order to get their correct sum: 2
        
        下面的这个Leetcode帖子，把所有和位操作相关的题都归纳了一遍，可以看看！！
        
        https://discuss.leetcode.com/topic/50315/a-summary-how-to-use-bit-manipulation-to-solve-problems-easily-and-efficiently/9
        
        WIKI
        
        Bit manipulation is the act of algorithmically manipulating bits or other pieces of data shorter than a word. Computer programming tasks that require bit manipulation include low-level device control, error detection and correction algorithms, data compression, encryption algorithms, and optimization. For most other tasks, modern programming languages allow the programmer to work directly with abstractions instead of bits that represent those abstractions. Source code that does bit manipulation makes use of the bitwise operations: AND, OR, XOR, NOT, and bit shifts.
        
        Bit manipulation, in some cases, can obviate or reduce the need to loop over a data structure and can give many-fold speed ups, as bit manipulations are processed in parallel, but the code can become more difficult to write and maintain.
        
        DETAILS
        
        Basics
        
        At the heart of bit manipulation are the bit-wise operators & (and), | (or), ~ (not) and ^ (exclusive-or, xor) and shift operators a << b and a >> b.
        
        There is no boolean operator counterpart to bitwise exclusive-or, but there is a simple explanation. The exclusive-or operation takes two inputs and returns a 1 if either one or the other of the inputs is a 1, but not if both are. That is, if both inputs are 1 or both inputs are 0, it returns 0. Bitwise exclusive-or, with the operator of a caret, ^, performs the exclusive-or operation on each pair of bits. Exclusive-or is commonly abbreviated XOR.
        Set union A | B
        Set intersection A & B
        Set subtraction A & ~B
        Set negation ALL_BITS ^ A or ~A
        Set bit A |= 1 << bit
        Clear bit A &= ~(1 << bit)
        Test bit (A & 1 << bit) != 0
        Extract last bit A&-A or A&~(A-1) or x^(x&(x-1))
        Remove last bit A&(A-1)
        Get all 1-bits ~0


     */


// Iterative
public int getSum(int a, int b) {
	if (a == 0) return b;
	if (b == 0) return a;

	while (b != 0) {
		int carry = a & b;  //calculate the carry
		a = a ^ b; //calculate sum of a and b without thinking the carry 
		b = carry << 1;
	}
	
	return a;
}


