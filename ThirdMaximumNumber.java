//三个指针分别记录三个最大值。从前到后遍历每个元素，每次将当前元素与当前记录的三个最大值作比较，有以下几种情况：
//1. 比最大值大，那么替换掉最大值，同时最大值变为第二大，第二大变为第三大// 2.只比第二大，则替换第二大，原先第二大变为第三大。
// 3.只比第三大，则仅替换第三大。


public int ThirdMaximumNumber (int[] nums) {
        Integer max1 = null;
        Integer max2 = null;
        Integer max3 = null;
        for (Integer n : nums) {
            if (n.equals(max1) || n.equals(max2) || n.equals(max3)) continue;
            if (max1 == null || n > max1) {
                max3 = max2;
                max2 = max1;
                max1 = n;
            } else if (max2 == null || n > max2) {
                max3 = max2;
                max2 = n;
            } else if (max3 == null || n > max3) {
                max3 = n;
            }
        }
        return max3 == null ? max1 : max3;
    }
