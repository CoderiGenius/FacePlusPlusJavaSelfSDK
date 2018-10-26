package face;

/**
 * Created by 周思成 on  2018/10/26 16:39
 */


import config.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * 设置人脸备注
 */
public class SetFaceID {


    com.alibaba.fastjson.JSONObject setFaceIDReturnJson = null;
    public com.alibaba.fastjson.JSONObject Set(String faceToken,String userID){

        //构建face++请求
        Map<String, String> createMap = new HashMap<String, String>();
        createMap.put("api_key", Config.api_key);
        createMap.put("api_secret", Config.api_secret);
        createMap.put("face_token", faceToken);
        createMap.put("user_id", userID);

        try {
            //发送请求，并将返回结果存为jason对象
            setFaceIDReturnJson = com.alibaba.fastjson.JSONObject.parseObject(http.HttpClientUtil.doPost(Config.setFaceUserID_url, createMap, "UTF-8"));

        } catch (Exception e) {
            Logger.logger.severe("发送请求失败！错误信息：" + e.getMessage());
        }
        return setFaceIDReturnJson;
    }
}
