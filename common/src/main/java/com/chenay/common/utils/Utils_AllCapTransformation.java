package com.chenay.common.utils;

import android.text.method.ReplacementTransformationMethod;

import java.io.ObjectStreamException;

/**
 * 自动将小写转换成大写
 * Created by Y.Chen5 on 3/22/2018.
 */

public class Utils_AllCapTransformation extends ReplacementTransformationMethod {

    /**
     *获取单例内部类句柄
     * @return
     *
     * Created by Chenay
     */
    private static class Utils_AllCapTransformationHolder {
            private static final Utils_AllCapTransformation SINGLETON = new Utils_AllCapTransformation();
        }
    /**
     *获取单例
     * @return
     *
     * Created by Chenay
     */
    public static Utils_AllCapTransformation newInstance() {
        return Utils_AllCapTransformationHolder.SINGLETON;
    }
    /**
     * 防止反序列化操作破坏单例模式 (条件) implements Serializable
     *@return
     * Created by Chenay
     */
    private Object readResolve() throws ObjectStreamException {
        return Utils_AllCapTransformationHolder.SINGLETON;
    }
    @Override
    protected char[] getOriginal() {
        char[] aa = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z' };
        return aa;
    }

    @Override
    protected char[] getReplacement() {
        char[] cc = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z' };
        return cc;
    }

}
