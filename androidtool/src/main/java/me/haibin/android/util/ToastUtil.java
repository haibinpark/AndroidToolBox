package me.haibin.android.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 保证整个应用都只有一个非阻塞消息提示,当需要连续不断的出现非阻塞消息时,为了避免下一个非阻塞消息影响上一个非阻塞消息效果用!
 */
public class ToastUtil {

	private static Toast toast;

	public static void showToast(Context context, String msg){
		if(toast == null){
			toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		}
		toast.setText(msg);
		toast.show();
	}
	
}
