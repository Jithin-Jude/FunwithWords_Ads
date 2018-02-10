package com.inc.mountzoft.funwithwords;

import android.content.Intent;
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

public class rules extends AppCompatActivity {
    public TextView text;
    int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_rules);

        Intent intent = getIntent();
        level = intent.getIntExtra("level_pass",0);

        text = (TextView) this.findViewById(R.id.rulesDisplay);
        text.setText("Level : " + String.valueOf(level)+"\nNumber of Words : "+String.valueOf(level*5)+"\nPoints for Each Word : "+String.valueOf(level*5)+"\nDuration : "+String.valueOf(level*10)+" sec"+"\n\nTo clear this level you need to\ncorrect atleast "+String.valueOf((level*5)/2)+" words");
    }
    public void delayRun() {
        Intent intent = new Intent(this, wordlist.class);
        intent.putExtra("level_pass", level);
        startActivity(intent);
    }
    public void goAheadButtonFunction(View view){
        global_var.btnBgm.start();
        Button button = (Button) findViewById(R.id.goAheadBtn);
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
}
