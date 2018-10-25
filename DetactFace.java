import base64.Image;
import config.Config;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 周思成 on  2018/10/25 16:27
 */


/**
 * 检测人脸
 */
public class DetactFace {

    //设置日期格式
    SimpleDateFormat df = new SimpleDateFormat("MM dd HH mm ss");

    //返回的json
    JSONObject detactReturnJson = null;

    /**
     * 检测人脸方法
     * @param path
     * @return 检测结果的json
     */
    public JSONObject Detact(String path){


        //获取照片base64

       String base64 = Image.getImageStr(path);
        //构建face++请求
        Map<String, String> createMap = new HashMap<String, String>();
        createMap.put("api_key", Config.api_key_test);
        createMap.put("api_secret", Config.api_secret_test);
        createMap.put("image_base64", base64);

        try{
            //发送请求，并将返回结果存为jason对象
            detactReturnJson = JSONObject.fromObject(http.HttpClientUtil.doPost(Config.detact_url, createMap, "UTF-8"));

        }catch(Exception e){
            Main.logger.severe("发送请求失败！错误信息："+e.getMessage());
        }
        return detactReturnJson;
    }
}
