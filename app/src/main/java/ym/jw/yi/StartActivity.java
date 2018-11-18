package ym.jw.yi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class StartActivity extends BaseActivity {

    private ImageView img;
    private Button startbutton;
    private float alpha;
    private int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        img = (ImageView) findViewById(R.id.tempImage);
        startbutton = (Button)findViewById(R.id.button);
        startbutton.setEnabled(false);
        final Thread removeTemp;

        removeTemp = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized(this){
                    alpha = 1.2F;
                    while(alpha > 0) {
                        if(alpha < 0.7){
                            alpha-=0.025;
                        } else {
                            alpha-=0.0075;
                        }
                        try{
                            wait(25);
                        } catch(Exception e) {
                            alpha = 0;
                            e.printStackTrace();
                        }finally {
                            img.setAlpha(alpha);
                        }
                    }
                    notify();
                    try{
                        startbutton.setEnabled(true);
                    }
                    catch(Exception e){

                    }
                }
            }
        });


        Thread stop = new Thread(new Runnable() {
            @Override
            public void run() {
                removeTemp.start();

                synchronized (removeTemp){
                    try{
                        removeTemp.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    startbutton.setEnabled(true);

                }
            }
        });
        stop.start();

    }
    public void setInvisible(float alpha) {
        img.setAlpha(alpha);
    }

    public void click_start(View v){
        Intent intent = new Intent(getApplicationContext(),ARActivity.class);
        startActivity(intent);
    }
}
