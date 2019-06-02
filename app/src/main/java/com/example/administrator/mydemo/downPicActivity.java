package com.example.administrator.mydemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;

public class downPicActivity extends Activity implements View.OnClickListener {
    private Button btnDown;
    private ImageView ivShow;
    private String message;

    private final static String ALBUM_PATH
            = Environment.getExternalStorageDirectory() + "/download_test/";
//    private String url =
//            "https://img03.sogoucdn.com/app/a/100520093/97e46e4c262ab077-e08426d869a2e1cb-ca42dc3a62c006fa857f7458dd96891b.jpg";
//
   private String url="http://s4.sinaimg.cn/mw690/001ve3i3zy6SziUdgH143&690";
    private String fileName = "test.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);
        startDown();
    }

    public String getTime() {

        long time = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳

        String str = String.valueOf(time);

        return str;

    }

    private void startDown() {
        btnDown = findViewById(R.id.btn_down);
        btnDown.setOnClickListener(this);
        ivShow = findViewById(R.id.iv_show);
        Glide.with(this).load(url).into(ivShow);

    }

    private void downPIC() {
        //////////////// 取得的是byte数组, 从byte数组生成bitmap
        byte[] data = new byte[0];
        try {
            data = getImage(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data != null) {
//            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);// bitmap
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    ivShow.setImageBitmap(bitmap);// display image
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(downPicActivity.this, "Image error!", Toast.LENGTH_LONG).show();

                }
            });
        }

    }

    /**
     * Get image from newwork
     *
     * @param path The path of image
     * @return byte[]
     * @throws Exception
     */
    public byte[] getImage(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        InputStream inStream = conn.getInputStream();
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return readStream(inStream);
        }
        return null;
    }

    /**
     * Get data from stream
     *
     * @param inStream
     * @return byte[]
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }


    private Handler messageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(downPicActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };


    /**
     * 保存文件
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public void saveFile(Bitmap bm, String fileName) throws IOException {
        File dirFile = new File(ALBUM_PATH);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(ALBUM_PATH + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();


    }


    private Runnable saveFileRunnable = new Runnable() {
        @Override
        public void run() {
//           try {
                downPIC();
//               String [] name=url.split("/");
//                fileName = name[name.length-1]+".jpg";
//               saveFile(bitmap, fileName);
                saveImage();
                message = "图片保存成功！";
//            } catch (IOException e) {
//                message = "图片保存失败！";
//                e.printStackTrace();
//            }
            messageHandler.sendMessage(messageHandler.obtainMessage());
        }

    };
    private void saveImage() {
        //将Bitmap转换成二进制，写入本地
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = new byte[0];
        try {
            byteArray = getImage(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/com.example.administrator.mydemo");
        if (!dir.exists()) {
            dir.mkdir();
        }
        String [] name=url.split("/");
        File file = new File(dir, name[name.length-1] + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(byteArray, 0, byteArray.length);
            fos.flush();

            //用广播通知相册进行更新相册
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            downPicActivity.this.sendBroadcast(intent);
              } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_down:

                new Thread(saveFileRunnable).start();
                break;
        }
    }
}
