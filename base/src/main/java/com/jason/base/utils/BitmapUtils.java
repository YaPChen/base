package com.jason.base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import junit.framework.Assert;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class BitmapUtils {

	private static final String TAG = "BitmapUtils";
	private Context mContext;
	private static final long MAX_BILL_BITMAP_SIZE = 2764800L;
	private static int mScreenWidth;
	private static int mScreenHeight;
	private static BitmapUtils mInstance = new BitmapUtils();
	private static final int MAX_DECODE_PICTURE_SIZE = 2764800;

	private BitmapUtils() {
	}

	public static BitmapUtils getInstance() {
		return mInstance;
	}

	public void setContext(Context context) {
		this.mContext = context;
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		mScreenHeight = dm.heightPixels;
		mScreenWidth = dm.widthPixels;
	}

	public static Intent createCaptureIntent(Uri uri) {
		Intent capture = new Intent("android.media.action.IMAGE_CAPTURE");
		capture.putExtra("output", uri);
		return capture;
	}

	public static Intent createGalleryIntent() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction("android.intent.action.GET_CONTENT");
		return intent;
	}

	public static Bitmap scaleBitmapFile(File bitmapFile, int width, int height) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		Bitmap scaleBitmap;
		if(bitmapFile.length() > 2764800L) {
			opt.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(bitmapFile.getAbsolutePath(), opt);
			opt.inSampleSize = calculateInSampleSize(opt, width, height);
			opt.inJustDecodeBounds = false;
			scaleBitmap = BitmapFactory.decodeFile(bitmapFile.getAbsolutePath(), opt);
			return ComThumbnailUtils.extractThumbnail(scaleBitmap, width, height, 2);
		} else {
			scaleBitmap = BitmapFactory.decodeFile(bitmapFile.getAbsolutePath(), opt);
			return ComThumbnailUtils.extractThumbnail(scaleBitmap, width, height, 2);
		}
	}

	public static Bitmap getSuitedBitmap(Resources resources, int id, int width, int height) {
		BitmapFactory.Options options = new BitmapFactory.Options();

		try {
			options.inJustDecodeBounds = true;
			Bitmap e = BitmapFactory.decodeResource(resources, id, options);
			if(e != null) {
				e.recycle();
				e = null;
			}

			Log.d("BitmapUtils", "extractThumbNail: round=" + width + "x" + height);
			double beY = (double)options.outHeight * 1.0D / (double)height;
			double beX = (double)options.outWidth * 1.0D / (double)width;
			Log.d("BitmapUtils", "extractThumbNail: extract beX = " + beX + ", beY = " + beY);
			options.inSampleSize = (int)(beY < beX?beX:beY);
			if(options.inSampleSize <= 1) {
				options.inSampleSize = 1;
			}

			while((long)(options.outHeight * options.outWidth / options.inSampleSize) > 2764800L) {
				++options.inSampleSize;
			}

			int newHeight = height;
			int newWidth = width;
			if(beY < beX) {
				newHeight = (int)((double)width * 1.0D * (double)options.outHeight / (double)options.outWidth);
			} else {
				newWidth = (int)((double)height * 1.0D * (double)options.outWidth / (double)options.outHeight);
			}

			options.inJustDecodeBounds = false;
			Log.i("BitmapUtils", "bitmap required size=" + newWidth + "x" + newHeight + ", orig=" + options.outWidth + "x" + options.outHeight + ", sample=" + options.inSampleSize);
			Bitmap bm = BitmapFactory.decodeResource(resources, id, options);
			if(bm == null) {
				Log.e("BitmapUtils", "bitmap decode failed");
				return null;
			} else {
				Log.i("BitmapUtils", "bitmap decoded size=" + bm.getWidth() + "x" + bm.getHeight());
				Bitmap scale = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
				if(scale != null) {
					bm.recycle();
					bm = scale;
				}

				return bm;
			}
		} catch (OutOfMemoryError var14) {
			Log.e("BitmapUtils", "decode bitmap failed: " + var14.getMessage());
			options = null;
			return null;
		}
	}

	public static Bitmap scaleBitmap(Bitmap source, int width, int height) {
		return ComThumbnailUtils.extractThumbnail(source, width, height);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		int height = options.outHeight;
		int width = options.outWidth;
		boolean inSampleSize = true;
		Log.d("BitmapUtils", "extractThumbNail: round=" + width + "x" + height);
		double beY = (double)options.outHeight * 1.0D / (double)height;
		double beX = (double)options.outWidth * 1.0D / (double)width;
		Log.d("BitmapUtils", "extractThumbNail: extract beX = " + beX + ", beY = " + beY);
		int var12 = (int)(beY < beX?beX:beY);
		if(var12 <= 1) {
			var12 = 1;
		}

		while(options.outHeight * options.outWidth / var12 > 2764800) {
			++var12;
		}

		return var12;
	}

	public static Bitmap getSmallBitmap(String filePath, int reqWidth, int reqHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, options);
	}

	public static Bitmap rotateBitmap(Bitmap bitmap, float rotateDegree) {
		Matrix matrix = new Matrix();
		matrix.postRotate(rotateDegree);
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
		bitmap.recycle();
		return newBitmap;
	}

	public static String bitmapToString(File file) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileInputStream is = null;
		byte[] buffer = new byte[4096];

		try {
			is = new FileInputStream(file);

			for(int size = is.read(buffer); size >= 0; size = is.read(buffer)) {
				baos.write(buffer, 0, size);
			}

			baos.flush();
			byte[] e = baos.toByteArray();
			baos.close();
			String var6 = Base64.encodeToString(e, 0);
			return var6;
		} catch (FileNotFoundException var12) {
			var12.printStackTrace();
		} catch (UnsupportedEncodingException var13) {
			var13.printStackTrace();
		} catch (IOException var14) {
			var14.printStackTrace();
		} finally {
			NetworkConnectUtils.closeInputStream(is);
		}

		return null;
	}

	public static boolean bitmapToFile(Bitmap bitmap, File toSave, int quality) {
		if(bitmap != null) {
				FileOutputStream out = null;

				boolean e;
				try {
					out = new FileOutputStream(toSave);
					e = bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
				} catch (FileNotFoundException var8) {
					var8.printStackTrace();
					return false;
				} finally {
					NetworkConnectUtils.closeOutStream(out);
				}

				return e;

		} else {
			return false;
		}
	}

	public static boolean bitmapToFile(Bitmap bitmap, File toSave, int quality, int reqWidth, int reqHeight) {
		if(bitmap != null) {
			Bitmap bp = ComThumbnailUtils.extractThumbnail(bitmap, reqWidth, reqHeight);
			return bitmapToFile(bp, toSave, quality);
		} else {
			return false;
		}
	}

	public static Bitmap getXBitmap(Bitmap mask, Bitmap dest) {
		Bitmap bitmap = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setFilterBitmap(true);
		paint.setAntiAlias(true);
		Rect dist = new Rect(0, 0, mask.getWidth(), mask.getHeight());
		canvas.drawBitmap(dest, (Rect)null, dist, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		canvas.drawBitmap(mask, 0.0F, 0.0F, paint);
		return bitmap;
	}

	public static Bitmap createRoundBitmap(Bitmap src, float roundPx) {
		Bitmap out = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(out);
		Rect rect = canvas.getClipBounds();
		RectF rectf = new RectF(rect);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(-1);
		canvas.drawRoundRect(rectf, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(src, 0.0F, 0.0F, paint);
		return out;
	}

	public static Bitmap[] getSuitedBitmaps(Context context, int[] resIds, int w, int h) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap[] bitmaps = new Bitmap[resIds.length];
		Resources res = context.getResources();
		int index = 0;
		int[] arr$ = resIds;
		int len$ = resIds.length;

		for(int i$ = 0; i$ < len$; ++i$) {
			int id = arr$[i$];
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeResource(res, id, options);
			int sampleSize = 1;
			if(options.outHeight > h || options.outWidth > w) {
				if(options.outHeight > options.outWidth) {
					sampleSize = options.outHeight / h;
				} else {
					sampleSize = options.outWidth / w;
				}
			}

			options.inJustDecodeBounds = false;
			options.inSampleSize = sampleSize;
			bitmaps[index] = BitmapFactory.decodeResource(res, id, options);
			++index;
		}

		return bitmaps;
	}

	public static Bitmap getScaledBitmap(Bitmap src) {
		int srcHeight = src.getHeight();
		int srcWidth = src.getWidth();
		float sx = 1.0F;
		if(mScreenWidth > 0) {
			sx = 1.0F * (float)mScreenWidth / (float)srcWidth;
		}

		Matrix matrix = new Matrix();
		matrix.postScale(sx, sx);
		return Bitmap.createBitmap(src, 0, 0, srcWidth, srcHeight, matrix, true);
	}

	public static Bitmap extractThumbNail(String path, int height, int width, boolean crop) {
		Assert.assertTrue(path != null && !path.equals("") && height > 0 && width > 0);
		BitmapFactory.Options options = new BitmapFactory.Options();

		try {
			options.inJustDecodeBounds = true;
			Bitmap e = BitmapFactory.decodeFile(path, options);
			if(e != null) {
				e.recycle();
				e = null;
			}

			Log.d("BitmapUtils", "extractThumbNail: round=" + width + "x" + height + ", crop=" + crop);
			double beY = (double)options.outHeight * 1.0D / (double)height;
			double beX = (double)options.outWidth * 1.0D / (double)width;
			Log.d("BitmapUtils", "extractThumbNail: extract beX = " + beX + ", beY = " + beY);
			options.inSampleSize = (int)(crop?(beY > beX?beX:beY):(beY < beX?beX:beY));
			if(options.inSampleSize <= 1) {
				options.inSampleSize = 1;
			}

			while(options.outHeight * options.outWidth / options.inSampleSize > 2764800) {
				++options.inSampleSize;
			}

			int newHeight = height;
			int newWidth = width;
			if(crop) {
				if(beY > beX) {
					newHeight = (int)((double)width * 1.0D * (double)options.outHeight / (double)options.outWidth);
				} else {
					newWidth = (int)((double)height * 1.0D * (double)options.outWidth / (double)options.outHeight);
				}
			} else if(beY < beX) {
				newHeight = (int)((double)width * 1.0D * (double)options.outHeight / (double)options.outWidth);
			} else {
				newWidth = (int)((double)height * 1.0D * (double)options.outWidth / (double)options.outHeight);
			}

			options.inJustDecodeBounds = false;
			Log.i("BitmapUtils", "bitmap required size=" + newWidth + "x" + newHeight + ", orig=" + options.outWidth + "x" + options.outHeight + ", sample=" + options.inSampleSize);
			Bitmap bm = BitmapFactory.decodeFile(path, options);
			if(bm == null) {
				Log.e("BitmapUtils", "bitmap decode failed");
				return null;
			} else {
				Log.i("BitmapUtils", "bitmap decoded size=" + bm.getWidth() + "x" + bm.getHeight());
				Bitmap scale = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
				if(scale != null) {
					bm.recycle();
					bm = scale;
				}

				if(crop) {
					Bitmap cropped = Bitmap.createBitmap(bm, bm.getWidth() - width >> 1, bm.getHeight() - height >> 1, width, height);
					if(cropped == null) {
						return bm;
					}

					bm.recycle();
					bm = cropped;
					Log.i("BitmapUtils", "bitmap croped size=" + cropped.getWidth() + "x" + cropped.getHeight());
				}

				return bm;
			}
		} catch (OutOfMemoryError var15) {
			Log.e("BitmapUtils", "decode bitmap failed: " + var15.getMessage());
			options = null;
			return null;
		}
	}

	public static Bitmap decodeResourceInSampleSize(Resources resources, int drawableId) {
		BitmapFactory.Options options = new BitmapFactory.Options();

		try {
			options.inJustDecodeBounds = true;
			Bitmap e = BitmapFactory.decodeResource(resources, drawableId, options);
			if(e != null) {
				e.recycle();
				e = null;
			}

			for(options.inSampleSize = 1; options.outHeight * options.outWidth / options.inSampleSize > 2764800; ++options.inSampleSize) {
				;
			}

			options.inJustDecodeBounds = false;
			Bitmap bm = BitmapFactory.decodeResource(resources, drawableId, options);
			if(bm == null) {
				Log.e("BitmapUtils", "bitmap decode failed");
				return null;
			} else {
				return bm;
			}
		} catch (OutOfMemoryError var5) {
			Log.e("BitmapUtils", "decode bitmap failed: " + var5.getMessage());
			options = null;
			return null;
		}
	}

	public static Bitmap decodeResourceInSampleSize(File file) {
		BitmapFactory.Options options = new BitmapFactory.Options();

		try {
			options.inJustDecodeBounds = true;
			Bitmap e = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
			if(e != null) {
				e.recycle();
				e = null;
			}

			for(options.inSampleSize = 1; options.outHeight * options.outWidth / options.inSampleSize > 2764800; ++options.inSampleSize) {
				;
			}

			options.inJustDecodeBounds = false;
			Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
			if(bm == null) {
				Log.e("BitmapUtils", "bitmap decode failed");
				return null;
			} else {
				return bm;
			}
		} catch (OutOfMemoryError var4) {
			Log.e("BitmapUtils", "decode bitmap failed: " + var4.getMessage());
			options = null;
			return null;
		}
	}

	public static void recycleImageViewSrcBitmap(ImageView imageView) {
		Drawable drawable = imageView.getDrawable();
		imageView.setImageDrawable((Drawable)null);
		if(drawable instanceof BitmapDrawable) {
			recycleBitmap(((BitmapDrawable)drawable).getBitmap());
		}

	}

	public static void recycleBitmap(Bitmap bitmap) {
		if(Build.VERSION.SDK_INT < 11 && bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
		}

	}

	public static int getBytesPerPixel(Bitmap.Config config) {
		return config == Bitmap.Config.ARGB_8888?4:(config == Bitmap.Config.RGB_565?2:(config == Bitmap.Config.ARGB_4444?2:(config == Bitmap.Config.ALPHA_8?1:1)));
	}

	@SuppressLint("NewApi")
	public static boolean canUseForInBitmap(Bitmap candidate, BitmapFactory.Options targetOptions) {
		if(Build.VERSION.SDK_INT >= 19) {
			int width = targetOptions.outWidth / targetOptions.inSampleSize;
			int height = targetOptions.outHeight / targetOptions.inSampleSize;
			int byteCount = width * height * getBytesPerPixel(candidate.getConfig());
			return byteCount <= candidate.getAllocationByteCount();
		} else {
			return candidate.getWidth() == targetOptions.outWidth && candidate.getHeight() == targetOptions.outHeight && targetOptions.inSampleSize == 1;
		}
	}
}