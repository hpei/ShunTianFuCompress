package utils;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

/**
 * 网络类型判断
 *
 * @author Administrator
 *
 */
public class CheckNetUtil {
    private static final Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");//4.0模拟器屏蔽掉该权限

    /**
     * 判断网络情况
     *
     * @return
     */
    public static boolean checkNetWork(Context context) {
        // 重点判断wap方式
        // 操作步骤：
        // 判断联网的渠道：WLAN VS APN
        boolean isWIFI = isWIFIConnection(context);
        boolean isAPN = isAPNConnection(context);

        // 分支：如果isWIFI、isAPN均为false
        if (!isWIFI && !isAPN) {
            return false;
        }

        // 如果是APN：区分NET OR WAP（代理信息的设置）
//        if (isAPN) {
//            readAPN(context);// 读联系人
//        }

        return true;
    }

    /**
     * 读取APN配置信息
     *
     * @param context
     */
    private static void readAPN(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        // uri:被选中的通信APN
        Cursor cursor = contentResolver.query(PREFERRED_APN_URI, null, null, null, null);// 只能返回一个结果，当前出与链接的APN信息
        if(cursor!=null&&cursor.moveToFirst())
        {

        }
    }

    /**
     * 判断MOBILE的链接状态
     *
     * @param context
     * @return
     */
    private static boolean isAPNConnection(Context context) {
        // 获取到系统服务——关于链接的管理
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取到WIFI的链接描述信息
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkInfo != null) {
            return networkInfo.isConnected();
        }
        return false;
    }

    /**
     * 判断WIFI的链接状态
     *
     * @param context
     * @return
     */
    private static boolean isWIFIConnection(Context context) {
        // 获取到系统服务——关于链接的管理
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取到WIFI的链接描述信息
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null) {
            return networkInfo.isConnected();
        }
        return false;
    }

}
