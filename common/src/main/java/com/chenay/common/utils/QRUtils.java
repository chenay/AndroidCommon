package com.chenay.common.utils;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

public class QRUtils {
    public static String createQRCode(String text, String filePath, String fileName, int width, int height) {
        String path = null;
        File file = null;
        try {
            path = filePath + File.separator + fileName;
            file = new File(path);
            if (!file.exists()) {
                if (file.getParentFile().mkdirs()) {
                    if (file.createNewFile()) {

                    } else {
                        return "file create failed! " + path;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "file create failed! " + path + e.getMessage();
        }


        Bitmap bitmap = newQRCode(text, width, height);
        if (bitmap != null) {
            saveBitmap(bitmap, file);
        }
        return path;
    }

    public static String create128Code(String text, String filePath, String fileName, int width, int height) {
        String path = null;
        File file = null;
        try {
            path = filePath + File.separator + fileName;
            file = new File(path);
            if (!file.exists()) {
                if (file.getParentFile().mkdirs()) {
                    if (file.createNewFile()) {

                    } else {
                        return "file create failed! " + path;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "file create failed! " + path + e.getMessage();
        }


        Bitmap bitmap = new128Code(text, width, height);
        if (bitmap != null) {
            saveBitmap(bitmap, file);
        } else {
            return "bitmap create failed";
        }
        return path;
    }

    private static Bitmap new128Code(String text, int qrWidth, int qrHeight) {
        try {
            // 需要引入core包
            Code128Writer writer = new Code128Writer();
            if (text == null || "".equals(text) || text.length() < 1) {
                return null;
            }
            // 把输入的文本转为二维码
            BitMatrix martix = writer.encode(text, BarcodeFormat.CODE_128, qrWidth, qrHeight);

            System.out.println("w:" + martix.getWidth() + "h:" + martix.getHeight());

            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new Code128Writer().encode(text, BarcodeFormat.CODE_128, qrWidth, qrHeight, hints);
            int[] pixels = new int[qrWidth * qrHeight];
            for (int y = 0; y < qrHeight; y++) {
                for (int x = 0; x < qrWidth; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * qrWidth + x] = 0xff000000;
                    } else {
                        pixels[y * qrWidth + x] = 0xffffffff;
                    }
                }
            }
            // 生成的二维码
            Bitmap bitmap = Bitmap.createBitmap(qrWidth, qrHeight, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, qrWidth, 0, 0, qrWidth, qrHeight);

            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Bitmap newQRCode(String text, int qrWidth, int qrHeight) {

        try {
            // 需要引入core包
            QRCodeWriter writer = new QRCodeWriter();
            if (text == null || "".equals(text) || text.length() < 1) {
                return null;
            }
            // 把输入的文本转为二维码
            BitMatrix martix = writer.encode(text, BarcodeFormat.QR_CODE, qrWidth, qrHeight);

            System.out.println("w:" + martix.getWidth() + "h:" + martix.getHeight());

            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, qrWidth, qrHeight, hints);
            int[] pixels = new int[qrWidth * qrHeight];
            for (int y = 0; y < qrHeight; y++) {
                for (int x = 0; x < qrWidth; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * qrWidth + x] = 0xff000000;
                    } else {
                        pixels[y * qrWidth + x] = 0xffffffff;
                    }
                }
            }
            // 生成的二维码
            Bitmap bitmap = Bitmap.createBitmap(qrWidth, qrHeight, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, qrWidth, 0, 0, qrWidth, qrHeight);

            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存bitmap到本地
     *
     * @param bitmap
     * @return
     */
    private static String saveBitmap(Bitmap bitmap, File filePic) {

        try {
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "bitmap save failed" + e.getMessage();
        }
        return "bitmap save success! " + filePic.getAbsolutePath();
    }
}
