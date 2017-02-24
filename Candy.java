 
 /*
思路1：自己想的方法比较傻，思路是从最小的Rate的孩子开始按照Rate依次考察，如果当前考察的孩子的Rate比邻居大，那么就要更新其Candy的值为邻居的当前Candy数+1，这个邻居说指Rate比当前考察节点小的那个邻居. 这个方法会造成需要对Rate排序，后来发现Leetcode上直接有不需要排序的方法，只有O(n)复杂度，非常简单，从左往右先扫一遍先处理那些ratings[i]>ratings[i-1]的节点，并按照规则更新其Candy数。然后再从右往左扫一遍，对于ratings[i-1]>ratings[i]，也更新其Candy数（取当前i-1的现有值和num[i]+1之间的最大值）。
*/

//C++版本，比较容易看懂和理解
int candy(vector<int> &ratings)
 {
	 int size=ratings.size();
	 if(size<=1)
		 return size;
	 vector<int> num(size,1);
	 for (int i = 1; i < size; i++)
	 {
		 if(ratings[i]>ratings[i-1])
			 num[i]=num[i-1]+1;
	 }
	 for (int i= size-1; i>0 ; i--)
	 {
		 if(ratings[i-1]>ratings[i])
			 num[i-1]=max(num[i]+1,num[i-1]);
	 }
	 int result=0;
	 for (int i = 0; i < size; i++)
	 {
		 result+=num[i];
		// cout<<num[i]<<" ";
	 }
	 return result;
 }

//同样的思想，JAVA版本，代码有一些稍微不同，但是大体思想一样

public int candy(int[] ratings) {

    int len = ratings.length;
    int[] candy = new int[len];
    
    candy[0] =1;
    for (int i = 1; i < len; ++i) {
        if (ratings[i] > ratings[i-1]) {
            candy[i] = candy[i-1] + 1;
        } else {
            candy[i] = 1;
        }
    }

    int total = candy[len-1];
    for (int i = len - 2; i >= 0; --i) {
        if (ratings[i] > ratings[i+1] && candy[i] <= candy[i+1]) {
            candy[i] = candy[i+1] + 1;
        }
        total += candy[i];    
    }
    return total;
}
 
 
