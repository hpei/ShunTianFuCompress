package common;

import android.app.Application;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import model.AlarmData;
import model.Threshold;
import ui.GetFazhiSucessListener;


/**
 * Created by chuanyhu on 2014/8/a.
 */
public class AppContext extends Application implements GetFazhiSucessListener {

    private static AppContext instance;

    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    public static void setInstance(AppContext instance) {
        AppContext.instance = instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //设置阀值监听
        setListener(this);


    }

    private Handler GetFazhiHandler;
    private GetFazhiSucessListener listener;

    private String dianyaFazhiLower;//是否需要默认值
    private String dianyaFazhi;//是否需要默认值
    private String dianliuFazhiLower;//是否需要默认值
    private String dianliuFazhi;//是否需要默认值
    private String wenduFazhiLower;//是否需要默认值
    private String wenduFazhi;//是否需要默认值
    private String gaoyaFazhiLower;//是否需要默认值
    private String gaoyaFazhi;//是否需要默认值
    private String diyaFazhi;//是否需要默认值
    private String diyaFazhiLower;//是否需要默认值

    public String getDianliuFazhiLower() {
        return dianliuFazhiLower;
    }

    public void setDianliuFazhiLower(String dianliuFazhiLower) {
        this.dianliuFazhiLower = dianliuFazhiLower;
    }

    public String getDianyaFazhiLower() {
        return dianyaFazhiLower;
    }

    public void setDianyaFazhiLower(String dianyaFazhiLower) {
        this.dianyaFazhiLower = dianyaFazhiLower;
    }

    public String getDiyaFazhiLower() {
        return diyaFazhiLower;
    }

    public void setDiyaFazhiLower(String diyaFazhiLower) {
        this.diyaFazhiLower = diyaFazhiLower;
    }

    public String getGaoyaFazhiLower() {
        return gaoyaFazhiLower;
    }

    public void setGaoyaFazhiLower(String gaoyaFazhiLower) {
        this.gaoyaFazhiLower = gaoyaFazhiLower;
    }

    public String getWenduFazhiLower() {
        return wenduFazhiLower;
    }

    public void setWenduFazhiLower(String wenduFazhiLower) {
        this.wenduFazhiLower = wenduFazhiLower;
    }

    private List<AlarmData> AlarmDatas;

    private boolean isAdmin = false;

    private boolean isGetFazhi = false;

    public boolean isGetFazhi() {
        return isGetFazhi;
    }

    public void setGetFazhi(boolean getFazhi) {
        isGetFazhi = getFazhi;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getDianliuFazhi() {
        return dianliuFazhi;
    }

    public void setDianliuFazhi(String dianliuFazhi) {
        this.dianliuFazhi = dianliuFazhi;
    }

    public String getDianyaFazhi() {
        return dianyaFazhi;
    }

    public void setDianyaFazhi(String dianyaFazhi) {
        this.dianyaFazhi = dianyaFazhi;
    }

    public String getDiyaFazhi() {
        return diyaFazhi;
    }

    public void setDiyaFazhi(String diyaFazhi) {
        this.diyaFazhi = diyaFazhi;
    }

    public String getGaoyaFazhi() {
        return gaoyaFazhi;
    }

    public void setGaoyaFazhi(String gaoyaFazhi) {
        this.gaoyaFazhi = gaoyaFazhi;
    }

    public String getWenduFazhi() {
        return wenduFazhi;
    }

    public void setWenduFazhi(String wenduFazhi) {
        this.wenduFazhi = wenduFazhi;
    }

    public Handler getGetFazhiHandler() {
        return GetFazhiHandler;
    }

    public void setGetFazhiHandler(Handler getFazhiHandler) {
        GetFazhiHandler = getFazhiHandler;
    }

    public GetFazhiSucessListener getListener() {
        return listener;
    }

    public void setListener(GetFazhiSucessListener listener) {
        this.listener = listener;
    }

    public List<AlarmData> getAlarmDatas() {
        return AlarmDatas;
    }

    public void setAlarmDatas(List<AlarmData> alarmDatas) {
        try {
            if (null != AlarmDatas) {
                AlarmDatas.clear();
            }
            AlarmDatas = alarmDatas;
        } catch (Exception ex) {
            Log.e("APPCONTEXT", ex.toString());
        }

    }

    @Override
    public void onSucess(Threshold threshold) {
        if (!TextUtils.isEmpty(threshold.getDianyaUpper())) {
            setDianyaFazhi(threshold.getDianyaUpper());
        }
        if (!TextUtils.isEmpty(threshold.getDianyaLower())) {
            setDianyaFazhiLower(threshold.getDianyaLower());
        }
        if (!TextUtils.isEmpty(threshold.getDianliuUpper())) {
            setDianliuFazhi(threshold.getDianliuUpper());
        }
        if (!TextUtils.isEmpty(threshold.getDianliuLower())) {
            setDianliuFazhiLower(threshold.getDianliuLower());
        }
        if (!TextUtils.isEmpty(threshold.getWenduUpper())) {
            setWenduFazhi(threshold.getWenduUpper());
        }
        if (!TextUtils.isEmpty(threshold.getWenduLower())) {
            setWenduFazhiLower(threshold.getWenduLower());
        }
        if (!TextUtils.isEmpty(threshold.getGaoyaUpper())) {
            setGaoyaFazhi(threshold.getGaoyaUpper());
        }
        if (!TextUtils.isEmpty(threshold.getGaoyaLower())) {
            setGaoyaFazhiLower(threshold.getGaoyaLower());
        }
        if (!TextUtils.isEmpty(threshold.getDiyaUpper())) {
            setDiyaFazhi(threshold.getDiyaUpper());
        }
        if (!TextUtils.isEmpty(threshold.getDiyaLower())) {
            setDiyaFazhiLower(threshold.getDiyaLower());
        }
        Log.e("AppContext", threshold.toString());
    }
}

