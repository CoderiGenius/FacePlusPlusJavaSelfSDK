package util;

import java.io.File;
import java.util.logging.Logger;


public class Move {
    public static Logger logger = util.MainLogger.setLoggerHanlder(Logger.getLogger("logger"));
    public static void moveToFolders(String startPath,String fileName,String endPath){
        try {
            File startFile = new File(startPath);
            File tmpFile = new File(endPath);//获取文件夹路径
            if(!tmpFile.exists()){//判断文件夹是否创建，没有创建则创建新文件夹
                tmpFile.mkdirs();
            }
           // //System.out.println(endPath + startFile.getName());
            if (startFile.renameTo(new File(endPath + fileName))) {
                logger.info("文件移动成功");
                //System.out.println("File is moved successful!");
                //System.out.println("文件移动成功！1");
            } else {
                logger.info("文件移动失败2");
                //System.out.println("文件移动失败！2");
            }
        } catch (Exception e) {
            logger.info("文件移动失败3");

        }
    }
}
