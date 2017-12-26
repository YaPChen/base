package com.jason.base.utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zhangcuicui on 2016/4/19.
 */
public class FilesUtils {private static final String TAG = "FilesUtils";
    public static long UNIT_M = 1048576L;
    public static long UNIT_K = 1024L;
    public static final String DIR_ACCOUNTS_ROOT = "accounts";
    public static final String DIR_ACCOUNTS_CACHE = "cache";
    public static final String DIR_ACCOUNTS_FILE = "files";
    public static final String DIR_CACHE = "cache";
    public static final String DIR_FILES = "files";
    public static final String SUFFIX_JSON = ".json";
    public static final String SUFFIX_XML = ".xml";

    public FilesUtils() {
    }

    public static void deleteFile(String tag, File file) {
        if(file == null) {
            DebugUtils.logDeleteFiles(tag, "deleteFile file is null");
        } else if(!file.exists()) {
            DebugUtils.logDeleteFiles(tag, "deleteFile " + file.getAbsolutePath() + " not exsit, just skip");
        } else {
            if(file.isDirectory()) {
                File[] deleted = file.listFiles();
                File[] deleted1 = deleted;
                int len$ = deleted.length;

                for(int i$ = 0; i$ < len$; ++i$) {
                    File subFile = deleted1[i$];
                    deleteFile(tag, subFile);
                }

                boolean var8 = file.delete();
                DebugUtils.logDeleteFiles(tag, "deleteFile " + file.getAbsolutePath() + ", deleted = " + var8);
            } else {
                boolean var7 = file.delete();
                DebugUtils.logDeleteFiles(tag, "deleteFile " + file.getAbsolutePath() + ", deleted = " + var7);
            }

        }
    }

    public static String computeLengthToString(long length) {
        StringBuilder sb = new StringBuilder();
        if(length < UNIT_K) {
            sb.append(length).append('B');
        } else {
            float len;
            if(length < UNIT_M) {
                len = 1.0F * (float)length / (float)UNIT_K;
                sb.append(Math.round(len)).append("KB");
            } else {
                len = 1.0F * (float)length / (float)UNIT_M;
                sb.append(Math.round(len)).append("MB");
            }
        }

        return sb.toString();
    }

    public static boolean saveFile(InputStream src, File out) {
        File dir = out.getParentFile();
        boolean success;
        if(!dir.exists()) {
            success = dir.mkdirs();
            DebugUtils.logD("FilesUtils", "saveFile mkdir " + dir.getAbsolutePath() + ", created=" + success);
        }

        success = true;
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(out);
            byte[] e = new byte[8192];
            boolean count = false;

            int count1;
            while((count1 = src.read(e)) > 0) {
                fos.write(e, 0, count1);
            }

            fos.flush();
        } catch (IOException var10) {
            var10.printStackTrace();
            success = false;
        } finally {
            NetworkConnectUtils.closeOutStream(fos);
        }

        DebugUtils.logD("FilesUtils", "saveFile to " + out.getAbsolutePath() + " success? " + success);
        return success;
    }

    public static boolean installDatabaseFiles(Context context, String fileName, String ext, String extReplace) {
        File file = context.getDatabasePath(fileName + extReplace);
        boolean success = true;
        if(file.exists() || file.isDirectory()) {
            boolean is = file.delete();
            if(is) {
                DebugUtils.logD("FilesUtils", "delete exsited  " + fileName);
            }
        }

        DebugUtils.logD("FilesUtils", "start to install DatabaseFiles " + fileName);
        file.getParentFile().mkdirs();
        InputStream is1 = null;
        FileOutputStream fos = null;

        try {
            is1 = context.getResources().getAssets().open(fileName + ext);
            fos = new FileOutputStream(file);
            byte[] e = new byte[8192];
            boolean count = false;

            int count1;
            while((count1 = is1.read(e)) > 0) {
                fos.write(e, 0, count1);
            }

            fos.flush();
        } catch (IOException var13) {
            var13.printStackTrace();
            success = false;
        } finally {
            NetworkConnectUtils.closeInputStream(is1);
            NetworkConnectUtils.closeOutStream(fos);
        }

        DebugUtils.logD("FilesUtils", "install " + fileName + " success? " + success);
        return success;
    }

    public static boolean installFiles(File src, File out) {
        boolean success = true;
        DebugUtils.logD("FilesUtils", "start to install File " + src.getAbsolutePath());
        FileInputStream is = null;
        FileOutputStream fos = null;

        try {
            is = new FileInputStream(src);
            fos = new FileOutputStream(out);
            byte[] e = new byte[8192];
            boolean count = false;

            int count1;
            while((count1 = is.read(e)) > 0) {
                fos.write(e, 0, count1);
            }

            fos.flush();
            NetworkConnectUtils.closeOutStream(fos);
            DebugUtils.logD("FilesUtils", "start to save File " + out.getAbsolutePath());
        } catch (IOException var10) {
            var10.printStackTrace();
            success = false;
        } finally {
            NetworkConnectUtils.closeInputStream(is);
        }

        DebugUtils.logD("FilesUtils", "install " + src.getAbsolutePath() + " success? " + success);
        return success;
    }




}
