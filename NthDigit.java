
    /** 思路：这道题还是蛮有创意的一道题，是说自然数序列看成一个长字符串，问我们第N位上的数字是什么。那么这道题的关键就是要找出第N位所在的数字，然后可以把数字转为字符串，这样直接可以访问任何一位。那么我们首先来分析自然数序列和其位数的关系，前九个数都是1位的，然后10到99总共90个数字都是两位的，100到999这900个数都是三位的，那么这就很有规律了，我们可以定义个变量cnt，初始化为9，然后每次循环扩大10倍，再用一个变量len记录当前循环区间数字的位数，另外再需要一个变量start用来记录当前循环区间的第一个数字，我们n每次循环都减去len*cnt (区间总位数)，当n落到某一个确定的区间里了，那么(n-1)/len就是目标数字在该区间里的坐标，加上start就是得到了目标数字，然后我们将目标数字start转为字符串，(n-1)%len就是所要求的目标位，最后别忘了考虑int溢出问题，我们干脆把所有变量都申请为长整型的好了

    /*Straight forward way to solve the problem in 3 steps:
    
    find the length of the number where the nth digit is from
    find the actual number where the nth digit is from
    find the nth digit and return
    
    发现如下规律：
    
        The first idea is: the result will only be within 0~9, can we find a cycle?
    
    For input 1 to 20, the result is:
    
    1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 0, 1, 1, 1, 2, 1, 3, 1, 4, 1, 5
    
    No cycle found. While we can find that digits matter! The result sequence should be like:
    
   
    1~9: 1*9=9 in total 1-9只有9个数，每个数是1位
    
    10~99: 2*90=180 in total 10-99有90个数，每个数是2位
    
    100~999: 3*900=2700 in total 100-999有900个数，每个数是3位
    
    所以 位数没多一位，涉及到的数字个数也比原来多10倍。
    
    
    */
    
    
    
    
	public int findNthDigit(int n) {
		int len = 1;
		long count = 9;
		int start = 1;

		while (n > len * count) {
			n -= len * count;
			len += 1;
			count *= 10;
			start *= 10;
		}
        //把n一直减到落入其区间的数字范围
		start += (n - 1) / len; //The reason why (n-1) is to keep the correct digits finally in number they correspond to. Eg: if we are trying to find the 192th digit, we know range from 1th digit to 9th digit belongs to numbers from 1 to 9 and range from 10th to 189th （也就是9+180） belongs to numbers from 10 to 99, right? So it is obvious that the next number should be 100 and the 192th digit should be the 3rd digit of 100(now n=3). OK, back to the code, if we donot minus 1 from n and then devide the len, the 192th digit would go to the next number which is 101.
		
		 //n in that situation is the relative number of the digit beginning from start and so on （Start是当前开始考察的第一个，所以要考虑这种相对关系）. In that case len is how much digits the numbers where the digit we are searching for have. So, we want to know the exact number where the digits is. If n is multiple of len then you will get a number one bigger than the original number. I guess that is the answer, or at least I understand it like this.
		 
		 
		String s = Integer.toString(start);
		return Character.getNumericValue(s.charAt((n - 1) % len));
	}




