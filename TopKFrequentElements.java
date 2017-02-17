/*
思路：用Hashmap以及Bucket或者优先级队列都可以。Hashmap用来统计一个字符出现的字数，然后对于Hashmap里面的每个键值对（K是字符，V是出现次数），按照出现次数进行桶排序，并加入相应的Key到桶里，最后从最大桶开始拿出k个元素，即可。

优先级队列是一样的思路，在Hashmap的Entity也就是键值对加入到最大堆里，那么接下里来从最大堆Pop出K个元素就行了。

*/
public List<Integer> topKFrequent(int[] nums, int k) {

	List<Integer>[] bucket = new List[nums.length + 1];
	Map<Integer, Integer> frequencyMap = new HashMap<Integer, Integer>();

	for (int n : nums) {
		frequencyMap.put(n, frequencyMap.getOrDefault(n, 0) + 1); //统计次数，加入Hashmap
	}
    
    //进行桶排序
	for (int key : frequencyMap.keySet()) {
		int frequency = frequencyMap.get(key); //按照Value，也就是Frenquncy进行桶排序
		if (bucket[frequency] == null) {
			bucket[frequency] = new ArrayList<>();
		}
		bucket[frequency].add(key); //而加入桶的却是加入Key
	}

	List<Integer> res = new ArrayList<>();
    //从最大桶开始，拿出K个
	for (int pos = bucket.length - 1; pos >= 0 && res.size() < k; pos--) {
		if (bucket[pos] != null) {
			res.addAll(bucket[pos]);
		}
	}
	return res;
}

// use maxHeap. Put entry into maxHeap so we can always poll a number with largest frequency
public class Solution {
    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int n: nums){
            map.put(n, map.getOrDefault(n,0)+1);
        }
         //注意这里的写法，已经规定了最大堆里的元素类型是Map.Entity,这种写法和用法自己要学会！！！  
        PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = 
                         new PriorityQueue<>((a,b)->(b.getValue()-a.getValue())); //这里定义了比较运算符是比较Entity.getValue()的值，好好学习如何写这样的语法。
        for(Map.Entry<Integer,Integer> entry: map.entrySet()){
            maxHeap.add(entry);
        }
        
        List<Integer> res = new ArrayList<>();
        while(res.size()<k){
            Map.Entry<Integer, Integer> entry = maxHeap.poll();
            res.add(entry.getKey());
        }
        return res;
    }
}
