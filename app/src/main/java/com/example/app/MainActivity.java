package com.example.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.android_terminal.androidTerminal.command.Command;
import com.android_terminal.androidTerminal.command.utils.CommandUtils;
import com.android_terminal.androidTerminal.terminal.Terminal;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final Command command = CommandUtils.dumpAppGfxInfo(getApplicationContext());
                    final String data = command.asyncStringRead();
                    MainActivity.this.runOnUiThread(() -> {
                        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}