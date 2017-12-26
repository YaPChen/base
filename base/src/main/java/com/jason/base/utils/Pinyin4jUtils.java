package com.jason.base.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by yaping on 2016/1/12.
 */
public class Pinyin4jUtils {
    private static final String TAG = "TAG";

    public static String converterToFirstSpell(String chines) {
        if(chines == null) {
            DebugUtils.logD(TAG, "string value = null, return!");
            return "";
        }

        StringBuilder sb = new StringBuilder();

        try {
            char[] nameChar = chines.toCharArray();
            HanyuPinyinOutputFormat hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
            hanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            hanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

            for (int i = 0; i < nameChar.length; i++) {
                if (nameChar[i] == ' ') {
                    continue;
                }
                if (nameChar[i] > 128) {
                    try {
                        sb.append(PinyinHelper.toHanyuPinyinStringArray(nameChar[i], hanyuPinyinOutputFormat)[0].charAt(0));
                    } catch (BadHanyuPinyinOutputFormatCombination e) {
                        DebugUtils.logD(TAG, e + "");
                    }
                } else {
                    sb.append(nameChar[i]);
                }
            }
        } catch (Exception e) {
            DebugUtils.logD(TAG, e + "");
        }
        return sb.toString();
    }
    public static String convertToPinyin(String chines) {
        if(chines == null) {
            DebugUtils.logD(TAG, "string value = null, return!");
            return "";
        }

        StringBuilder sb = new StringBuilder();

        try {
            char[] nameChar = chines.toCharArray();
            HanyuPinyinOutputFormat hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
            hanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            hanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

            for (int i = 0; i < nameChar.length; i++) {
                if (nameChar[i] == ' ') {
                    continue;
                }
                if (nameChar[i] > 128) {
                    try {
                        String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], hanyuPinyinOutputFormat);
                        sb.append(pinyinArray[0]);
                    } catch (BadHanyuPinyinOutputFormatCombination e) {
                        DebugUtils.logD(TAG, e + "");
                    }
                } else {
                    sb.append(nameChar[i]);
                }
            }
        } catch (Exception e) {
            DebugUtils.logD(TAG, e + "");
        }
        return sb.toString();
    }
}
