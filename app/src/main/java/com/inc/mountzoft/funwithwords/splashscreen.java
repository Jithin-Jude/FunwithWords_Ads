package com.inc.mountzoft.funwithwords;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;


public class splashscreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        LinearLayout ll = (LinearLayout) findViewById(R.id.splashLayout);
        ll.setBackgroundResource(R.drawable.splash);
        
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(200);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(splashscreen.this,fww.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
    @Override
    public void onBackPressed() {}

}