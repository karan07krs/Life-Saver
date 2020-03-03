package com.example.clinic;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.regex.Pattern;

public class searchfragment extends Fragment {
View v;
TextView nosearch;
EditText searchtext;
ImageView search;
String searchatgoogle="https://www.google.com/search?q=";
    WebView webView;
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v= inflater.inflate(R.layout.search_fragment,container,false);
        webView=v.findViewById(R.id.webview);
searchtext=(EditText) v.findViewById(R.id.searchtext);
search=(ImageView)v.findViewById(R.id.searchbutt);
nosearch=(TextView)v.findViewById(R.id.no_search_webview);
nosearch.setVisibility(View.VISIBLE);

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);


            }

            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onLoadResource(view, url);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                searchtext.setText(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!searchtext.getText().equals("")){
                    nosearch.setVisibility(View.INVISIBLE);
                    ;
                  String s=searchtext.getText().toString();
                    String finalsearch=searchatgoogle +s.replaceAll(" ","+");

                    webView.loadUrl(finalsearch);
                    searchtext.setText(webView.getUrl());

                }
            }
        });


        return v;
    }
    public String replaceAll(String regex, String replacement) {
        return Pattern.compile(regex).matcher((CharSequence) this).replaceAll(replacement);
    }

}
