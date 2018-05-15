package com.example.bitmapoptmize;

import android.graphics.Bitmap;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/4/1 0001.
 */

public class BitmapUtils {
    /**
     * 图片质量压缩保存
     *
     * @param bitmap    要保存的图片
     * @param format    图片格式
     * @param quality   质量(0--100)
     * @param path      保存位置
     */
    public static void saveToFile(Bitmap bitmap, Bitmap.CompressFormat format,int quality,String path){
        FileOutputStream fos= null;

        try {
            fos = new FileOutputStream(path);
            bitmap.compress(format,quality,fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (null!=fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }







}
