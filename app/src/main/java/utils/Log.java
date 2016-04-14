package utils;

/**
 * Created by xinqi on 2016/2/2
 */
public class Log {
	public static boolean DEBUG = true;

	public static void i(String tag, String string) {
		if (DEBUG) {
			while (string.length() > 3 * 1000) {
				android.util.Log.i(tag, string.substring(0, 3 * 1000));
				string = string.substring(3 * 1000);
			}
			android.util.Log.i(tag, string);
		}
	}

	public static void e(String tag, String string) {
		if (DEBUG) {
			while (string.length() > 3 * 1000) {
				android.util.Log.e(tag, string.substring(0, 3 * 1000));
				string = string.substring(3 * 1000);
			}
			android.util.Log.e(tag, string);
		}
	}

	public static void d(String tag, String string) {
		if (DEBUG) {
			while (string.length() > 3 * 1000) {
				android.util.Log.d(tag, string.substring(0, 3 * 1000));
				string = string.substring(3 * 1000);
			}
			android.util.Log.d(tag, string);
		}
	}

	public static void v(String tag, String string) {
		if (DEBUG) {
			while (string.length() > 3 * 1000) {
				android.util.Log.v(tag, string.substring(0, 3 * 1000));
				string = string.substring(3 * 1000);
			}
			android.util.Log.v(tag, string);
		}
	}

	public static void w(String tag, String string) {
		if (DEBUG) {
			while (string.length() > 3 * 1000) {
				android.util.Log.w(tag, string.substring(0, 3 * 1000));
				string = string.substring(3 * 1000);
			}
			android.util.Log.w(tag, string);
		}
	}
}
