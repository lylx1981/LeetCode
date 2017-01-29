/*思路：这个代码是网上找了一圈，感觉说的最仔细的，可以看看，但是最后代码可以用九章的代码。主要是熟悉一下Unix的文件规范

Unix路径简化的依据是：
当遇到“/../"则需要返回上级目录，需检查上级目录是否为空。所以后面再用/做split后，凡是遇到".. 就要返回上一层
当遇到"/./"则表示是本级目录，无需做任何特殊操作。 所以后面再用/做split后，凡是遇到 "." 就要返回上一层
当遇到"//"则表示是本级目录，无需做任何操作。 所以后面再用/做split后，凡是遇到"" 就要返回上一层
当遇到其他字符则表示是文件夹名，无需简化。
当字符串是空或者遇到”/../”，则需要返回一个"/"。
当遇见"/a//b"，则需要简化为"/a/b"。
因此，可以用两个stack来实现。

第一个栈用于保存所有合法的文件夹名。首先将path根据"/"进行划分，对于所有划分出来的字符串，
若等于""（例如"/."划分后就是""和"."）或者"."则什么都不做，
若等于".."则将栈顶元素出栈（相当于返回上级目录，栈为空则什么都不做），
否则入栈（合法文件夹名）。
遍历完所有划分出的字符串后，若栈为空，则返回"/"，否则要按照顺序返回栈中所有文件夹目录名。但是因为是栈，所以栈底元素应该在最前名，所以用第二个栈来将第一个栈中元素取相反顺序后输出。也可以不用第二个stack，直接用string，每次加到最前面，最后将最后一个字符（"/"）删去即可（下面九章的代码就是这么做的）。
代码如下：*/

public class Solution {
    /**
     * @param path the original path
     * @return the simplified path
     */
    public String simplifyPath(String path) {
        // Write your code here
        if(path == null || path.length() == 0){
            return null;
        }

        String[] array = path.split("/");

        Stack<String> stack = new Stack<String>();
        for(int i = 0; i < array.length; i++){
            if(array[i].equals(".") || array[i].equals("")){
                continue;
            }

            if(array[i].equals("..")){
                if(!stack.isEmpty()){
                    stack.pop();
                }
                continue;
            }

            stack.push(array[i]);
        }

        if(stack.isEmpty()){
            return "/";
        }

        Stack<String> temp = new Stack<String>();
        while(!stack.isEmpty()){
            temp.push(stack.pop());
        }

        String res = "";
        while(!temp.isEmpty()){
            res = res + "/" + temp.pop();
        }

        return res;
    }
}


/**
 * 九章代码是一个意思
 */

public class Solution {
    public String simplifyPath(String path) {
        String result = "/";
        String[] stubs = path.split("/+");
        //String[] stubs = path.split("/"); 代码写成这样也可以通过Leetcode，也就是不要/后面的加号也可以
        ArrayList<String> paths = new ArrayList<String>();
        for (String s : stubs){
            if(s.equals("..")){
                if(paths.size() > 0){
                    paths.remove(paths.size() - 1); //和Stack的作用一样
                }
            }
            else if (!s.equals(".") && !s.equals("")){
                paths.add(s);
            }
        }
        for (String s : paths){
            result += s + "/";
        }
        if (result.length() > 1)
            result = result.substring(0, result.length() - 1); //最后一个元素多了一个“/”,删去即可
        return result;
    }
}
