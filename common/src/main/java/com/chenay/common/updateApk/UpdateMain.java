package com.chenay.common.updateApk;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import android.widget.Toast;

import com.chenay.common.thread.ThreadPoolUtil;
import com.chenay.common.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static com.chenay.common.updateApk.HttpDownload.DOWN_SUCESS;
import static com.chenay.common.updateApk.HttpDownload.DO_NOT_VERIFY;

import androidx.core.content.FileProvider;

/**
 * Created by Chenay on 2016/5/23.
 */

public class UpdateMain {
    private Handler handler;
    private String packageName;
    private ProgressDialog pBar;
    private String nowVersionName;
    private long nowVersionCode;

    private String newApkPath;
    private String apkName;
    private Context context;

    private String url;
    private static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String down_file_path = null;

    @SuppressLint("HandlerLeak")
    public UpdateMain(Context context1, String url, String newApkPath, String apkName1, String ApkRootPath) {
        this.context = context1;
        this.url = url;
        this.newApkPath = newApkPath;
        this.apkName = apkName1;
        this.down_file_path = SDCARD_PATH + File.separator + ApkRootPath;


        handler = new Handler(context1.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (pBar != null) {
                    pBar.cancel();
                    pBar.dismiss();
                }
                if (msg.what == DOWN_SUCESS) {
                    ToastUtil.showDefaultToast(context, "下载成功！！");
                    final File apkFile = new File(down_file_path, apkName);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    //判断是否是AndroidN以及更高的版本
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        Uri contentUri = FileProvider.getUriForFile(context, "com.tti.platform.fileProvider", apkFile);
                        intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                    } else {
                        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    context.startActivity(intent);
                } else {
                    ToastUtil.showDefaultToast(context, "下载失败！！");
                }
            }
        };
    }

    /**
     * 开始更新
     */
    public void startUpadata() {
        yesOrNoUpdataApk();
    }

    public void onDestory() {

    }

    /**
     * Role:是否更新版本<BR>
     */
    private int yesOrNoUpdataApk() {
        packageName = context.getPackageName();
        nowVersionCode = getVerCode(context);
        new GetVersion_MyAsyncTast().execute(url);
        return 0;
    }

    /**
     * Role:是否进行更新提示框<BR>
     */
    private void doNewVersionUpdate(String nowVersion, String newVersion,
                                    final String newApkPath, final String apkName) {
        if (newApkPath != null && !newApkPath.equals("")) {
            this.newApkPath = newApkPath;
        }
        if (apkName != null && !apkName.equals("")) {
            this.apkName = apkName;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("当前版本:    V");
        sb.append(nowVersion + "\n\n");
        sb.append("发现新版本:  V");
        sb.append(newVersion + "\n\n");
        sb.append("是否更新?");
        Dialog dialog = new AlertDialog.Builder(context)
                .setTitle("软件更新")
                .setMessage(sb.toString())
                // 设置内容
                .setPositiveButton("更新",// 设置确定按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                pBar = new ProgressDialog(context);
                                pBar.setTitle("正在下载");
                                pBar.setMessage("请稍候...");
                                // pBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                pBar.show();
                                goToDownloadApk();
                            }
                        })
                .setNegativeButton("暂不更新",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // 点击"取消"按钮之后退出程序

                            }
                        }).create();// 创建
        // 显示对话框
        dialog.show();
    }

    /**
     * Role:开启下载apk的线程<BR>
     */
    private void goToDownloadApk() {
        ThreadPoolUtil.execute_(new DownloadApkThread(handler, newApkPath, apkName, down_file_path));
    }


    /**
     * Role:取得程序的当前版本<BR>
     * Date:2012-4-5<BR>
     */
    private int getVerCode(Context context) {
        int verCode = 0;
        String verName = null;
        try {
            verCode = context.getPackageManager()
                    .getPackageInfo(packageName, 0).versionCode;
            verName = context.getPackageManager()
                    .getPackageInfo(packageName, 0).versionName;

            nowVersionName = verName;
        } catch (PackageManager.NameNotFoundException e) {
            System.out.println("no");
        }
        System.out.println("verCode" + verCode + "===" + "verName" + verName);
        return verCode;
    }

    /**
     * 获取版本
     */
    @SuppressLint("StaticFieldLeak")
    private class GetVersion_MyAsyncTast extends
            AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            // 根据地址创建URL对象
            String result = null;
            try {
                // 根据地址创建URL对象
                final String uri = params[0];
                URL url = new URL(uri);
                // 根据URL对象打开链接
                HttpURLConnection urlConnection = (HttpURLConnection) url
                        .openConnection();
                // 设置请求的方式
                urlConnection.setRequestMethod("POST");
                // 设置请求的超时时间
                urlConnection.setReadTimeout(5000);
                urlConnection.setConnectTimeout(5000);

                boolean useHttps = uri.startsWith("https");
                if (useHttps) {
                    HttpsURLConnection httpsConn = (HttpsURLConnection) urlConnection;
                    HttpDownload.trustAllHosts(httpsConn);
                    httpsConn.getHostnameVerifier();
                    httpsConn.setHostnameVerifier(DO_NOT_VERIFY);

                    // 传递的数据
                    String data = "version="
                            + URLEncoder.encode("newVewsion", "UTF-8");

                    httpsConn.setDoOutput(true); // 发送POST请求必须设置允许输出
                    httpsConn.setDoInput(true); // 发送POST请求必须设置允许输入
                    // setDoInput的默认值就是true
                    // 获取输出流
                    OutputStream os = httpsConn.getOutputStream();
                    os.write(data.getBytes());
                    os.flush();
                    if (httpsConn.getResponseCode() == 200) {
                        // 获取响应的输入流对象
                        InputStream is = httpsConn.getInputStream();
                        // 创建字节输出流对象
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        // 定义读取的长度
                        int len = 0;
                        // 定义缓冲区
                        byte[] buffer = new byte[1024];
                        // 按照缓冲区的大小，循环读取
                        while ((len = is.read(buffer)) != -1) {
                            // 根据读取的长度写入到os对象中
                            baos.write(buffer, 0, len);
                        }
                        // 释放资源
                        is.close();
                        baos.close();
                        httpsConn.disconnect();
                        // 返回字符串
                        // {"access_token":"31137b7e014cdbac3d3065c7b8b68f5e905ee52c","expires_in":7200,"token_type":"Bearer","scope":"all_json"}
                        result = new String(baos.toByteArray()).trim();
                        System.out.println(result);
                    } else {
                        System.out.println("链接失败.........");
                        return null;
                    }
                } else {

                    // 传递的数据
                    String data = "version="
                            + URLEncoder.encode("newVewsion", "UTF-8");

                    urlConnection.setDoOutput(true); // 发送POST请求必须设置允许输出
                    urlConnection.setDoInput(true); // 发送POST请求必须设置允许输入
                    // setDoInput的默认值就是true
                    // 获取输出流
                    OutputStream os = urlConnection.getOutputStream();
                    os.write(data.getBytes());
                    os.flush();
                    if (urlConnection.getResponseCode() == 200) {
                        // 获取响应的输入流对象
                        InputStream is = urlConnection.getInputStream();
                        // 创建字节输出流对象
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        // 定义读取的长度
                        int len = 0;
                        // 定义缓冲区
                        byte[] buffer = new byte[1024];
                        // 按照缓冲区的大小，循环读取
                        while ((len = is.read(buffer)) != -1) {
                            // 根据读取的长度写入到os对象中
                            baos.write(buffer, 0, len);
                        }
                        // 释放资源
                        is.close();
                        baos.close();
                        urlConnection.disconnect();
                        // 返回字符串
                        // {"access_token":"31137b7e014cdbac3d3065c7b8b68f5e905ee52c","expires_in":7200,"token_type":"Bearer","scope":"all_json"}
                        result = new String(baos.toByteArray()).trim();
                        System.out.println(result);
                    } else {
                        System.out.println("链接失败.........");
                        return null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == null) {
//                myToastPlay("软件更新服务器连接失败");
            } else {
                List<UpdateConfigBean> beanList;
                Type listType = new TypeToken<List<UpdateConfigBean>>() {
                }.getType();
                beanList = new Gson().fromJson(result, listType);
                if (beanList == null || (beanList.size() == 0)) {
                    String stringBuffer = "当前版本为:" +
                            nowVersionName + "," +
                            "已是最新版本";
                    myToastPlay(stringBuffer);
                    return;
                }

                for (int i = 0; i < beanList.size(); i++) {
                    UpdateConfigBean updateConfigBean = beanList.get(i);
                    UpdateConfigBean.ApkInfoBean apkInfo = updateConfigBean.getApkInfo();
                    String apkName = updateConfigBean.getPath();

                    if (apkInfo != null && apkName != null) {
                        long newVersionCode = apkInfo.getVersionCode();
                        String newApkPath = null;
                        String newVersion = apkName.substring(apkName.lastIndexOf("-") + 1, apkName.lastIndexOf("."));
                        if (newVersionCode > nowVersionCode) {
                            doNewVersionUpdate(nowVersionName, newVersion,
                                    newApkPath, apkName);
                            break;
                        } else {
                            String stringBuffer = "当前版本为:" +
                                    nowVersionName + "," +
                                    "已是最新版本";
                            myToastPlay(stringBuffer);
                        }
                    }
                    break;
                }

            }
        }

    }

    void myToastPlay(String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

}
