# FacePlusPlusJavaSelfSDK
## 使用时请注意新建config包，并在包下放config.java 的配置信息，如下：
```
package config;

/**
 * Created by 周思成 on  2018/10/25 16:27
 */

public class Config {

    public  static  String api_key_test = "";
    public  static  String api_secret_test = "";
    public static  String api_secret = "";
    public static String api_key = "";
    public static String detact_url = "https://api-cn.faceplusplus.com/facepp/v3/detect";
    public static String createFaceSet_url = "https://api-cn.faceplusplus.com/facepp/v3/faceset/create";
    public static String addFace_url = "https://api-cn.faceplusplus.com/facepp/v3/faceset/addface";
    public static String searchFace_url = "https://api-cn.faceplusplus.com/facepp/v3/search";
}

```