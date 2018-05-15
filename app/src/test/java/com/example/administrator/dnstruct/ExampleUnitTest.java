package com.example.administrator.dnstruct;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    //    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void setTime() {
        long totleTime = 259203000L;
        long s = totleTime / 1000;
        long m = s / 60;
        long h = m / 60;
        System.out.println(String.valueOf(s));
        System.out.println(String.valueOf(m));
        System.out.println(String.valueOf(h));
        String second = String.valueOf(totleTime / 1000 % 60);
        String minite = String.valueOf((totleTime / 1000 / 60) % 60);
        String hour = String.valueOf(totleTime / 1000 / 3600);

        System.out.println(hour);
        System.out.println(minite);
        System.out.println(second);
    }

    @Test
    public void scaleImage() {
        Bitmap bitmap = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.heheh);
        bitmap = cross(bitmap);
        bitmap = Bitmap.createScaledBitmap(bitmap, 60, 60, true);
        while (bitmap.getByteCount() > 1024) {
            bitmap = cross(bitmap);
        }
        System.out.println("压缩后大小:" + bitmap.getByteCount());
    }

    public Bitmap cross(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream);
        byte[] bytes = stream.toByteArray();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    @Test
    public void compare1() {
        String str = "hello ! my name is tang yang hai ,and my book xx dd xx dd xx hello ! my name is tang yang hai ,and my book xx dd xx dd xx hello ! my name is tang yang hai ,and my book xx dd xx dd xx hello ! my name is tang yang hai ,and my book xx dd xx dd xx hello ! my name is tang yang hai ,and my book xx dd xx dd xx";
        long l = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            test(str);
        }
        System.out.println("lf运行总时间= " + (System.currentTimeMillis() - l));
    }

    @Test
    public void compare2() {
        String str = "hello ! my name is tang yang hai ,and my book xx dd xx dd xx hello ! my name is tang yang hai ,and my book xx dd xx dd xx hello ! my name is tang yang hai ,and my book xx dd xx dd xx hello ! my name is tang yang hai ,and my book xx dd xx dd xx hello ! my name is tang yang hai ,and my book xx dd xx dd xx";
        long l = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            getMostUsedWords(str);
        }
        System.out.println("tyh运行总时间= " + (System.currentTimeMillis() - l));
    }

    public void test(String str) {
        String[] words = str.toLowerCase().split("[ !?',;.$%^&*]+");
        HashMap<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        String res = null;
        for (String word : map.keySet()) {
            if (res == null || map.get(word) > map.get(res)) {
                res = word;
            }
        }
        mostUsedWord.word = res;
    }

    MostUsedWord mostUsedWord = new MostUsedWord();

    public void getMostUsedWords(String str) {
        mostUsedWord.count = 0;
        String[] split = str.toLowerCase().split("[ !?',;.$%^&*]+");
        List<String> all = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            String s1 = split[i];
            if (all.contains(s1)) {
                return;
            } else {
                all.add(s1);
                int count = 1;
                for (int j = i + 1; j < split.length; j++) {
                    if (equals(s1, split[j])) {
                        count++;
                    }
                }
                if (count > mostUsedWord.count) {
                    mostUsedWord.count = count;
                    mostUsedWord.word = s1;
                }
            }
        }
    }


    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    class MostUsedWord {
        int count;
        String word;

        void out() {
            if (word != null) {
                System.out.println("出现最多的单词是: " + word + ",共出现" + count + "次");
            }
        }
    }


}