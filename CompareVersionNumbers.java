/**
 * 思路,从最高版本号开始比较就行了，主要注意两点：
 * split的参数需要传递的是正则表达式，
 *  public String[] split(String regex)
    regex - the delimiting regular expression
 * 
 * 另外，需要将两个版本号都补齐长度，短的后面补0，因为2和2.0就需要补0操作才可以比较出来他们是相等的。
 * 
 * 但是这个算法有人评论可能有错误，主要是归结于到底版本号是按什么数字生成规矩制定的：

    Yes i think solution is wrong as its converting characters after "." as interger and comparing
    them . Will fail for cases like 1.02 and 1.1 as it will treat numbers after "." as 2 and 1 , thus giving 1.02 > 1.1 which is wrong.
    
    注意了解一下这个情况。


 */
public int compareVersion(String version1, String version2) {
    String[] levels1 = version1.split("\\.");
    String[] levels2 = version2.split("\\.");
    
    int length = Math.max(levels1.length, levels2.length);
    for (int i=0; i<length; i++) {
    	Integer v1 = i < levels1.length ? Integer.parseInt(levels1[i]) : 0;
    	Integer v2 = i < levels2.length ? Integer.parseInt(levels2[i]) : 0;
    	int compare = v1.compareTo(v2);
    	if (compare != 0) {
    		return compare;
    	}
    }
    
    return 0;
}
