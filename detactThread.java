import Socket.SetSocket;
import base64.Image;
import base64.ImageList;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;

import java.io.File;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

public class detactThread extends Thread {

    String base64 = "";
    private int confidenceThreshold = 60;
    public void run() {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("MM dd HH mm ss");

        JSONObject jsonObject2 = null;
        String url = "https://api-cn.faceplusplus.com/facepp/v3/search";
        //日志记录
        Main.logger.info("启动人脸检测线程");
        //开始监测缓冲区照片文件
        while (true) {
           String date = df.format(new Date());
            //System.out.print(date);
            List<String> pathList = new ArrayList<String>();
            pathList = ImageList.getPictures("C:\\face\\", ".jpg");

            if (pathList.size() != 0) {
                //日志记录
                Main.logger.info("检测到缓冲区文件");
                //若缓冲区检测到文件，则等待300毫秒，以保证opencv将内容存储完毕
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Main.logger.info("线程休眠300ms失败");
                }
            }
            for (int i = 0; i < pathList.size(); i++) {
                //System.out.println("size:" + pathList.size());
                try {
                    //尝试获取已存入缓冲区的脸照片
                    Main.logger.info("开始转换base64");
                    base64 = Image.getImageStr("C:\\face\\faces.jpg");
                    Main.logger.info("转换base64成功");
                } catch (Exception e3) {
                    Main.logger.severe("转base64失败，疑似缓冲区有其他文件");
                    continue;
                }
                ////System.out.println(base64);
                //判断base64是否为空，若为空则跳过，不发送请求
                if (base64 == "") {
                    Main.logger.severe("base64为空，疑似缓冲区有其他文件");
                    continue;
                }
                //构建face++请求
                Map<String, String> createMap = new HashMap<String, String>();
                createMap.put("api_key", "-d_3qvaR3mV7fYuYNLAt6resHHXWaySy");//试用api
                createMap.put("api_secret", "23bk8NPrl_TSNZUaluOWfdLX9HOmlkP7");//试用api
                //createMap.put("api_key", "OJmMq7ATfzw1_wcwczA5y3ddJ9I8XORm");//正式api
               // createMap.put("api_secret", "jMvybnitr4Mw-7RJJLFSiJGAffhaGVOS");//正式api
                createMap.put("outer_id", "kx");//试用id
                //createMap.put("outer_id","onwalltest");
                createMap.put("image_base64", base64);
                //System.out.println(createMap);
                // JSONObject jsonObject = JSONObject.fromObject(createMap);
                try{
                    //发送请求，并将返回结果存为jason对象
                    jsonObject2 = JSONObject.fromObject(http.HttpClientUtil.doPost(url, createMap, "UTF-8"));
                    //System.out.println(jsonObject2);

                }catch(Exception e){
                    Main.logger.severe("发送请求失败！");
                }
                try {
                    //尝试获取json中的results结果
                    jsonObject2.getString("results");
                } catch (Exception e) {
                    Main.logger.info("照片中无人脸或请求返回异常，具体信息:"+jsonObject2);
                    try {
                        //若没有成功获得results，则证明请求失败或者其他没有成功识别人脸或不是数据库中人脸信息等
                        //执行删除当前缓冲区文件操作
                        File tmpFile = new File("C:\\face\\faces.jpg");//获取文件夹路径
                        if (tmpFile.exists()) {
                            util.Move.moveToFolders("C:\\face\\faces.jpg", "erro" + date + ".jpg", "C:\\faces\\");
                            //Delete.deleteFile("D:\\face\\faces.jpg");
                        } else {
                            // //System.out.println("等待缓冲区图像中……");
                        }
                    } catch (Exception e2) {
                        Main.logger.info("无效识别后清空缓冲区失败");
                        continue;
                    }
                    continue;
                }
                //再次尝试获取results结果
                com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray(jsonObject2.getString("results"));
                com.alibaba.fastjson.JSONObject confidenceArray = com.alibaba.fastjson.JSONObject.parseObject(jsonArray.get(0).toString());
                String confidence = confidenceArray.getString("confidence");
                String name = confidenceArray.getString("user_id");
                //System.out.println("置信度：" + confidence + "，姓名：" + name);
                int confidenceValue = Integer.parseInt(confidence.substring(0, 2));
                //判断置信度是否符合
                if (confidenceValue > confidenceThreshold) {
                    //System.out.println("验证成功");
                    Main.logger.info("验证成功，被识别者："+name+"  置信度："+confidence+"  当前置信度阀值："+confidenceThreshold);
                    //发送消息给门锁
                    Util.SendOutputStream.OutputStream(SetSocket.outputStream,name+"");
                    //验证成功后，等待六秒钟，以保证不会因为同一个人而过多请求，而后执行清除缓冲区
                    try {
                        try{
                            //验证成功，将缓冲区清空，并将验证数据以时间为名称保存至faces文件夹
                            File tmpFile = new File("C:\\face\\faces.jpg");//获取文件夹路径
                            if (tmpFile.exists()) {
                                util.Move.moveToFolders("C:\\face\\faces.jpg", date + ".jpg", "C:\\faces\\");
                                //Delete.deleteFile("D:\\face\\faces.jpg");
                            } else {
                                // //System.out.println("等待缓冲区图像中……");
                            }
                        } catch (Exception e) {
                            Main.logger.info("验证成功后，清空缓冲区失败");
                            e.printStackTrace();
                        }
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Main.logger.info("验证成功后，线程等待休眠6000ms失败");
                        continue;
                    }
                } else {
                    Main.logger.info("验证失败，疑似被识别者："+name+"置信度："+confidence+"当前置信度阀值："+confidenceValue);
                    //System.out.println("验证失败");
                    try {
                        try {
                            //验证成功，将缓冲区清空，并将验证数据以时间为名称保存至faces文件夹
                            File tmpFile = new File("C:\\face\\faces.jpg");//获取文件夹路径
                            if (tmpFile.exists()) {
                                util.Move.moveToFolders("C:\\face\\faces.jpg", date + ".jpg", "C:\\faces\\");
                                //Delete.deleteFile("D:\\face\\faces.jpg");
                            } else {
                                // //System.out.println("等待缓冲区图像中……");
                            }
                        } catch (Exception e) {
                            Main.logger.info("验证成功后，清空缓冲区失败");
                            e.printStackTrace();
                        }
                        Thread.sleep(4000);
                        Main.logger.info("验证成功后，线程等待休眠4000ms");
                    } catch (InterruptedException e) {
                        Main.logger.severe("验证成功后，线程等待休眠4000ms失败");
                        continue;
                    }
                }

                try {
                    //验证成功，将缓冲区清空，并将验证数据以时间为名称保存至faces文件夹
                    File tmpFile = new File("C:\\face\\faces.jpg");//获取文件夹路径
                    if (tmpFile.exists()) {
                        util.Move.moveToFolders("C:\\face\\faces.jpg", date + ".jpg", "C:\\faces\\");
                        //Delete.deleteFile("D:\\face\\faces.jpg");
                    } else {
                        // //System.out.println("等待缓冲区图像中……");
                    }
                } catch (Exception e) {
                    Main.logger.info("验证成功后，清空缓冲区失败");
                    e.printStackTrace();
                }
            }


        }
    }
}

