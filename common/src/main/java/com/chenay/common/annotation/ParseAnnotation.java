package com.chenay.common.annotation;

import java.util.List;

public class ParseAnnotation {

    /**
     * 供应商代码 : 559
     * 生产日期 : 20180326
     * 数量 : 200
     * info : [{"jobNo":"32414329","itemNo":"4354325"},{"jobNo":"2314214","itemNo":"435794535"}]
     */

    private String 供应商代码;
    private String 生产日期;
    private String 数量;
    private List<InfoBean> info;

    public String get供应商代码() {
        return 供应商代码;
    }

    public void set供应商代码(String 供应商代码) {
        this.供应商代码 = 供应商代码;
    }

    public String get生产日期() {
        return 生产日期;
    }

    public void set生产日期(String 生产日期) {
        this.生产日期 = 生产日期;
    }

    public String get数量() {
        return 数量;
    }

    public void set数量(String 数量) {
        this.数量 = 数量;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * jobNo : 32414329
         * itemNo : 4354325
         */

        private String jobNo;
        private String itemNo;

        public String getJobNo() {
            return jobNo;
        }

        public void setJobNo(String jobNo) {
            this.jobNo = jobNo;
        }

        public String getItemNo() {
            return itemNo;
        }

        public void setItemNo(String itemNo) {
            this.itemNo = itemNo;
        }
    }
}
