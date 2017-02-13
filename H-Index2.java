/**思路：因为已经是排序的了，所以可以用二分法来找。经典的二分法应用。
 
     * Binary Search.
     * Think about the definition of h index: h papers that have >= h citations.
     * If randomly pick an index in the citations array, mid.
     * The # of papers have >= h citations is: array length - mid.
     * If citations[mid] = length - mid, return mid.
     * If citations[mid] > length - mid, paper not enough, mid should be smaller.
     * If citations[mid] < length - mid, too many papers, mid should be larger.
    */
    public int hIndex(int[] citations) {
        if (citations == null || citations.length == 0) {
            return 0;
        }
        int lo = 0;
        int hi = citations.length;
        int mid;
        while (lo < hi) {
            mid = lo + (hi - lo) / 2;
            if (citations[mid] >= citations.length - mid) { //如果当前对应的Ciation数目比Paper数目（也就是当前的h）大，说明Paper不够，那么还呆让paper数再大点,为了让h再大点，那就让mid要再小点，也就是继续要在左半部份找，所以就是挪动右指针
                hi = mid;
            } else { //否则，现在h也就是paper数比Citation大，这些Papper的Citation不够，所以要继续考察更大点的Citation，同时减小Paper数量，也就是要继续在右半边找 
                lo = mid + 1;
            }
        }
        return citations.length - lo; // Cannot be citations[lo] since search might fail and lo can out of bounds.
    }
