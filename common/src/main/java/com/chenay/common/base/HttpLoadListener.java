package com.chenay.common.base;


/**
 * @author Y.Chen5
 */
public interface HttpLoadListener {

    /**
     * 开始加载
     *
     * @param action
     * @param msg
     */
    void loadStart(int action, String msg);

    /**
     * 加载成功
     *
     * @param action 请求行为
     * @param msg    提示信息
     */
    void loadSuccess(int action, String msg);

    /**
     * 加载失败
     *
     * @param action
     * @param msg
     */
    void loadFailure(int action, String msg);

    /**
     * 加载错误
     *
     * @param action
     * @param message
     */
    void loadError(int action, String message);

    /**
     * 加载完成
     *
     * @param action
     */
    void loadComplete(int action);
}
