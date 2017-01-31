/**
 * 思路
该题在LeetCode中假设开头和结尾没有空格，而且单词之间只有一个空格。但其实不需要这些假设也是可以的，就是代码会比较复杂。
思路就是两步走，第一步就是将整个字符串翻转。然后从头逐步扫描，将每个遇到单词再翻转过来。

[注意事项]
1）如果是Java，应该跟面试官指出String是immutable，所以需要用char array来做。
2）follow-up问题：k-step reverse。也就是在第二部翻转的时候，把k个单词看作一个长单词，进行翻转。


 */
public void reverseWords(char[] s){
		reverseWords(s,0,s.length-1);
		for(int i = 0, j = 0;i <= s.length;i++){
			if(i==s.length || s[i] == ' '){
				reverseWords(s,j,i-1);
				j = i+1;
			}
		}
	}

	private void reverseWords(char[] s, int begin, int end){
		while(begin < end){
			char c = s[begin];
			s[begin] = s[end];
			s[end] = c;
			begin++;
			end--;
		}
	}
