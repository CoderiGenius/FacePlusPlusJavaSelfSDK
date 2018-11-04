import Socket.TCP;
import com.alibaba.fastjson.JSON;
import face.DetactFace;
import face.FaceSet;
import face.SetFaceID;

import java.util.logging.Logger;

public class Main {
    public static Logger logger = util.MainLogger.setLoggerHanlder(Logger.getLogger("logger"));
    public static void main(String args[]) {

       /* DetactFace detactFace = new DetactFace();

        com.alibaba.fastjson.JSONObject jsonObject =  detactFace.Detact("");
        System.out.println(jsonObject.get("faces"));
        com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray(jsonObject.get("faces").toString());
*/

        /*SetFaceID setFaceID = new SetFaceID();
        System.out.println(setFaceID.Set("064850e6ddb3d9e227360c588b4f22a8"
                ,getFileName("")));*/
        FaceSet faceSetForAddFace = new FaceSet();
        System.out.println(faceSetForAddFace.AddFaceToFaceSet("18", "83fd2402890144690669c799ba16c858"));
    }

    private static String getFileName(String path){

        String fileName = path.split("\\\\")[4];
        String name = fileName.split("\\.")[0];
        return name;
    }
}
