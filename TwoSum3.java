The big data test only have the condition that lots of add and few find. In fact, there has to be one operation's time complexity is O(n) and the other is O(1), no matter add or find. So clearly there's trade off when solve this problem, prefer quick find or quick add.

If consider more find and less add or we only care time complexity in finding.For example, add operation can be pre-done.

public class TwoSum {
        Set<Integer> sum;
        Set<Integer> num;
        
        TwoSum(){
            sum = new HashSet<Integer>();
            num = new HashSet<Integer>();
        }
        // Add the number to an internal data structure.
    	public void add(int number) {
    	    if(num.contains(number)){
    	        sum.add(number * 2);
    	    }else{
    	        Iterator<Integer> iter = num.iterator();
    	        while(iter.hasNext()){
    	            sum.add(iter.next() + number);
    	        }
    	        num.add(number);
    	    }
    	}
    
        // Find if there exists any pair of numbers which sum is equal to the value.
    	public boolean find(int value) {
    	    return sum.contains(value);
    	}
    }
On the other side

public class TwoSum {
    Map<Integer,Integer> hm;
    
    TwoSum(){
        hm = new HashMap<Integer,Integer>();
    }
    // Add the number to an internal data structure.
	public void add(int number) {
	    if(hm.containsKey(number)){
	        hm.put(number,2);
	    }else{
	        hm.put(number,1);
	    }
	}

    // Find if there exists any pair of numbers which sum is equal to the value.
	public boolean find(int value) {
	    Iterator<Integer> iter = hm.keySet().iterator();
	    while(iter.hasNext()){
	        int num1 = iter.next();
	        int num2 = value - num1;
	        if(hm.containsKey(num2)){
	            if(num1 != num2 || hm.get(num2) == 2){
	                return true;
	            }
	        }
	    }
	    return false;
	}
}
