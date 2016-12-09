package me.haibin.android.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.graphics.BitmapFactory.decodeFile;

/**
 * 图片工具类
 */
public class ImageUtil {

    /**
     * 压缩bitmap(从本地图片)-->有旋转
     *
     * @param imagePath 本地图片路径
     * @param scale     压缩比率 可以为空,那么是默认是200,越大压缩比越小
     * @return 一个缩放好的bitmap
     */
    public static Bitmap fileZoomBitmapByRotate(String imagePath, Float scale) {
        //得到图片旋转角度,主要针对三星
        int degree = readPictureDegree(imagePath);

        if (!new File(imagePath).exists()) {
            return null;
        }

        if (scale == null) {
            scale = 200f;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bm = decodeFile(imagePath, options);
        options.inJustDecodeBounds = false;
        int be = (int) (options.outHeight / scale);
        if (be <= 1) {
            be = 1;
        }
        options.inSampleSize = be;// be=2.表示压缩为原来的1/2,以此类推
        bm = decodeFile(imagePath, options);
        //旋转角度
        bm = getRotateBitmap(bm, degree);
        return bm;
    }

    /**
     * 没有旋转
     *
     * @param imagePath
     * @param scale
     * @return
     */
    public static Bitmap fileZoomBitmap(String imagePath, Float scale) {
        //得到图片旋转角度,主要针对三星

        if (!new File(imagePath).exists()) {
            return null;
        }

        if (scale == null) {
            scale = 200f;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bm = decodeFile(imagePath, options);
        options.inJustDecodeBounds = false;
        int be = (int) (options.outHeight / scale);
        if (be <= 1) {
            be = 1;
        }
        options.inSampleSize = be;// be=2.表示压缩为原来的1/2,以此类推
        bm = decodeFile(imagePath, options);
        return bm;
    }

    /**
     * 压缩bitmap(从res图片)
     *
     * @param res
     * @param drawable
     * @param scale
     * @return
     */
    public static Bitmap resZoomBitmap(Resources res, int drawable, Float scale) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bm = BitmapFactory.decodeResource(res, drawable, options);
        options.inJustDecodeBounds = false;
        //这里可以得到bm的狂傲尺寸信息而不需要把bm加载到内存中
        int be = (int) (options.outHeight / scale);
        if (be <= 1) {
            be = 1;
        }
        options.inSampleSize = be;// be=2.表示压缩为原来的1/2,以此类推
        bm = BitmapFactory.decodeResource(res, drawable, options);
        return bm;
    }

    /**
     * 压缩文件
     *
     * @param f
     * @param mBitmap
     */
    public static File bitmapZoomFile(File f, Bitmap mBitmap) {
        try {
            f.createNewFile();
            FileOutputStream fOut = new FileOutputStream(f);
            //bitmap和流压缩到文件中,100为不压缩,50即压缩50%
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    /**
     * 旋转Bitmap(照相用)
     *
     * @param b
     * @param rotateDegree
     * @return
     */
    public static Bitmap getRotateBitmap(Bitmap b, float rotateDegree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotateDegree);
        Bitmap rotaBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, false);
        return rotaBitmap;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * Get bitmap from specified image path
     *
     * @param imgPath
     * @return
     */
    public static Bitmap getBitmap(String imgPath) {
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        // Do not compress
        newOpts.inSampleSize = 1;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }

    /**
     * Store bitmap into specified image path
     *
     * @param bitmap
     * @param outPath
     * @throws FileNotFoundException
     */
    public static void storeImage(Bitmap bitmap, String outPath) throws FileNotFoundException {
        FileOutputStream os = new FileOutputStream(outPath);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
    }

    /**
     * Compress image by pixel, this will modify image width/height.
     * Used to get thumbnail
     *
     * @param imgPath image path
     * @param pixelW  target pixel of width
     * @param pixelH  target pixel of height
     * @return
     */
    public static Bitmap ratio(String imgPath, float pixelW, float pixelH) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        // Get bitmap info, but notice that bitmap is null now
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 想要缩放的目标尺寸
        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        // 开始压缩图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        // 压缩好比例大小后再进行质量压缩
//        return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }

    /**
     * Compress image by size, this will modify image width/height.
     * Used to get thumbnail
     *
     * @param image
     * @param pixelW target pixel of width
     * @param pixelH target pixel of height
     * @return
     */
    public static Bitmap ratio(Bitmap image, float pixelW, float pixelH) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
        if (os.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            os.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, os);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        is = new ByteArrayInputStream(os.toByteArray());
        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        //压缩好比例大小后再进行质量压缩
//      return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }

    /**
     * Compress by quality,  and generate image to the path specified
     *
     * @param image
     * @param outPath
     * @param maxSize target will be compressed to be smaller than this size.(kb)
     * @throws IOException
     */
    public static void compressAndGenImage(Bitmap image, String outPath, int maxSize) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // scale
        int options = 100;
        // Store the bitmap into output stream(no compress)
        image.compress(Bitmap.CompressFormat.JPEG, options, os);
        // Compress by loop
        //这里是bitmap本身压缩还是压缩还是文件压缩
        while (os.toByteArray().length / 1024 > maxSize) {
            // Clean up os
            os.reset();
            // interval 10
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
        }

        // Generate compressed image file
        FileOutputStream fos = new FileOutputStream(outPath);
        fos.write(os.toByteArray());
        fos.flush();
        fos.close();
    }

    /**
     * Compress by quality,  and generate image to the path specified
     *
     * @param imgPath
     * @param outPath
     * @param maxSize     target will be compressed to be smaller than this size.(kb)
     * @param needsDelete Whether delete original file after compress
     * @throws IOException
     */
    public static void compressAndGenImage(String imgPath, String outPath, int maxSize, boolean needsDelete) throws IOException {
        compressAndGenImage(getBitmap(imgPath), outPath, maxSize);

        // Delete original file
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * Ratio and generate thumb to the path specified
     *
     * @param image
     * @param outPath
     * @param pixelW  target pixel of width
     * @param pixelH  target pixel of height
     * @throws FileNotFoundException
     */
    public static void ratioAndGenThumb(Bitmap image, String outPath, float pixelW, float pixelH) throws FileNotFoundException {
        Bitmap bitmap = ratio(image, pixelW, pixelH);
        storeImage(bitmap, outPath);
    }

    /**
     * Ratio and generate thumb to the path specified
     *
     * @param imgPath
     * @param outPath
     * @param pixelW      target pixel of width
     * @param pixelH      target pixel of height
     * @param needsDelete Whether delete original file after compress
     * @throws FileNotFoundException
     */
    public static void ratioAndGenThumb(String imgPath, String outPath, float pixelW, float pixelH, boolean needsDelete) throws FileNotFoundException {
        Bitmap bitmap = ratio(imgPath, pixelW, pixelH);
        storeImage(bitmap, outPath);

        // Delete original file
        if (needsDelete) {
            File file = new File(imgPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 对扫脸获取的图片字节数组第一次压缩,防止OOM和卡顿
     *
     * @param imgByte
     * @return
     */
    public static Bitmap byteToBitmap(byte[] imgByte) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length, options);
        //根据参数128*128的值改变inSampleSize的大小,参数越大inSampleSize相对越大
        options.inSampleSize = computeSampleSize(options, -1, 500 * 500);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length, options);

        return bitmap;
    }

    /**
     * 根据安卓源码得来的获取最佳inSampleSize缩小比例的方法
     *
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }



    /**
     * 获取{@link Bitmap}在内存中占用的字节大小
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static int getBitmapByteSize(Bitmap bitmap) {

        //检查bitmap是否被销毁
        if (bitmap.isRecycled()) {
            throw new IllegalStateException("Cannot obtain size for recycled Bitmap: " + bitmap
                    + "[" + bitmap.getWidth() + "x" + bitmap.getHeight() + "] " + bitmap.getConfig());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // Workaround for KitKat initial release NPE in Bitmap, fixed in MR1. See issue #148.
            try {
                return bitmap.getAllocationByteCount();
            } catch (NullPointerException e) {
                // Do nothing.
            }
        }
        return bitmap.getHeight() * bitmap.getRowBytes();
    }

    /**
     * 获取{@link Bitmap}在内存中占用的字节大小
     */
    public static int getBitmapByteSize(int width, int height, Bitmap.Config config)
    {
        return width * height * getBytesPerPixel(config);
    }

    /**
     * 检查图片的宽高
     */
    public static int[] getImageDimensions(String path)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        return new int[]{imageWidth,imageHeight};
    }

    /**
     * 检查图片类型
     */
    public static String getImageType(String path)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);
        return options.outMimeType;
    }


    private static int getBytesPerPixel(Bitmap.Config config) {
        // A bitmap by decoding a gif has null "config" in certain environments.
        if (config == null) {
            config = Bitmap.Config.ARGB_8888;
        }

        int bytesPerPixel;
        switch (config) {
            case ALPHA_8:
                bytesPerPixel = 1;
                break;
            case RGB_565:
            case ARGB_4444:
                bytesPerPixel = 2;
                break;
            case ARGB_8888:
            default:
                bytesPerPixel = 4;
        }
        return bytesPerPixel;
    }



    /**
     * 将Drawable转换成Bitmap
     * @param context   上下文
     * @param drawable  {@link Drawable}
     * @return {@link Bitmap}
     */
    public static Bitmap toBitmap(@NonNull Context context, @DrawableRes int drawable)
    {
        return toBitmap(ContextCompat.getDrawable(context,drawable));
    }

    /**
     * 将Drawable转换成Bitmap
     * @param drawable  {@link Drawable}
     * @return {@link Bitmap}
     */
    public static Bitmap toBitmap(@NonNull Drawable drawable)
    {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 将Drawable转换成Bitmap并缩放到制定的宽高
     * @param context   上下文
     * @param drawable  {@link Drawable}
     * @param newWidth  指定宽度PX
     * @param newHeight 指定高度PX
     * @return  {@link Bitmap}
     */
    public static Bitmap toBitmapAndScale(@NonNull Context context,@DrawableRes int drawable,float newWidth,float newHeight)
    {
        return toBitmapAndScale(ContextCompat.getDrawable(context,drawable),newWidth,newHeight);
    }

    /**
     * 将Drawable转换成Bitmap并缩放到制定的宽高
     * @param drawable  {@link Drawable}
     * @param newWidth  指定宽度PX
     * @param newHeight 指定高度PX
     * @return  {@link Bitmap}
     */
    public static Bitmap toBitmapAndScale(@NonNull Drawable drawable,float newWidth,float newHeight)
    {
        int width = drawable.getIntrinsicWidth();
        int height= drawable.getIntrinsicHeight();

        Bitmap oldbmp = toBitmap(drawable);

        Matrix matrix = new Matrix();
        float scaleWidth = (newWidth/ (float)width);
        float scaleHeight = (newHeight/ (float)height);

        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);
        oldbmp.recycle();

        return newbmp;
    }

    /**
     * Drawable 染色
     * @param drawable  {@link Drawable}
     * @param color     颜色值
     * @return  染色后的Drawable
     */
    public static Drawable Tint(@NonNull Drawable drawable,int color)
    {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }


}
