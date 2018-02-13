package com.inc.mountzoft.funwithwords;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Locale;

public class rewards_lose extends AppCompatActivity implements RewardedVideoAdListener {

    public TextView loseInfo;
    public TextToSpeech congrats;
    int level, numCorrectWrds, pts;

    private RewardedVideoAd mRewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_rewards_lose);
        global_var.loseBgm.start();


        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        loadRewardedVideoAd();


        Intent intent = getIntent();
        level = intent.getIntExtra("level_pass", 0);
        numCorrectWrds = intent.getIntExtra("numCorrectWrds", 0);
        pts = level * 5 * numCorrectWrds;
        global_var.tot_pts = global_var.tot_pts + pts;

        //  DATABASE STUFF BEGINS =======================================================================================================

        String file = "high_score.txt", textContent = "0", textData = "0";

        try{
            FileInputStream fis = openFileInput(file);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            textContent = new String(buffer);

        }catch (Exception e1) {
            e1.printStackTrace();

            try{
                FileOutputStream fos = openFileOutput(file, Context.MODE_PRIVATE);
                fos.write(textData.getBytes());
                fos.close();
            } catch (Exception e2){
                e2.printStackTrace();
                Toast.makeText(this, "Error occurred !", Toast.LENGTH_SHORT).show();
            }
        }

        if(Integer.valueOf(textContent) < global_var.tot_pts){

            String overwrite = String.valueOf(global_var.tot_pts);

            try{
                FileOutputStream fos = openFileOutput(file, Context.MODE_PRIVATE);
                fos.write(overwrite.getBytes());
                fos.close();
                Toast.makeText(this, "Sorry you lose. But you created a new record !\nBetter luck next time !", Toast.LENGTH_LONG).show();

                congrats =new TextToSpeech(rewards_lose.this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status == TextToSpeech.SUCCESS) {
                            congrats.setLanguage(Locale.ENGLISH);
                        }else {
                            Toast.makeText(rewards_lose.this, "language not supported !", Toast.LENGTH_SHORT).show();
                        }
                        congrats.speak("Sorry you lose. But you created a new record !\nBetter luck next time !", TextToSpeech.QUEUE_FLUSH, null);
                    }
                });

            } catch (Exception e2){
                e2.printStackTrace();
                Toast.makeText(this, "Error occurred !", Toast.LENGTH_SHORT).show();
            }
        }

        //  DATABASE STUFF OVER =========================================================================================================


        loseInfo = (TextView) this.findViewById(R.id.lose);
        loseInfo.setText("Sorry you lose !\nTry again.\n\nScore in this level : " + pts + " points\nTotal score : " + global_var.tot_pts + " points");
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-9873336567720966/1029458915",
                new AdRequest.Builder().build());
    }


    public void startVideoAd(View view){
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }


    @Override
    public void onRewarded(RewardItem reward) {
        Toast.makeText(this, "Now you can proceed to next level !", Toast.LENGTH_SHORT).show();
        // Reward the user.
        Intent intent = new Intent(this, rules.class);
        intent.putExtra("level_pass",level+1);
        startActivity(intent);
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Toast.makeText(this, "Rewarded Video !",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        Toast.makeText(this, "You closed Rewarded Video !", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        Toast.makeText(this, "Sorry, there is no Reward video available !", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Toast.makeText(this, "Reward video available !", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRewardedVideoAdOpened() {
        Toast.makeText(this, "Reward Video Opened !", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRewardedVideoStarted() {
        Toast.makeText(this, "Watch video completely to go for next level !", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }


    @Override
    protected void onStop() {
        super.onStop();

        if(congrats != null){
            congrats.shutdown();
        }
    }

    public void delayRun() {
        Intent intent = new Intent(this, fww.class);
        startActivity(intent);
    }

    public void exit_to_home_ButtonFunction(View view) {
        global_var.loseBgm.stop();
        global_var.btnBgm.start();
        Button button = (Button) findViewById(R.id.exit_to_home);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.animator.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        bounceInterpolator interpolator = new bounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        button.startAnimation(myAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                delayRun();
            }
        }, 100);
    }
    @Override
    public void onBackPressed() {}
}
