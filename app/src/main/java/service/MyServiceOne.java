package service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import business.MainManager;
import common.AppContext;
import common.Constants;
import model.AlarmData;
import shuntianfu.com.shuntianfucompressor.MainActivity;
import shuntianfu.com.shuntianfucompressor.R;
import utils.DataHandler;
import utils.Log;

public class MyServiceOne extends Service {

    public final static String TAG = "MyServiceOne";
    public final static int ALARM = 201;
    public final static int NOALARM = 202;


    private static final int TIME = 180000;//1

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            switch (msg.what) {

                case ALARM: {
                    Log.e(TAG, "MyServiceOne Run: " + System.currentTimeMillis());
                    alarm();

                    handler.sendEmptyMessageDelayed(NOALARM, TIME);
                    break;
                }
                case NOALARM: {
                    //无警报
                    Log.e(TAG, "MyServiceOne Run getAlarm: " + System.currentTimeMillis());
                    getAlarm();
                    break;
                }

                default: {
                    break;
                }

            }


        }
    };
    List<AlarmData> alarmDatas = new ArrayList<>();
    private DataHandler alarmDataHandler = new DataHandler() {
        @Override
        public void onData(int code, String msg, Object obj) {
            if (code == Constants.SERVER_SUCCESS) {
                Log.e(TAG, "obj==: " + obj.toString());

                Type listType = new TypeToken<LinkedList<AlarmData>>() {
                }.getType();
                Gson gson = new Gson();

                if (alarmDatas.size() != 0) {
                    alarmDatas.clear();
                }

                alarmDatas = gson.fromJson(obj.toString(), listType);
                android.util.Log.d(TAG, alarmDatas.toString());

                AppContext.getInstance().setAlarmDatas(alarmDatas);

                if (alarmDatas.size() != 0) {

                    handler.sendEmptyMessageDelayed(ALARM, 0);
                } else {

                    handler.sendEmptyMessageDelayed(NOALARM, TIME);
                }

            } else {
                handler.sendEmptyMessageDelayed(NOALARM, TIME);
            }
        }
    };

    /**
     * 响警报
     */
    private void alarm() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification mNotification;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            mNotification = new Notification.Builder(AppContext.getInstance())
                    //                    .addAction(action)
                    .setContentTitle("顺天府压缩机")
                    .setContentText("有数据超过阀值，请查看")
                    .setSmallIcon(R.mipmap.smallicon)
                            //				.setContent(contentView)
                    .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
                    .setOngoing(true)
					.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.b))
                    .build();
        } else {
            mNotification = new Notification.Builder(AppContext.getInstance())
//                .addAction()
                    .setContentTitle("顺天府压缩机")
                    .setContentText("有数据超过阀值，请查看")
                    .setSmallIcon(R.mipmap.smallicon)
                    .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
                    .setOngoing(true)
					.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.b))
                    .getNotification();
        }
//		Notification notification = new Notification();
//        notification.("顺天府压缩机")//设置通知栏标题
//                .setContentText("有数据超过阀值，请查看") // < span style = "font-family: Arial;" >/设置通知栏显示内容</span >
//                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL)) //设置通知栏点击意图
////  .setNumber(number) //设置通知集合的数量
//                .setTicker("警报数据") //通知首次出现在通知栏，带上升动画效果的
//                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
////                .setPriority(Notification.PRIORITY_HIGH) //设置该通知优先级
//                .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
////                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
////                .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
////                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
////                .setDefaults(Notification.DEFAULT_SOUND)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
//                .setSmallIcon(R.drawable.ic_launcher)//设置通知小ICON;
//		mNotification.sound = (Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.a));
        mNotification.flags = (Notification.FLAG_INSISTENT);
        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
//                .setVibrate(new long[]{0, 300, 500, 700});

//        Notification notification = mBuilder.build();

//        notification.flags = Notification.FLAG_INSISTENT;  //让声音、振动无限循环，直到用户响应 （取消或者打开）

//        notification.flags =  Notification.FLAG_ONLY_ALERT_ONCE;  //发起Notification后，铃声和震动均只执行一次

//        notification.flags =  Notification.FLAG_NO_CLEAR ;         //只有全部清除时，Notification才会清除 ，不清楚该通知(QQ的通知无法清除，就是用的这个)

//        notification.flags = Notification.FLAG_FOREGROUND_SERVICE;  //表示正在运行的服务

        mNotificationManager.notify(1, mNotification);
//        AppContext.getInstance().startAlarm();

    }

    public PendingIntent getDefalutIntent(int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), flags);
        return pendingIntent;
    }

    /**
     * 获取警报信息
     */
    private void getAlarm() {
        MainManager.getInstance().getAlarm(AppContext.getInstance(), alarmDataHandler);

    }

    public MyServiceOne() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();


        //网络请求
        getAlarm();

    }

    Thread thread = new Thread(new Runnable() {

        @Override
        public void run() {
            Log.e(TAG, "MyServiceOne Run: " + System.currentTimeMillis());
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {

                @Override
                public void run() {
                    //网络请求

                }
            };
            timer.schedule(task, 0, 5000);
        }
    });

    @Override
    public void onDestroy() {

        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Date d = new Date();

        Intent intent = new Intent(AppContext.getInstance(), MyServiceOne.class);
        //可以通过构建Intent启动任何的东东，activity,service等
        PendingIntent pi = PendingIntent.getService(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);//getService用来启动服务，也可以getActivity启动activity等
        am.set(AlarmManager.RTC_WAKEUP, (d.getTime() + 5000), pi); // 5秒后重新启动

        super.onDestroy();
    }
}
