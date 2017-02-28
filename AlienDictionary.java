/*
思路： 对字典里每相邻的两个词进行单独比较，比较方法是对他们相同位的字符进行比较，如果大家前缀都一样，那么一直到第一次出现不一样的那个字符的位置时开始进行比较，比较后，退出当前两个词的比较即可（因为最多也就能比较到这里了）。另外，用两个Hashmap存储比较结果，其中Map里存储针对一个节点，其都比谁大（也就是Value存的是一个Node Set）。另一个Degree存储的是针对每个节点，有多少个节点比它大。 在比较完毕后，对Degree里的节点进行BFS，从degree为0的开始（说明没有节点比他们大），进入Queue。然后每从Queue里面 pop出一个元素，就将其加入Res里，并且对这个节点进行如下判断： 如果这些节点在Map里有对应的键值对，那么其键值对的Value就是一个比当前节点小的节点。那么对应这些节点，依次更新他们在Degree里的对应键值对，也就是对应的Value值也要相应减1（也为当前考察节点已经考察完毕，把它的影响要删除掉）.然后，一旦出现新的节点Degree变为0了，就再次加入Queue中，继续下去。最后判断只要Res Size和 Degree size一样 （出现的字符都完整覆盖，同时是一个合法的序），就说明这是一个合法的解了。

自己想的也差不多，但是没有想到就让相邻的两个单词来进行比较即可（比较巧）。自己想的是用类似递归的方法，比如首先对所有单词的第一个字符进行比较，把所有第一位一样的Words归类在一起类似于分组，然后对这些Words,再从第二位进行比较，依次下去。

*/
public String alienOrder(String[] words) {
    Map<Character, Set<Character>> map=new HashMap<Character, Set<Character>>();
    Map<Character, Integer> degree=new HashMap<Character, Integer>();
    String result="";
    if(words==null || words.length==0) return result;
    for(String s: words){
        for(char c: s.toCharArray()){
            degree.put(c,0); //每个出现的字符都在degree里有一个对应的键值对
        }
    }
    for(int i=0; i<words.length-1; i++){
        String cur=words[i];
        String next=words[i+1];
        int length=Math.min(cur.length(), next.length());
        for(int j=0; j<length; j++){
            char c1=cur.charAt(j);
            char c2=next.charAt(j);
            if(c1!=c2){
                Set<Character> set=new HashSet<Character>();//其实这里代码写的不好，其实意思就是如果map.containsKey(c1)是-1的话，就为c1生成一个set.
                if(map.containsKey(c1)) set=map.get(c1); //如果map.containsKey(c1)存在的话，实际上就替代掉了上面的新生成的Set
                if(!set.contains(c2)){
                    set.add(c2);
                    map.put(c1, set); //更新C1的Set
                    degree.put(c2, degree.get(c2)+1); //更新C2的Degree
                }
                break;
            }
        }
    }
    Queue<Character> q=new LinkedList<Character>();
    for(char c: degree.keySet()){
        if(degree.get(c)==0) q.add(c);//把Degreee为0的都加入Queue
    }
    while(!q.isEmpty()){
        char c=q.remove();
        result+=c;
        if(map.containsKey(c)){
            for(char c2: map.get(c)){
                degree.put(c2,degree.get(c2)-1); //更新C2的Degree
                if(degree.get(c2)==0) q.add(c2); //当更新完后，C2的Degree变为0的话，C2进Queue，很经典的BFS
            }
        }
    }
    if(result.length()!=degree.size()) return "";
    return result;
}
