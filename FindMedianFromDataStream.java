/* 思路：这道题的解法自己还是不能想到，其实很简单。就是用两个Heap存储大一半的元素，以及小一半的元素。大一半的元素用个最小堆，这样Top元素总是离Mediea最近的那个元素，同理，小一半的元素用最大堆。另外，这道题用了一个技巧，对于小一半的元素，都以负值存储并且还用最大堆，这样模拟量最小堆的效果 （其实不这样做也可以）。每次新加的元素，就先加入到最小堆里，也就是加到大一半的元素的Heap里，Heap调整之后，从Heap里Pop出一个元素，然后将其放入小一半元素对应的Heap里，其实就是相当于进行了序的调整（这里只是一种方法，也可以先放入小一半元素的堆里，再从小一半的元素Heap里Pop出一个元素再放入大一半元素的Heap，两种方法都可以，思想都是一样的）。同时，一直要保证大一半对应的堆的包含元素的个数“不小于”小一半元素对应堆的个数（基本上是等于，或者个数多1）。最后，取Mediea的时候，如果两个Heap Size一样，就各自取Top元素除以2即可。如果Size不等，就直接从大一半对应的Heap取Top元素即可（因为前面一直要求保证大一半对应的堆的包含元素的个数“不小于”小一半元素对应堆的个数，这样大一半元素对应的堆直接Pop出来的就是那个中间元素）。

I keep two heaps (or priority queues):

Max-heap small has the smaller half of the numbers.
Min-heap large has the larger half of the numbers.
This gives me direct access to the one or two middle values (they're the tops of the heaps), so getting the median takes O(1) time. And adding a number takes O(log n) time.

Supporting both min- and max-heap is more or less cumbersome, depending on the language, so I simply negate the numbers in the heap in which I want the reverse of the default order. To prevent this from causing a bug with -2^31 (which negated is itself, when using 32-bit ints), I use integer types larger than 32 bits.

Using larger integer types also prevents an overflow error when taking the mean of the two middle numbers. I think almost all solutions posted previously have that bug.

*/

class MedianFinder {

    private Queue<Long> small = new PriorityQueue(), //系统都默认的是最小堆（但是我们要将它模拟成最大堆），其实要定义一个最大堆，也可以用如下代码定义也行 new PriorityQueue(Collections.reverseOrder());
                        large = new PriorityQueue();

    public void addNum(int num) {
        large.add((long) num);
        small.add(-large.poll());//拿出large里面Top元素，加上符号，再加到small里，每个数字加上符号后才放入Small里，则负值最小的，其实就是对应正值最大的，正好模拟了最大堆。
        if (large.size() < small.size())
            large.add(-small.poll());//一直要保证large里面的元素比small多1，如果发现不满足的话，就从small拿出Top元素去掉符号后，再加入到large里。
    }

    public double findMedian() {
        return large.size() > small.size()
               ? large.peek()
               : (large.peek() - small.peek()) / 2.0;
    }
}
