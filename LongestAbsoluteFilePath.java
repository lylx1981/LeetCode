/*
思路：比较有意思的一道题，很考细节，尤其是文件目录操作，值得好好看看。思路自己想到了，就是用Stack就可以了，Stack里面存的是到当前每个Level时长度是多少。每次判断一个特定的字符串（把原字符串按照/n全部断开为一组字符串依次判断即可），首先判断它所在的层数，同时Pop Stack中元素直到找到当前元素的父亲那一层后，取出那个Stack顶元素，其对应的就是当前考察元素路径的长度，再加上当前元素的长度，就是一个新的当前层的长度，继续入栈，同时如果当前考察元素包含"."则说明其是一个文件，则与最长文件名进行比较，如果有必要进行更新即可。几个注意的地方一个是Stack的相关操作比较巧，另外一个是转移字符其实是当作一个字符的长度，这个要记得。

lastIndexOf(int ch)
Returns the index within this string of the last occurrence of the specified character.


 Just pay attention that in String "\n", "\t", "\123" will all be count the length as one. As I mentioned, I'm assuming that "\t"will appear only before the directory or file name. For example, if s = "\t\t\tdirname", then s.lastIndexOf("\t") will be 2, the number of "\t" will be 3. 
 
 
。
转义字符：escape characters
\a 响铃(BEL) 007 　　\b 退格(BS) 008 　　\f 换页(FF) 012 　　\n 换行(LF) 010 　　\r 回车(CR) 013 　　\t 水平制表(HT) 009 　　\v 垂直制表(VT) 011 　　\\ 反斜杠 092 　　\? 问号字符 063 　　\' 单引号字符 039 　　\" 双引号字符 034 　　\0 空字符(NULL) 000 　　\ddd 任意字符 三位八进制 　　\xhh 任意字符 二位十六进制
*/


public int lengthLongestPath(String input) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(0); // "dummy" length 这个技巧有必要掌握。表示在第0层目录的时候，文件绝对路径长度为0.
        int maxLen = 0;
        for(String s:input.split("\n")){ //把原字符串按照/n全部断开为一组字符串依次判断
            int lev = s.lastIndexOf("\t")+1; // number of "\t"，"\n", "\t", "\123" will all be count the length as one，+1是因为Index是从0开始的。
            while(lev+1<stack.size()) stack.pop(); // find parent这一步最难理解，其实Stack里面当前存放的都是到某一层为止的路径总长度，所以对于当前考察串，我要找到我的父目录，但是现在Stack里面所达到的目录层数是stack.size()，这个很可能是比我当前层数还深（由于前面的操作一直深入到了很深的地方）。这个时候我就需要首先从Stack里面一直Pop元素，相当于从深的层向浅的层退，直到降到Stack里面有lev+1个元素，因为之前加了一个0在Stack，而且本身还有一个”dir“在最前面，占据了2个stack的位置，所以我要找到Leve-1层的长度其实是当Stack有Level-1+2=Level+1的时候。
            
            /*            To understand
            
            while(level + 1 < stack.size()) stack.pop(); 
            think an example that: we are somehow currently at "\t\tAbc.exe", whose level is 2. We only want to keep its ancestors in the stack, so that we can calculate the total length for this file path, so that is to say: we want our stack to store: dummy head, "dir" and "\tParent". (actually store len instead of file name string). So the stack size we want is 3, so do stack.pop() till stack.size() == level +1.*/


            int len = stack.peek()+s.length()-lev+1; // remove "/t", add"/"
            stack.push(len); //即使是一个包含文件名的字符串也会进stack，但是没关系，其后面会被Pop出来的，因为它后面不会再有目录了。
            // check if it is file
            if(s.contains(".")) maxLen = Math.max(maxLen, len-1); //len-1是因为上一步中文件后面的那个原本已经添加的 "/"要去掉
        }
        return maxLen;
    }
    
    //另外一个更简单看懂的版本，不再把文件放入Stack了。
    
    /*    Slightly longer version. But I think is way easier to understand.
    
    Only need -1 when it's at root dir.
    Only add to stack if it isn't an executable file.*/
public static int lengthLongestPath(String input) {
    Stack<Integer> stack = new Stack<>();
    stack.push(0);  // Layer 0, dummy head
    int maxLen = 0;
    for(String s : input.split("\n")) {
        int layer = s.lastIndexOf("\t") + 1;    // e.g. Layer 2 s: "\t\tsubsubdir1"
        while(layer < stack.size() - 1)
            stack.pop();
        int length = stack.peek() + s.length() - layer + 1; // remove "\t\t..." add "\"
        if(layer == 0)  // dir has no "\t" in the front
            length--;
        if(s.contains("."))
            maxLen = Math.max(maxLen, length);
        else
            stack.push(length);
    }
    return maxLen;
}


    
    
