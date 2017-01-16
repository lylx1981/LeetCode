class FirstMissingPositive {
    /*
      原理就是地一遍遍历数组，对于每个遇到的正数A，把它放在index为A-1的位置上（通过交换swap 操作），作为它本应该呆的位置
      然后第二遍循环看一下第i的位置上是不是值为i+1,不是的话，当前的值就是missing的值,返回即可。
    */
    public static void main(String[] args) {
        int[] A = {1,2,0};
        System.out.println(new FirstMissingPositive().firstMissingPositive(A));
    }
    
    /**
     * Position of integer n should be n - 1 if sorted
     * Correct form [1, 2, 3, 4, ..., #, n]
     * If not in position swap it with A[A[p]-1]
     */
    public static int firstMissingPositive(int[] A) {
        if (A == null || A.length == 0) return 1;
        int n = A.length;
        for (int i = 0; i < n; i++) {
            int num = A[i];
            while (A[i] <= n && A[i] > 0 && A[num - 1] != num) {
                A[i] = A[num - 1];
                A[num - 1] = num;
                num = A[i];
            }
        }
        for (int i = 0; i < n; i++) 
            if (A[i] != i + 1) return i + 1;
        return n + 1; // nothing in middle losing, return largest
    }
    
}
