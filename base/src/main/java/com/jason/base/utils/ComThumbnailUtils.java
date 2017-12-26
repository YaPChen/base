package com.jason.base.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.ThumbnailUtils;

/**
 * Created by yaping on 2016/1/13.
 */
public class ComThumbnailUtils {
    private static final String TAG = "ComThumbnailUtils";
    private static final int OPTIONS_NONE = 0;
    private static final int OPTIONS_SCALE_UP = 1;
    public static final int OPTIONS_RECYCLE_INPUT = 2;
    public static final int TARGET_SIZE_MINI_THUMBNAIL = 320;
    public static final int TARGET_SIZE_MICRO_THUMBNAIL = 96;

    public ComThumbnailUtils() {
    }

    public static Bitmap createImageThumbnail(String filePath, int kind) {
        return ThumbnailUtils.createVideoThumbnail(filePath, kind);
    }

    public static Bitmap createVideoThumbnail(String filePath, int kind) {
        return ThumbnailUtils.createVideoThumbnail(filePath, kind);
    }

    public static Bitmap extractThumbnail(Bitmap source, int width, int height) {
        return ThumbnailUtils.extractThumbnail(source, width, height);
    }

    public static Bitmap extractThumbnail(Bitmap source, int width, int height, int options) {
        if(source == null) {
            return null;
        } else {
            float scale;
            if(source.getWidth() < source.getHeight()) {
                scale = (float)width / (float)source.getWidth();
            } else {
                scale = (float)height / (float)source.getHeight();
            }

            Matrix matrix = new Matrix();
            matrix.setScale(scale, scale);
            Bitmap thumbnail = transform(matrix, source, width, height, 1 | options);
            return thumbnail;
        }
    }

    private static Bitmap transform(Matrix scaler, Bitmap source, int targetWidth, int targetHeight, int options) {
        boolean scaleUp = (options & 1) != 0;
        boolean recycle = (options & 2) != 0;
        int deltaX = source.getWidth() - targetWidth;
        int deltaY = source.getHeight() - targetHeight;
        if(!scaleUp && (deltaX < 0 || deltaY < 0)) {
            Bitmap bitmapWidthF1 = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
            Canvas bitmapHeightF1 = new Canvas(bitmapWidthF1);
            int scaleH1 = Math.max(0, deltaX / 2);
            int scaleW1 = Math.max(0, deltaY / 2);
            Rect scale1 = new Rect(scaleH1, scaleW1, scaleH1 + Math.min(targetWidth, source.getWidth()), scaleW1 + Math.min(targetHeight, source.getHeight()));
            int b11 = (targetWidth - scale1.width()) / 2;
            int dstY = (targetHeight - scale1.height()) / 2;
            Rect dst = new Rect(b11, dstY, targetWidth - b11, targetHeight - dstY);
            bitmapHeightF1.drawBitmap(source, scale1, dst, (Paint)null);
            if(recycle) {
                source.recycle();
            }

            bitmapHeightF1.setBitmap((Bitmap)null);
            return bitmapWidthF1;
        } else {
            float bitmapWidthF = (float)source.getWidth();
            float bitmapHeightF = (float)source.getHeight();
            float scaleH = (float)targetHeight / bitmapHeightF;
            float scaleW = (float)targetWidth / bitmapWidthF;
            float scale = Math.min(scaleH, scaleW);
            if(scale >= 0.9F && scale <= 1.0F) {
                scaler = null;
            } else {
                scaler.setScale(scale, scale);
            }

            Bitmap b1;
            if(scaler != null) {
                b1 = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), scaler, true);
            } else {
                b1 = source;
            }

            if(recycle && b1 != source) {
                source.recycle();
            }

            return b1;
        }
    }
}
