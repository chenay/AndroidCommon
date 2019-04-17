package com.chenay.common.desgin.listview;

public class PinnedListItemInfo<D> {
    private Boolean isHeader;
    private int headCode;

    private D data;

    public PinnedListItemInfo() {
        isHeader = false;
        headCode = 0;
    }

    public Boolean getHeader() {
        return isHeader;
    }

    public void setHeader(Boolean header) {
        isHeader = header;
    }

    public int getHeadCode() {
        return headCode;
    }

    public void setHeadCode(int headCode) {
        this.headCode = headCode;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }
}
