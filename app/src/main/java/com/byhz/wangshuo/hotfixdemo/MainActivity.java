package com.byhz.wangshuo.hotfixdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.taobao.sophix.SophixManager;

public class MainActivity extends AppCompatActivity {
    private String str = "";
    private TextView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Util.verifyStoragePermissions(this);
        log = (TextView) findViewById(R.id.main_log);
        findViewById(R.id.main_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Main2Activity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.main_pill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SophixManager.getInstance().queryAndLoadNewPatch();
            }
        });
        updateConsole(MyApplication.cacheMsg.toString());
        MyApplication.msgDisplayListener = new MyApplication.MsgDisplayListener() {
            @Override
            public void handle(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateConsole(msg);
                    }
                });
            }
        };
    }

    private void updateConsole(String content) {
        str += content + "\n";
        if (log != null) {
            log.setText(str);
        }
    }
}
