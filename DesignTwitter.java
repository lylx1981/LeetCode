/*思路： 思路： 要定义几个类，比如用户类，Twitee类，等等。基本和自己想的一样，主要的技术点在于针对一个用户ID，拿到其Top10，注意题目的定义，这10个是要么用户自己发的，要么是它Follow的那个用户发的，仅此而已，不存在级联的关系，也就是说不存在a关注b,b关注c，如果c发了一条，那么对a取Top10的时候，c发的那一条不算在top10里。另外就是用List来存每个用户的Twitter，让每个twitee用next连起来,每个新Twitee的加到当前的头。再一个就是把用户自己也加入自己的follow里面，这样处理起来更简单。另外Top10，对于所有要考察的twitter list,用Heap来处理它，刚开始只把所有List的头加入Heap（需要是最大堆，应为发布时间越晚，时间值越大），从Heap拿出来一个，然后再把该Twitee的Next对应的Twitee再加入heap里（heap内部会自动继续调整排列），直到取出Top10. 自己对于Top10功能的实现也想到了对所有List的Interator进行考察，看看哪个时间最靠后，但是这里直接使用了JAVA的Heap最大堆，更简单明了。


*/
public class Twitter {
    private static int timeStamp=0;
    
    // easy to find if user exist
    private Map<Integer, User> userMap;
    
    // Tweet link to next Tweet so that we can save a lot of time
    // when we execute getNewsFeed(userId)
    private class Tweet{
        public int id;
        public int time;
        public Tweet next;
        
        public Tweet(int id){
            this.id = id;
            time = timeStamp++;
            next=null;
        }
    }
    
    
    // OO design so User can follow, unfollow and post itself
    public class User{
        public int id;
        public Set<Integer> followed;
        public Tweet tweet_head;
        
        public User(int id){
            this.id=id;
            followed = new HashSet<>();
            follow(id); // first follow itself
            tweet_head = null;
        }
        
        public void follow(int id){
            followed.add(id);
        }
        
        public void unfollow(int id){
            followed.remove(id);
        }
        
        
        // everytime user post a new tweet, add it to the head of tweet list.
        public void post(int id){
            Tweet t = new Tweet(id);
            t.next=tweet_head;
            tweet_head=t;
        }
    }
    
    
    

    /** Initialize your data structure here. */
    public Twitter() {
        userMap = new HashMap<Integer, User>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if(!userMap.containsKey(userId)){
            User u = new User(userId);
            userMap.put(userId, u);
        }
        userMap.get(userId).post(tweetId);
            
    }
    

    
    // Best part of this.
    // first get all tweets lists from one user including itself and all people it followed.
    // Second add all heads into a max heap. Every time we poll a tweet with 
    // largest time stamp from the heap, then we add its next tweet into the heap.
    // So after adding all heads we only need to add 9 tweets at most into this 
    // heap before we get the 10 most recent tweet.
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res = new LinkedList<>();

        if(!userMap.containsKey(userId))   return res;
        
        Set<Integer> users = userMap.get(userId).followed;
        PriorityQueue<Tweet> q = new PriorityQueue<Tweet>(users.size(), (a,b)->(b.time-a.time));
        for(int user: users){
            Tweet t = userMap.get(user).tweet_head;
            // very imporant! If we add null to the head we are screwed.
            if(t!=null){
                q.add(t);
            }
        }
        int n=0;
        while(!q.isEmpty() && n<10){
          Tweet t = q.poll();
          res.add(t.id);
          n++;
          if(t.next!=null)
            q.add(t.next);
        }
        
        return res;
        
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId)){
            User u = new User(followerId);
            userMap.put(followerId, u);
        }
        if(!userMap.containsKey(followeeId)){
            User u = new User(followeeId);
            userMap.put(followeeId, u);
        }
        userMap.get(followerId).follow(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId) || followerId==followeeId)
            return;
        userMap.get(followerId).unfollow(followeeId);
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
