package com.iskhakovayrat.aivk.ui.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.iskhakovayrat.aivk.App;
import com.iskhakovayrat.aivk.R;
import com.iskhakovayrat.aivk.presentation.login.LoginPresenter;
import com.iskhakovayrat.aivk.presentation.login.LoginView;
import com.iskhakovayrat.aivk.ui.newsfeed.NewsfeedActivity;


public class LoginActivity extends AppCompatActivity implements LoginView {

    private LoginPresenter presenter;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = App.openLoginComponent().loginPresenter();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        webView = findViewById(R.id.loginContainer);
        initWebView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attach(this);
    }

    @Override
    protected void onStop() {
        presenter.detach();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isChangingConfigurations()) App.closeLoginComponent();
    }

    private void initWebView() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                presenter.onLogin(url);
                return false;
            }
        });
    }

    @Override
    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

    @Override
    public void goToNewsfeed() {
        startActivity(new Intent(this, NewsfeedActivity.class));
        finish();
    }


}
