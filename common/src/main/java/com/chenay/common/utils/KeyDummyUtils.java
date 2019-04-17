package com.chenay.common.utils;

import android.app.Instrumentation;

import com.chenay.common.thread.ThreadPoolUtil;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * @author Y.Chen5
 */
public class KeyDummyUtils {

    private static OutputStream outputStream;
    private static Runtime runtime = Runtime.getRuntime();
    private static Instrumentation inst;


    /**
     * 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限)
     *
     * @return 应用程序是/否获取Root权限
     */
    public static boolean upgradeRootPermission(String pkgCodePath) {
        Process process = null;
        DataOutputStream os = null;
        try {
            if (isRoot()) {
                String cmd = "chmod 777 " + pkgCodePath;
                process = Runtime.getRuntime().exec("su"); // 切换到root帐号
                outputStream = process.getOutputStream();
                os = new DataOutputStream(outputStream);
                os.writeBytes(cmd + "\n");
                os.writeBytes("exit\n");
                os.flush();
                process.waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
        return true;
    }

    // 判断是否具有ROOT权限
    public static boolean isRoot() {
        boolean res = false;
        try {
            if ((!new File("/system/bin/su").exists()) &&
                    (!new File("/system/xbin/su").exists())) {
                res = false;
            } else {
                res = true;
            }
        } catch (Exception e) {

        }
        return res;
    }

    /**
     * 执行shell指令
     *
     * @param cmd 指令
     */
    private static void exec(String cmd) {
        try {
            if (outputStream == null) {
//                Process process = new ProcessBuilder()
//                        .command("/system/xbin/su")
//                        .redirectErrorStream(true).start();
                final Process process = Runtime.getRuntime().exec("");
                outputStream = process.getOutputStream();
            }
            outputStream.write(cmd.getBytes());
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 输入文字
     *
     * @param str
     */
    public static void execInputText(String str) {
        final String cmd = "input text " + str;
        exec(cmd);
    }

    /**
     * 输入按钮
     *
     * @param keyCode
     */
    public static void execInputKey(int keyCode) {
        final String cmd = "input keyevent " + keyCode;
        exec(cmd);
    }


    /**
     * 执行shell 命令， 命令中不必再带 adb shell
     *
     * @param cmd input keyevent 66||KeyEvent.KEYCODE_VOLUME_UP, input text abc123
     * @return Sting  命令执行在控制台输出的结果
     */
    public static String execByRuntime(String cmd) {
        Process process = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            inputStreamReader = new InputStreamReader(process.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);

            int read;
            char[] buffer = new char[4096];
            StringBuilder output = new StringBuilder();
            while ((read = bufferedReader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != inputStreamReader) {
                try {
                    inputStreamReader.close();
                } catch (Throwable t) {
                    //
                }
            }
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (Throwable t) {
                    //
                }
            }
            if (null != process) {
                try {
                    process.destroy();
                } catch (Throwable t) {
                    //
                }
            }
        }
    }

    /**
     * <pre>
     * 使用Instrumentation接口：对于非自行编译的安卓系统，无法获取系统签名，只能在前台模拟按键，不能后台模拟
     * 注意:调用Instrumentation的sendKeyDownUpSync方法必须另起一个线程，否则无效
     * @param keyCode
     *            按键事件(KeyEvent)的按键值
     * </pre>
     */

    public static void sendString(final String str, final int keyCode) {
        Runnable r = () -> {

            try {
                // 创建一个Instrumentation对象
                if (inst == null) {
                    inst = new MInstrumentation();
                }
                // 调用inst对象的按键模拟方法
                inst.sendStringSync(str);
                if (keyCode > 0) {
                    inst.sendKeyDownUpSync(keyCode);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        ThreadPoolUtil.execute_(r);
    }

}
