/*思路：这道题刚开始觉得挺像Skyline的那道题，那道题也是随着坐标向右移动，楼层高度会有变化，但是要时刻更新为当前的最大楼层，但是后面思考发现两者还是不同的。因为Skyline那道题，每个楼的宽度是不一样的，所以那道题的解法应用了Heap，或者是TreeMap的解法。而这样题是标准的滑动窗口题目，窗口大小一直是K，但是随着窗口的移动，最大值会不停的变化。

这道题非常具有代表性，这道题对这一类型的求解滑动窗口动态最大最小值是一个很好的代表题目，需要好好理解记忆。

具体思路是用dequeue,dequeue具体比Queue更多的操作，主要体现为可以从两头添加删除元素。所以这道题的基本思路是从头到尾遍历数组，每到一个新的位置，首先从dequeue的头部删除掉已经过期的不应该在窗口中的元素。同时从尾部在加入当前考察元素之前，先逐个从尾部删除所有比当前元素小的元素，因为一旦当前元素进入Dequeue了，他们无论如何都不再有可能成为Max了。当然，如果当前Dequeue里面末尾元素是大于当前元素,那么就不会做任何从尾部进行删除的操作。最后，将当前元素塞入Dequeue尾部即可。所以这样的话，Dqueue的头元素实际上一直存储的是当前Window里面的最大元素（而头元素之后的元素是当前窗口内的其他元素，其都比头元素小，且应该是递减的，且顺序是他们在原数组中的出现相对顺序，所以看到别人也把这样的Dequeue叫做monotonic queue），所以每次每挪动一个位置（并且当窗口已经展开到K个大小时），就每次直接把当前Dequeue里面的头元素加入到结果中即可。



We scan the array from 0 to n-1, keep "promising" elements in the deque. The algorithm is amortized O(n) as each element is put and polled once.

At each i, we keep "promising" elements, which are potentially max number in window [i-(k-1),i] or any subsequent window. This means

If an element in the deque and it is out of i-(k-1), we discard them. We just need to poll from the head, as we are using a deque and elements are ordered as the sequence in the array

Now only those elements within [i-(k-1),i] are in the deque. We then discard elements smaller than a[i] from the tail. This is because if a[x] <a[i] and x<i, then a[x] has no chance to be the "max" in [i-(k-1),i], or any other subsequent window: a[i] would always be a better candidate.

As a result elements in the deque are ordered in both sequence in array and their value. At each step the head of the deque is the max element in [i-(k-1),i]

下面是关于Dequeue的相关知识：

java.util.Deque接口是java.util.Queue接口的子接口。

它代表的队列包含从队列两端添加和删除元素。

"Deque" 是 "Double Ended Queue"的简称。

添加,访问和删除元素

向Deque中添加元素除了add()和offer()方法以外，

还可以调用addLast()插入到末尾，addFirst()插入到头部。

offerFirst()插入到头部,offerLast()插入到末尾。

push()插入到头部,

出队列的方法除了queue中提到的

peek();  element();  poll(); remove();

Deque还新增了

getFirst()方法,返回头部元素, 不删除该元素

getLast()方法,返回末尾元素,不删除该元素

peekFirst()方法,返回头部元素,不删除该元素

peekLast()方法,返回末尾元素,不删除该元素

pollFirst()方法,返回头部元素并且删除该元素

pollLast()方法,返回尾部元素并且删除该元素

removeFirst()方法,返回头部元素并且删除该元素

removeLast()方法,返回尾部元素并且删除该元素

removeFirstOccurrence(Object)方法,由头至尾删除第一次出现在列表中的元素

removeLastOccurrence(Object)方法,由尾至头删除第一次出现在列表中的元素

pop()从末尾取出并且删除元素




*/

public int[] maxSlidingWindow(int[] a, int k) {		
		if (a == null || k <= 0) {
			return new int[0];
		}
		int n = a.length;
		int[] r = new int[n-k+1]; //用于存储结果
		int ri = 0; //用于记录当前用于存储结果的R用到什么位置了
		// store index
		Deque<Integer> q = new ArrayDeque<>();
		for (int i = 0; i < a.length; i++) {
			// remove numbers out of range k 删除掉过期的元素
			while (!q.isEmpty() && q.peek() < i - k + 1) {
				q.poll();
			}
			// remove smaller numbers in k range as they are useless 删除掉无用的元素
			while (!q.isEmpty() && a[q.peekLast()] < a[i]) {
				q.pollLast();
			}
			// q contains index... r contains content
			q.offer(i); //执行完上面2步后，再加入当前考察元素
			if (i >= k - 1) { //当窗口已经展开到K个大小时
				r[ri++] = a[q.peek()];
			}
		}
		return r;
	}
