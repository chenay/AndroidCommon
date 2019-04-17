package com.chenay.common.updateApk;

import android.support.annotation.Keep;

import com.chenay.common.msg.BaseBean;

import java.util.List;


/**
 *
 * @author Y.Chen5
 * @date 12/28/2017
 */
@Keep
public class UpdateConfigBean extends BaseBean {


    /**
     * outputType : {"type":"APK"}
     * apkInfo : {"type":"MAIN","splits":[],"versionCode":3}
     * path : eFactory-debug-1.0.03.apk
     * properties : {"packageId":"com.tti.ychen5.efactory","split":"","minSdkVersion":"15"}
     */

    private OutputTypeBean outputType;
    private ApkInfoBean apkInfo;
    private String path;
    private PropertiesBean properties;

    public OutputTypeBean getOutputType() {
        return outputType;
    }

    public void setOutputType(OutputTypeBean outputType) {
        this.outputType = outputType;
    }

    public ApkInfoBean getApkInfo() {
        return apkInfo;
    }

    public void setApkInfo(ApkInfoBean apkInfo) {
        this.apkInfo = apkInfo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public PropertiesBean getProperties() {
        return properties;
    }

    public void setProperties(PropertiesBean properties) {
        this.properties = properties;
    }

    @Keep
    public static class OutputTypeBean {
        /**
         * type : APK
         */

        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    @Keep
    public static class ApkInfoBean {
        /**
         * type : MAIN
         * splits : []
         * versionCode : 3
         */

        private String type;
        private int versionCode;
        private String versionName;
        private List<?> splits;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public List<?> getSplits() {
            return splits;
        }

        public void setSplits(List<?> splits) {
            this.splits = splits;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }
    }

    @Keep
    public static class PropertiesBean {
        /**
         * packageId : com.tti.ychen5.efactory
         * split :
         * minSdkVersion : 15
         */

        private String packageId;
        private String split;
        private String minSdkVersion;

        public String getPackageId() {
            return packageId;
        }

        public void setPackageId(String packageId) {
            this.packageId = packageId;
        }

        public String getSplit() {
            return split;
        }

        public void setSplit(String split) {
            this.split = split;
        }

        public String getMinSdkVersion() {
            return minSdkVersion;
        }

        public void setMinSdkVersion(String minSdkVersion) {
            this.minSdkVersion = minSdkVersion;
        }
    }
}
