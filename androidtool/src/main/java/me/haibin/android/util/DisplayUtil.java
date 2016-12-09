package me.haibin.android.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import me.haibin.androidtool.R;

/**
 * 显示处理类
 */
public class DisplayUtil {
    private static final String TAG = "DisplayUtil";

    /**
     * 获取状态栏高度
     *
     * @param
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取view截图
     */
    public static Bitmap getCaptureScreen(View view) {
        view.setDrawingCacheEnabled(true); //其中View是你需要截图的的View

        Bitmap bm = view.getDrawingCache();

        return bm;
    }

    /**
     * 获取view截图 并除状态栏
     */
    public static Bitmap getCaptureScreenAndScreen(Activity activity) {
        // 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();
        // 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        Display display = activity.getWindowManager().getDefaultDisplay();
        // 获取屏幕宽和高
        int widths = display.getWidth();
        int heights = display.getHeight();
        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
        // 去掉状态栏
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
                statusBarHeights, widths, heights - statusBarHeights);
        // 销毁缓存信息
        view.destroyDrawingCache();
        return bmp;
    }

    /**
     * 获取根视图
     * @param context
     * @return
     */
    public static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

    /**
     * @param context
     * @return
     */
    public static Point getScreenMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        Log.i(TAG, "Screen---Width = " + w_screen + " Height = " + h_screen + " densityDpi = " + dm.densityDpi);
        return new Point(w_screen, h_screen);

    }

    /**
     * @param context
     * @return
     */
    public static float getScreenRate(Context context) {
        Point P = getScreenMetrics(context);
        float H = P.y;
        float W = P.x;
        return (H / W);
    }

    /**
     * 设置全屏
     *
     * @param activity
     */
    public static void setFullScreen(Activity activity) {
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 取消全屏
     *
     * @param activity
     */
    public static void cancelFullScreen(Activity activity) {
        activity.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 设置隐藏标题栏
     *
     * @param activity
     */
    public static void setNoTitleBar(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }


    /**
     * 设置文字不同大小
     *
     * @param context
     * @param tv
     * @param text
     * @param textBigStart   第一段文字开始位置
     * @param textBigEnd     第一段文字结束位置
     * @param textSmallStart 第二段文字开始位置
     * @param textSmallEnd   第二段文字结束位置
     */
    public static void setTextDiffSize(Context context, TextView tv, String text, int textBigStart, int textBigEnd,
                                       int textSmallStart, int textSmallEnd) {
        SpannableString styledText = new SpannableString(text);
        //不同文字的样式
        //TextStyleBig 设置文本字体的大小
        //TextStyleSmall 设置文本字体的大小
        styledText.setSpan(new TextAppearanceSpan(context, R.style.TextStyleBig), textBigStart, textBigEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(context, R.style.TextStyleSmall), textSmallStart, textSmallEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(styledText, TextView.BufferType.SPANNABLE);
    }



}
