/**
 * 思路，要成为一个合法的IP地址，需要满足以下几个条件：
 * 1. 一个字符串最大为12位，最小为4位
 * 2.一个IP地址是从0-255之间变化
 * 3.一个IP地址一共有4部分组成，中间用“.”号连接
 * 4. 对于一个IP地址中的某一段，其要么是0，要么不能必须是1-255之间的数字，且这些数字前不能有0
 
 特殊的网址
每一个字节都为0的地址（“0.0.0.0”）对应于当前主机；
IP地址中的每一个字节都为1的IP地址（“255．255．255．255”）是当前子网的广播地址；
 */

public class Solution {
    public ArrayList<String> restoreIpAddresses(String s) {
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> list = new ArrayList<String>();
        
        if(s.length() <4 || s.length() > 12) //验证是否满足* 1. 一个字符串最大为12位，最小为4位
            return result; 
        
        helper(result, list, s , 0); //list里面装的是中间结果，当list里面有4部分的时候，这时候就要判断是不是其可以成为一个合法的IP地址了
        return result;
    }
    
    public void helper(ArrayList<String> result, ArrayList<String> list, String s, int start){
        if(list.size() == 4){ //
            if(start != s.length())
                return; //如果List已经包含4部分，但是现在还没有走到字符串尽头，则说明这个切分方案不对，就此放弃
            
            //如果通过了上面的步骤，说明现在List里面的4部分可以组成一个合法的IP地址，则进行组装
            StringBuffer sb = new StringBuffer(); 
            for(String tmp: list){
                sb.append(tmp);
                sb.append(".");
            }
            sb.deleteCharAt(sb.length()-1); //最后多加了一个“.”，去除即可
            result.add(sb.toString()); //加入最终结果集中
            return;
        }
        
        //当list.size()还不是4的时候，从当前Start位置开始，继续往List加下一部分
        for(int i=start; i<s.length() && i < start+3; i++){
            //一共可以有3种方案，[start自己]，[start, start+1]，[start,start+2] 每一种都是新的一种方案
            String tmp = s.substring(start, i+1); //temp现在存放的就是一种当前正在验证的方案
            //判断如果tmp是合法有效的数字，则将其加入List中，然后从加入这个字符串后的下一位开始作为新的Start，然后继续进行递归
            if(isvalid(tmp)){
                list.add(tmp);
                helper(result, list, s, i+1);
                list.remove(list.size()-1); //回溯操作，将其从List中去除
            }
        }
    }
    
    private boolean isvalid(String s){
        //如果一个字符串首位为0，它是一个合法有效的IP数字当且仅当它就是一个单0字符串，所以以0开头的长于1的字符串都将被视为无效的。
        if(s.charAt(0) == '0')
            return s.equals("0"); // to eliminate cases like "00", "01"
        int digit = Integer.valueOf(s);
        return digit >= 0 && digit <= 255; //另外，对于一个数字，其如果要作为一个IP地址中的一段数字的话，这个数字必须是0-255之间
    }
}
