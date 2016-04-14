package common;

import android.os.Handler;

/**
 * Created by haopei on 2016/3/25.
 */
public class Constants {
//    public static final String PRESSURE = "pressure";//电压
//    public static final String  MAELECTRIC= "MAelectric";//电流
//    public static final String TEMPERTUREVALUE = "temperaturevalue";//末端温度值
//    public static final String HIGHPREESUREVALUE = "highpressurevalue";//高压值
//    public static final String LOWPREESUREVALUE = "lowpressurevalue";//低压值


    public static final int DIAYA = 0;//电压
    public static final int DIANLIU = 1;//电流
    public static final int WENDU = 2;//末端温度值
    public static final int GAOYA = 3;//高压值
    public static final int DIYA = 4;//低压值

    public static final String SERVER_HOST = "http://123.56.88.88:8080/supermarket/rest/";


    public static final int LOCAL_ERROR = -1;
    public static final int SERVER_ERROR = 0;
    public static final int SERVER_SUCCESS = 1;
    public static final int SERVER_FAILED = 2;

    public static final String USER_NAME = "user_name";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_TOKEN = "user_token";
    public static final String NOWCOMPRESSOR = "now_compressor";

    public static final int AUTHSUCESS = 101;
    public static final int AUTHERRO = 102;
    public static final int GETFAZHISUCESS = 103;
    public static final int GETCPListSUCESS = 104;




    /**
     * 登录
     * 参数 userName 和 password (admin1  222)
     */
    public static final String USER_LOGIN = "user/login";

    /**
     * 添加用户
     * 参数：userName=***&password=***&role=（1为普通用户 2为管理官）
     */
    public static final String USER_ADD = "user/createUser";
    /**
     * 获取压缩机列表 必须先登录
     */
    public static final String COMPRESSOR_LIST = "compressor/compressorList";
    /**
     * 获取 压缩机的实时状态
     * 参数startTime=1459334211297&endTime=1459335116745&compressorName=压缩机1
     * 都是需要先登录的
     */
    public static final String REAL_TIME_LIST = "realtime/realtimeList";
    /**
     * 获取压缩机各状态的阀值
     * 查询阀值和修改阀值那儿必须是管理员登录 (admin2    222     这是管理员
     */
    public static final String QUERY_THRESHOLD = "threshold/queryThreshold";
    /**
     * 更新各个阀值
     * 参数:
     * dianyaThreshold
     * dianliuThreshold
     * wenduThreshold
     * gaoyaThreshold
     * diyaThreshold
     */
    public static final String UPDATE_THRESHOLD = "threshold/updateThreshold";
    /**
     * 获取报警阀值
     */
    public static final String GET_ALARM_FAZHI = "usermsg/getMsg";

    public static final int SYNC_TIMEOUT = 6 * 1000;
    public static final int REQUEST_TIMEOUT = 6 * 1000;//原先都是60s
    public static final String LANGUAGE = "zh";

    public static class Error {
        public static final int NETWORK_IS_UNREACHABLE = 10001;//网络不可用
    }


}
