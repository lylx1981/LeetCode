/*思路： 主要用Stack，但是由于是？：这样的运算符，如果前面是F的话，那就值应该去：后面的值，而如果从前往后依次进Stack的话，可能：后面的部分都还没有在Stack里。所以这道题的方法是从后往前进Stack。而且 最主要的Trick是Stack里放的一直都是S2的Candidate（且越在Stack底的越大）,当遇见一个新的元素，需要和当前Stack顶元素比，如果没有Stack顶元素大，直接入栈，否则需要把当前Stack里所以比这个元素小的元素都Pop出来的，并且Pop出来的最大值付给S3，然后再把当前元素继续入Stack。这样做的目的是通过Stack里面一直保证有最大的S2，而同时让Stack Pop出来的最大的元素付给S3,这样使得S3也尽可能大但是同时又小于Stack里面的所有S2 candidate，这样就有最大的机会使得小一个要考察的元素满足<S3,由于下一个元素就是要找的S1 Candidate，那么就有最大可能要找到132 Pattern。可以看看下面的例子体会体会思想。同时，如果132Pattern存在的话，这个方法一定能找到，因为这个办法基本上会覆盖所有会出现132 Pattern的情况。


keep the value of s3 as big as possible
use a "sorted" stack to maintain the candidates of s2 and s3.
The numbers inside the stack are s2 and the number that popped out is the maximum s3. So the last thing to do is to maintain the order of the stack.



We want to search for a subsequence (s1,s2,s3)

INTUITION: The problem would be simpler if we want to find sequence with s1 > s2 > s3, we just need to find s1, followed by s2 and s3. Now if we want to find a 132 sequence, we need to switch up the order of searching. we want to first find s2, followed by s3, then s1.

IDEA: We can start from either side but I think starting from the end allow us to finish in a single pass. The idea is to start from end and search for a candidate for s2 and s3. A number becomes a candidate for s3 if there is any number on the left of s2 that is bigger than it （这句话是最核心的一句，但是说的不清楚，这句话要说的是如果一个数在S2的Candidate的左边，且这个数比S2小，这个数就是一个S3的Candidate）.

DETECTION: Keep track of the largest candidate of s3 and once we encounter any number smaller than s3, we know we found a valid sequence since s1 < s3 implies s1 < s2.

IMPLEMENTATION:

Have a stack, each time we store a new number, we first pop out all numbers that are smaller than that number. The numbers that are popped out becomes candidate for s3.
We keep track of the maximum of such s3 (which is always the most recently popped number from the stack).
Once we encounter any number smaller than s3, we know we found a valid sequence since s1 < s3 implies s1 < s2.
RUNTIME: Each item is pushed and popped once at most, the time complexity is therefore O(n).

EXAMPLE: 这里例子值得看看
i = 6, nums = [ 9, 11, 8, 9, 10, 7, 9 ], S1 candidate = 9, S3 candidate = None, Stack = Empty
i = 5, nums = [ 9, 11, 8, 9, 10, 7, 9 ], S1 candidate = 7, S3 candidate = None, Stack = [9] //Stack里面存放的都是S2的Candidate
i = 4, nums = [ 9, 11, 8, 9, 10, 7, 9 ], S1 candidate = 10, S3 candidate = None, Stack = [9,7]
i = 3, nums = [ 9, 11, 8, 9, 10, 7, 9 ], S1 candidate = 9, S3 candidate = 9, Stack = [10] //9，7都没有当前10大，所以9，7都要出战，同时9变成了S3的Candidate，10现在是S2的Candidate
i = 2, nums = [ 9, 11, 8, 9, 10, 7, 9 ], S1 candidate = 8, S3 candidate = 9, Stack = [10,9] We have 8<9, sequence found!

EDIT: Thanks @Pumpkin78 and @dalwise for pointing out that the maximum candidate for s3 is always the recently popped number from the stack, because if we encounter any entry smaller than the current candidate, the function would already have returned.
*/
//C++版本，用上面的思想实现的。


 bool find132pattern(vector<int>& nums) {
        int s3 = INT_MIN;
        stack<int> st;
        for( int i = nums.size()-1; i >= 0; i -- ){
            if( nums[i] < s3 ) return true;
            else while( !st.empty() && nums[i] > st.top() ){ 
              s3 = st.top(); st.pop(); 
            }
            st.push(nums[i]);
        }
        return false;
    }

//同样的思想，但是是JAVA的，而且同时没有用stack，而是直接用原来的数组以末尾当作Stack底仿真了一个Stack，值得看看！！

 public boolean find132pattern(int[] nums) {
        int two = Integer.MIN_VALUE;
        int index = nums.length;
        for (int i=nums.length-1; i>=0; i--) {
            if (nums[i] < two) return true;
            while (index < nums.length && nums[i] > nums[index]) {
                two = nums[index++];
            }
            nums[--index] = nums[i];
        }
        return false;
    }

