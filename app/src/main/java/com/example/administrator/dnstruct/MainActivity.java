package com.example.administrator.dnstruct;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.heheh);
        System.out.println("压缩前大小:"+bitmap.getByteCount());
        bitmap = cross(bitmap);
        bitmap = Bitmap.createScaledBitmap(bitmap, 60, 60, true);
//        while (bitmap.getByteCount()>1024){
//            bitmap = cross(bitmap);
//        }
        System.out.println("压缩后大小:"+bitmap.getByteCount());
    }
    public Bitmap cross(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,20,stream);
        byte[] bytes = stream.toByteArray();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
