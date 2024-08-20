//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.chenay.common.http;

import androidx.annotation.Nullable;

import java.util.concurrent.TimeUnit;

public class CacheControl {
    private long maxAge = -1L;
    private boolean noCache = false;
    private boolean noStore = false;
    private boolean mustRevalidate = false;
    private boolean noTransform = false;
    private boolean cachePublic = false;
    private boolean cachePrivate = false;
    private boolean proxyRevalidate = false;
    private long staleWhileRevalidate = -1L;
    private long staleIfError = -1L;
    private long sMaxAge = -1L;

    protected CacheControl() {
    }

    public static CacheControl empty() {
        return new CacheControl();
    }

    public static CacheControl maxAge(long maxAge, TimeUnit unit) {
        CacheControl cc = new CacheControl();
        cc.maxAge = unit.toSeconds(maxAge);
        return cc;
    }

    public static CacheControl noCache() {
        CacheControl cc = new CacheControl();
        cc.noCache = true;
        return cc;
    }

    public static CacheControl noStore() {
        CacheControl cc = new CacheControl();
        cc.noStore = true;
        return cc;
    }

    public CacheControl mustRevalidate() {
        this.mustRevalidate = true;
        return this;
    }

    public CacheControl noTransform() {
        this.noTransform = true;
        return this;
    }

    public CacheControl cachePublic() {
        this.cachePublic = true;
        return this;
    }

    public CacheControl cachePrivate() {
        this.cachePrivate = true;
        return this;
    }

    public CacheControl proxyRevalidate() {
        this.proxyRevalidate = true;
        return this;
    }

    public CacheControl sMaxAge(long sMaxAge, TimeUnit unit) {
        this.sMaxAge = unit.toSeconds(sMaxAge);
        return this;
    }

    public CacheControl staleWhileRevalidate(long staleWhileRevalidate, TimeUnit unit) {
        this.staleWhileRevalidate = unit.toSeconds(staleWhileRevalidate);
        return this;
    }

    public CacheControl staleIfError(long staleIfError, TimeUnit unit) {
        this.staleIfError = unit.toSeconds(staleIfError);
        return this;
    }

    @Nullable
    public String getHeaderValue() {
        StringBuilder ccValue = new StringBuilder();
        if (this.maxAge != -1L) {
            this.appendDirective(ccValue, "max-age=" + Long.toString(this.maxAge));
        }

        if (this.noCache) {
            this.appendDirective(ccValue, "no-cache");
        }

        if (this.noStore) {
            this.appendDirective(ccValue, "no-store");
        }

        if (this.mustRevalidate) {
            this.appendDirective(ccValue, "must-revalidate");
        }

        if (this.noTransform) {
            this.appendDirective(ccValue, "no-transform");
        }

        if (this.cachePublic) {
            this.appendDirective(ccValue, "public");
        }

        if (this.cachePrivate) {
            this.appendDirective(ccValue, "private");
        }

        if (this.proxyRevalidate) {
            this.appendDirective(ccValue, "proxy-revalidate");
        }

        if (this.sMaxAge != -1L) {
            this.appendDirective(ccValue, "s-maxage=" + Long.toString(this.sMaxAge));
        }

        if (this.staleIfError != -1L) {
            this.appendDirective(ccValue, "stale-if-error=" + Long.toString(this.staleIfError));
        }

        if (this.staleWhileRevalidate != -1L) {
            this.appendDirective(ccValue, "stale-while-revalidate=" + Long.toString(this.staleWhileRevalidate));
        }

        String ccHeaderValue = ccValue.toString();
        return hasText(ccHeaderValue) ? ccHeaderValue : null;
    }

    private void appendDirective(StringBuilder builder, String value) {
        if (builder.length() > 0) {
            builder.append(", ");
        }

        builder.append(value);
    }

    public boolean hasText(@Nullable String str) {
        return str != null && !str.isEmpty() && containsText(str);
    }

    private boolean containsText(CharSequence str) {
        int strLen = str.length();

        for (int i = 0; i < strLen; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }

        return false;
    }
}
