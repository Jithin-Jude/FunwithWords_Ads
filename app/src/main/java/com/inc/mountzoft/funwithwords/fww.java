package com.inc.mountzoft.funwithwords;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;


public class fww extends AppCompatActivity {

    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fww);

        try {
            AssetFileDescriptor afd = getAssets().openFd("root_menu_bgm.mp3");
            player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error loading file ! Please Choose from File Exploer", Toast.LENGTH_LONG).show();
        }
        player.start();

    }

    public void delayRun4() {
        Intent intent = new Intent(this, highScore.class);
        startActivity(intent);
    }

    public void highScoreButtonFunction(View view) {
        Button button = (Button)findViewById(R.id.high_score_btn);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.animator.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        bounceInterpolator interpolator = new bounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        button.startAnimation(myAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                delayRun4();
            }
        }, 100);
    }

    public void delayRun3() {
        Intent intent = new Intent(this, rules.class);
        int level=1;
        global_var.tot_pts = 0;
        intent.putExtra("level_pass",level);
        startActivity(intent);
    }

    public void playButtonFunction(View view) {
        Button button = (Button)findViewById(R.id.play_btn);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.animator.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        bounceInterpolator interpolator = new bounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        button.startAnimation(myAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                delayRun3();
            }
        }, 100);
    }

    public void delayRun2() {
        Intent intent = new Intent(this, help.class);
        startActivity(intent);
    }
    public void helpButtonFunction(View view) {
        Button button = (Button) findViewById(R.id.help_btn);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.animator.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        bounceInterpolator interpolator = new bounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        button.startAnimation(myAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                delayRun2();
            }
        }, 100);
    }

    public void delayRun1() {
        Intent intent = new Intent(this, credits.class);
        startActivity(intent);
    }

    public void creditsButtonFunction(View view) {
        Button button = (Button) findViewById(R.id.cretits_btn);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.animator.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        bounceInterpolator interpolator = new bounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        button.startAnimation(myAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                delayRun1();
            }
        }, 100);
        }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    }
