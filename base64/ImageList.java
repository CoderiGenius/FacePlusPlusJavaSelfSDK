package base64;
import java.io.File;  
import java.util.ArrayList;  
import java.util.List;  
import java.util.Locale;

import sun.rmi.runtime.Log;  
 
/**  
 * @author 54wall 一笃强 
 * @date 创建时间：2016-2-3 下午2:46:37  
 * @version 1.0  
 * 已经增加目录遍历功能，即文件夹及子文件中全部图片全部会遍历得到 
 */  
public class ImageList {  
    static int depth = 0;  
    static String path = null;  
    static List<String> list_all = new ArrayList<String>();  
  
    public static List<String> printDirectory(File f, int depth) {  
        if (!f.isDirectory()) {  
            // 如果不是目录，则打印输出  
            
        } else {  
            File[] fs = f.listFiles();  
            depth++;  
            for (int i = 0; i < fs.length; ++i) {  
                File file = fs[i];  
                path = file.getPath();  
                list_all.add(path);  
                printDirectory(file, depth);  
            }  
        }  
        return list_all;  
    }  
    /** 
     * 将得到的list_all按照结尾是.及其他条件筛选一下是图片的信息 
     *  
     * @param strPath    文件的绝对路径字符串 
     * @param format     后缀名 
     * @return list_last 符合format后缀名称的全部路径 
     */  
    public static List<String> getPictures(final String strPath, String format) {  
  
        List<String> list_last = new ArrayList<String>();  
        List<String> list = new ArrayList<String>();  
        File file = new File(strPath);  
        list = printDirectory(file, depth);  
        list.size();  
        /** 
         * 在循环判断之前，就必须完成树的遍历 
         */  
        for (int k = 0; k < list.size(); k++) {  
  
            int idx = list.get(k).lastIndexOf(".");  
             
            if (idx <= 0) {  
                continue;  
            }  
            String suffix = list.get(k).substring(idx);  
            /* 
             * format可以是".jpg"、".jpeg"等等，例如suffix.toLowerCase().equals(".jpeg") 
             */  
            if (suffix.toLowerCase(Locale.PRC).equals(format)  
            ) {  
                list_last.add(list.get(k));  
            }  
        }  
        /** 
         * 如果没有这个，因为List<String> list_all = new 
         * ArrayList<String>();作为GetEachDir类构造函数的成员变量 
         * ，可以不被清楚，再再次使用GetEachDir的printDirectory,之前的list_all依然存在 
         */  
        list_all.clear();  
        return list_last;  
    }  
}  

