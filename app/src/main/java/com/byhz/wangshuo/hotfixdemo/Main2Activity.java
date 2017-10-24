package com.byhz.wangshuo.hotfixdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        findViewById(R.id.main_close).setOnClickListener(this);
        img = (ImageView) findViewById(R.id.main_img);
        img.setBackgroundResource(R.drawable.img_man);
//        Glide.with(this).load(R.drawable.ic_launcher).into(img);
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                //.placeholder(R.mipmap.ic_launcher_round)
//                .error(android.R.drawable.stat_notify_error)
//                .priority(Priority.HIGH)
////                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
//        Glide.with(this).load(R.drawable.git_cat).apply(options).into(img);

//        Glide.with(this).load(R.drawable.git_cat).asGif()
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .centerCrop().into(img);

//        Glide.with(this).load(R.drawable.img_man).into(img);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_close:
                this.finish();
                break;
        }
    }
}
