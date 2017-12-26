package com.jason.base.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

/**
 * Created by Administrator on 2016/4/29.
 */
public class PinyinUtils {

    public static String hanzi2pinyin(String hanzi) {
        try{
            HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
            format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            char[] charArray = hanzi.toCharArray();
            String str = "";
            for(int x=0;x<charArray.length;x++) {
                String[] hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(charArray[x],format);
                if(hanyuPinyinStringArray.length > 0) {
                    str += hanyuPinyinStringArray[0];
                }
            }

            return str;

        } catch(Exception e) {

        }

        return null;
    }
}
