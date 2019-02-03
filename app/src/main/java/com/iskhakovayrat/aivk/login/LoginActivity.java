package com.iskhakovayrat.aivk.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.iskhakovayrat.aivk.R;
import com.iskhakovayrat.aivk.TokenHolder;
import com.iskhakovayrat.aivk.main.MainActivity;


public class LoginActivity extends AppCompatActivity implements LoginView {

    private WebView uiLogin;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uiLogin = findViewById(R.id.loginContainer);

        presenter = new LoginPresenter(new TokenHolder(this));
        presenter.attach(this);

        initWebView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    private void initWebView() {
        uiLogin.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (presenter.isLoggedIn(url)) return false;

                view.loadUrl(url);
                return false;
            }
        });
    }

    @Override
    public void loadLoginUrl(String loginUrl) {
        uiLogin.loadUrl(loginUrl);
    }

    @Override
    public void showNewsFeed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
