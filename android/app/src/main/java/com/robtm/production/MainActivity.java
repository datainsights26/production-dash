package com.robtm.production;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/** Opens the password-locked production dashboard full screen. Holds no figures itself. */
public class MainActivity extends Activity {
    private static final String HOME = "https://datainsights26.github.io/production-dash/";
    private static final String HOST = "datainsights26.github.io";
    private WebView web;

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        Window w = getWindow();
        w.setStatusBarColor(Color.parseColor("#0a0a0b"));
        w.setNavigationBarColor(Color.parseColor("#0a0a0b"));
        web = new WebView(this);
        web.setBackgroundColor(Color.parseColor("#0a0a0b"));
        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);
        s.setLoadWithOverviewMode(true);
        s.setUseWideViewPort(true);
        s.setBuiltInZoomControls(true);
        s.setDisplayZoomControls(false);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest req) {
                Uri u = req.getUrl();
                if (HOST.equals(u.getHost())) return false;          // stay inside the dashboard
                startActivity(new Intent(Intent.ACTION_VIEW, u));    // anything else opens in the browser
                return true;
            }
        });
        setContentView(web);
        if (saved != null) web.restoreState(saved); else web.loadUrl(HOME);
    }

    @Override
    protected void onSaveInstanceState(Bundle out) { super.onSaveInstanceState(out); web.saveState(out); }

    @Override
    public void onBackPressed() { if (web.canGoBack()) web.goBack(); else super.onBackPressed(); }
}
