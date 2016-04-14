package utils;

import android.content.Context;

public class DP2Tools {
	/**
	 * dip转为 px
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = getScale(context);
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * px 转为 dip
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = getScale(context);
		return (int) (pxValue / scale + 0.5f);
	}

	public static float getScale(Context context) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return scale;
	}
}
