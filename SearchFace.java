/**
 * Created by 周思成 on  2018/10/25 17:18
 */


import base64.Image;
import config.Config;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 人脸搜索
 */
public class SearchFace {


    //搜索返回json
    JSONObject searchReturnJson;


    /**
     * 通过base64搜索人脸
     *
     * @param base64
     * @param facesetToken
     * @return 搜索结果json
     */
    private JSONObject SearchByBase64(String base64, String facesetToken) {


        //构建face++请求
        Map<String, String> createMap = new HashMap<String, String>();
        createMap.put("api_key", Config.api_key_test);
        createMap.put("api_secret", Config.api_secret_test);
        createMap.put("faceset_token", facesetToken);
        createMap.put("image_base64", base64);
        createMap.put("return_result_count", "5");
        try {
            //发送请求，并将返回结果存为jason对象
            searchReturnJson = JSONObject.fromObject(http.HttpClientUtil.doPost(Config.searchFace_url, createMap, "UTF-8"));

        } catch (Exception e) {
            Main.logger.severe("发送请求失败！错误信息：" + e.getMessage());
        }
        return searchReturnJson;
    }


    /**
     * 通过二进制文件搜索人脸
     * @param path 路径
     * @param facesetToken 人脸集合
     * @return 搜索结果
     */
    private JSONObject SearchByFile(String path, String facesetToken) {

        //获取照片base64
        String base64 = Image.getImageStr(path);
        return SearchByBase64(base64, facesetToken);
    }
}
