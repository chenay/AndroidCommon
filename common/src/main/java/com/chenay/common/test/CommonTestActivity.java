package com.chenay.common.test;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chenay.common.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

public class CommonTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_test);
    }


    public void onClick(View view) {
        EditText editText = (EditText) findViewById(R.id.text);
        final Editable text = editText.getText();
        if (!TextUtils.isEmpty(text)) {

            Bitmap bitmap = createQRCode(text.toString());
            ImageView img = (ImageView) findViewById(R.id.qr_img);
            img.setImageBitmap(bitmap);


            Bitmap bitmap1 = crateDimensionCode(text.toString());
            ImageView img1 = (ImageView) findViewById(R.id.yiWeiMa);
            img1.setImageBitmap(bitmap1);

        }

    }


    public Bitmap createQRCode(String text){
        int QR_WIDTH = 200;//生成二维码的宽
        int QR_HEIGHT = 200;//生成二维码的高

        try {
            // 需要引入core包
            QRCodeWriter writer = new QRCodeWriter();
            if (text == null || "".equals(text) || text.length() < 1) {
                return null;
            }
            // 把输入的文本转为二维码
            BitMatrix martix = writer.encode(text, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT);

            System.out.println("w:" + martix.getWidth() + "h:" + martix.getHeight());

            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            // 生成的二维码
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);

            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 条维码
     * @param text
     * @return
     */
    public Bitmap crateDimensionCode(String text) {
        int QR_WIDTH = 200;//生成二维码的宽
        int QR_HEIGHT = 50;//生成二维码的高

        try {
            // 需要引入core包
            Code128Writer writer = new Code128Writer();
            if (text == null || "".equals(text) || text.length() < 1) {
                return null;
            }
            // 把输入的文本转为二维码
            BitMatrix martix = writer.encode(text, BarcodeFormat.CODE_128, QR_WIDTH, QR_HEIGHT);

            System.out.println("w:" + martix.getWidth() + "h:" + martix.getHeight());

            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new Code128Writer().encode(text, BarcodeFormat.CODE_128, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            // 生成的二维码
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);

            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

}