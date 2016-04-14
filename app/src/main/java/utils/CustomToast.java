package utils;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import shuntianfu.com.shuntianfucompressor.R;


public class CustomToast {

	private Context mContext;
	private WindowManager wm;
	private int mDuration;
	private View mNextView;
	public static final int LENGTH_SHORT = 1500;
	public static final int LENGTH_LONG = 3000;

	public CustomToast(Context context) {
		mContext = context.getApplicationContext();
		wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
	}

	public static CustomToast showToast(Context context, CharSequence text, int duration) {
		CustomToast result = new CustomToast(context);
		LayoutInflater inflate = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflate.inflate(R.layout.custom, null);
//		v.setBackgroundColor(Color.argb(150, 0, 0, 0));
		TextView tv = (TextView) v.findViewById(R.id.message);
		tv.setText(text);
		result.mNextView = v;
		result.mDuration = duration;
		result.show();
		return result;
	}

	public static void showIconToast(Context context, CharSequence text, int drawId, int duration){
		CustomToast result = new CustomToast(context);
		LayoutInflater inflate = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflate.inflate(R.layout.custom_icon, null);
		ImageView iv = (ImageView) v.findViewById(R.id.icon_img);
		iv.setImageResource(drawId);

		TextView tv = (TextView) v.findViewById(R.id.icon_message);
		tv.setText(text);

//		Drawable drawable = context.getResources().getDrawable(drawId);
//		int width = DP2Tools.dip2px(context,24);
//		int height = DP2Tools.dip2px(context,24);
//		drawable.setBounds(0,0,width,height);
//		tv.setCompoundDrawables(null, drawable, null, null);

		result.mNextView = v;
		result.mDuration = duration;
		result.showIcon();

	}

	public static CustomToast showToast(Context context, int resId, int duration) throws Resources.NotFoundException {
		return showToast(context, context.getResources().getText(resId), duration);
	}

	public void show() {
		if (mNextView != null) {
			WindowManager.LayoutParams params = new WindowManager.LayoutParams();
			params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
//			params.gravity = Gravity.CENTER;
			params.height = WindowManager.LayoutParams.WRAP_CONTENT;
			params.width = WindowManager.LayoutParams.WRAP_CONTENT;
			params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
			params.format = PixelFormat.TRANSLUCENT;
			params.windowAnimations = R.style.Animation_Toast;
			params.y = DP2Tools.dip2px(mContext, 64);
			params.type = WindowManager.LayoutParams.TYPE_TOAST;
			wm.addView(mNextView, params);
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (mNextView != null) {
						wm.removeView(mNextView);
						mNextView = null;
						wm = null;
					}
				}
			}, mDuration);
		}
	}

	public void showIcon() {
		if (mNextView != null) {
			WindowManager.LayoutParams params = new WindowManager.LayoutParams();
			params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
			params.height = WindowManager.LayoutParams.WRAP_CONTENT;
			params.width = WindowManager.LayoutParams.WRAP_CONTENT;
			params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
			params.format = PixelFormat.TRANSLUCENT;
			params.windowAnimations = R.style.Animation_Toast;
			params.y = DP2Tools.dip2px(mContext, 64);//距离底部高度
			params.type = WindowManager.LayoutParams.TYPE_TOAST;
			wm.addView(mNextView, params);
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					if (mNextView != null) {
						wm.removeView(mNextView);
						mNextView = null;
						wm = null;
					}
				}
			}, mDuration);
		}
	}
}