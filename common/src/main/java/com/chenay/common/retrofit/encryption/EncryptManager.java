package com.chenay.common.retrofit.encryption;

import android.util.Base64;

import java.io.ObjectStreamException;
import java.io.UnsupportedEncodingException;

public class EncryptManager {
    private static final String TAG = "EncryptManager";
    /**
     * 是否加密
     */
    private static final boolean isEncrypt = true;
    private static final String CHARSET_NAME = "utf-8";

    /**
     * 获取单例内部类句柄
     *
     * @return Created by Chenay
     */
    private static class EncryptManagerHolder {
        private static final EncryptManager SINGLETON = new EncryptManager();
    }

    /**
     * 获取单例
     *
     * @return Created by Chenay
     */
    public static EncryptManager newInstance() {
        return EncryptManagerHolder.SINGLETON;
    }

    /**
     * 防止反序列化操作破坏单例模式 (条件) implements Serializable
     *
     * @return Created by Chenay
     */
    private Object readResolve() throws ObjectStreamException {
        return EncryptManagerHolder.SINGLETON;
    }


    /**
     * 加密
     *
     * @param msg
     * @return
     */
    public String encrypt(String msg) {
        if (!isEncrypt) {
            return msg;
        }
//        Log.d(TAG, "convert: 加密前:" + msg);

        // byte[] temp1 = CodeUtils.xorEncode(str.getBytes(Charset.defaultCharset()));
        // msg = new String(temp1);

        // temp1 = CodeUtils.xorDecode(str.getBytes(Charset.defaultCharset()));
        // msg = new String(temp1);

        // final RequestBody requestBody = RequestBody.create(MEDIA_TYPE, content);

        // byte[] temp1 = EncryptUtil.encryptDES(str.getBytes(Charset.defaultCharset()),
        // EncryptUtil.KEY_BYTES);
        try {
            byte[] temp1;
            temp1 = EncryptUtil.encryptAES(msg.getBytes(CHARSET_NAME),
                    EncryptUtil.AES_KEY.getBytes(CHARSET_NAME));
            msg = Base64.encodeToString(temp1, Base64.DEFAULT);
//		Log.d(TAG,"convert: 加密后:" + msg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return msg;
    }

    /**
     * 解密
     *
     * @param msg
     * @return
     */
    public String decrypt(String msg) {
        if (!isEncrypt) {
            return msg;
        }
        try {
            byte[] temp1 = decryptByte(msg);
            msg = new String(temp1, CHARSET_NAME);
//            Log.d(TAG, "convert: 解密后:" + msg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 解密
     *
     * @param msg
     * @return
     */
    private byte[] decryptByte(String msg) throws UnsupportedEncodingException {
//		Log.d(TAG,"convert: 解密前:" + msg);
        final byte[] bytes = Base64.decode(msg, Base64.DEFAULT);
        // byte[] temp1 = EncryptUtil.decryptDES(bytes,EncryptUtil.KEY_BYTES);

        byte[] temp1 = EncryptUtil.decryptAES(bytes,
                EncryptUtil.AES_KEY.getBytes(CHARSET_NAME));
        return temp1;

    }
}
