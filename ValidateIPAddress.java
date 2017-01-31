
/*
思路： 这道题难度不大，主要就是看是不是能把所有情况都考虑全，所以还是一道非常重要的题，要好好巩固。
另外，关于IP地址的介绍，要彻底记住,尤其是哪些是合法地址，哪些是不合法地址。

关于16进制的一些知识： 十六进制（简写为hex或下標16）在数学中是一种逢16进1的进位制。 一般用数字0到9和字母A到F（或a~f）表示，其中:A~F表示10~15，这些称作十六进制数字。 例如十进制數57，在二进制寫作111001，在16进制寫作39。
*/


public String validIPAddress(String IP) {
	if(isValidIPv4(IP)) return "IPv4";
	else if(isValidIPv6(IP)) return "IPv6";
	else return "Neither";
}

public boolean isValidIPv4(String ip) {
	if(ip.length()<7) return false;
	if(ip.charAt(0)=='.') return false;
	if(ip.charAt(ip.length()-1)=='.') return false;
	String[] tokens = ip.split("\\.");
	if(tokens.length!=4) return false;
	for(String token:tokens) {
		if(!isValidIPv4Token(token)) return false;
	}
	return true;
}
public boolean isValidIPv4Token(String token) {
	if(token.startsWith("0") && token.length()>1) return false;
	try {
		int parsedInt = Integer.parseInt(token);
		if(parsedInt<0 || parsedInt>255) return false;
		if(parsedInt==0 && token.charAt(0)!='0') return false; //处理这种特殊情况"0.0.0.-0"
	} catch(NumberFormatException nfe) {
		return false;
	}
	return true;
}
	
public boolean isValidIPv6(String ip) {
	if(ip.length()<15) return false;
	if(ip.charAt(0)==':') return false;
	if(ip.charAt(ip.length()-1)==':') return false;
	String[] tokens = ip.split(":");
	if(tokens.length!=8) return false;
	for(String token: tokens) {
		if(!isValidIPv6Token(token)) return false;
	}
	return true;
}
public boolean isValidIPv6Token(String token) {
	if(token.length()>4 || token.length()==0) return false;
	char[] chars = token.toCharArray();
	for(char c:chars) {
		boolean isDigit = c>=48 && c<=57; //这几个数字要记住
		boolean isUppercaseAF = c>=65 && c<=70;  //这几个数字要记住
		boolean isLowerCaseAF = c>=97 && c<=102;  //这几个数字要记住
		if(!(isDigit || isUppercaseAF || isLowerCaseAF)) 
			return false;
	}
	return true;
}


