package utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by haopei on 2016/3/31.
 */
public class AlarmUtil {
    /**
     * 响警报
     * @param context
     * @param alarm
     */
    public static void runAlarm(Context context,int alarm){
        MediaPlayer mediaPlayer = MediaPlayer.create(context,alarm);
        mediaPlayer.setVolume(1, 1);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }
}
