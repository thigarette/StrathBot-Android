package com.thiga.strathbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.thiga.strathbot.ui.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    private Context context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image_test);
        loadImage();
    }

    private void loadImage(){
        String internetUrl = "http://giphygifs.s3.amazonaws.com/media/eaeE9qEHKUZX2/giphy.gif";
        int resourceID = R.drawable.login_pic;
        Glide
                .with(context)
                .load(resourceID)
                .into(imageView);
    }

    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}
