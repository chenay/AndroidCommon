package com.chenay.common.updateApk;

import android.os.Handler;
import android.os.Message;


/**
 *
 * @author Chenay
 * @date 2016/5/20
 * class name:DownloadApkThread<BR>
 * class description:下载apk的线程<BR>
 */
public class DownloadApkThread implements Runnable {
    private  String down_file_path;
    private Handler handler;
    private String url;
    private String fileName;


    public DownloadApkThread(Handler handler) {
        this.handler = handler;
    }

    public DownloadApkThread(Handler handler, String newApkPath, String apkName,String downFilePath) {
        this.handler = handler;
        this.url = newApkPath;
        this.fileName = apkName;
        this.down_file_path = downFilePath;
    }

    @Override
    public void run() {
        System.out.println("下载线程开启");
        Message message = Message.obtain();
        message.what = HttpDownload.downLoadFile(url, fileName, down_file_path);
        handler.sendMessage(message);

    }
}
