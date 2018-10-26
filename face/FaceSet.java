package face; /**
 * Created by 周思成 on  2018/10/25 17:04
 */


import com.alibaba.fastjson.JSON;
import config.Config;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * faceset相关操作
 */
public class FaceSet {

    //添加faceset返回的json
    com.alibaba.fastjson.JSONObject creatFaceSetReturnJson = null;

    //添加人脸到faceset返回的json
    com.alibaba.fastjson.JSONObject addFaceReturnJson = null;

    /**
     * 新建faceset
     * @param displayName 人脸集合的名字
     * @param outerID 账号下全局唯一的 FaceSet 自定义标识
     * @return 创建结果的json
     */
    public com.alibaba.fastjson.JSONObject CreatFaceSet(String displayName, String outerID){

        //构建face++请求
        Map<String, String> createMap = new HashMap<String, String>();
        createMap.put("api_key", Config.api_key);
        createMap.put("api_secret", Config.api_secret);
        createMap.put("display_name", displayName);
        createMap.put("outer_id", outerID);

        try{
            //发送请求，并将返回结果存为jason对象
            creatFaceSetReturnJson = com.alibaba.fastjson.JSONObject.parseObject(http.HttpClientUtil.doPost(Config.createFaceSet_url, createMap, "UTF-8"));

        }catch(Exception e){
            Logger.logger.severe("发送请求失败！错误信息："+e.getMessage());
        }
        return creatFaceSetReturnJson;
    }


    /**
     * 添加人脸到faceset
     * @param outerID FaceSet 的标识
     * @param faceTokens 人脸标识
     * @return 返回json
     */
    public com.alibaba.fastjson.JSONObject AddFaceToFaceSet(String outerID,String faceTokens){

        //构建face++请求
        Map<String, String> createMap = new HashMap<String, String>();
        createMap.put("api_key", Config.api_key);
        createMap.put("api_secret", Config.api_secret);
        createMap.put("outer_id", outerID);
        createMap.put("face_tokens", faceTokens);
        System.out.println(createMap);
        try{
            //发送请求，并将返回结果存为jason对象

            addFaceReturnJson = com.alibaba.fastjson.JSONObject.parseObject(http.HttpClientUtil.doPost(Config.addFace_url, createMap, "UTF-8"));

        }catch(Exception e){
            Logger.logger.severe("发送请求失败！错误信息："+e.getMessage());
        }
        return addFaceReturnJson;
    }
}
