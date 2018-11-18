package ng.dat.ar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class PPopupActivity extends Activity {
    private TextView floor;
    private Button btn;
    private TextView r1;
    private TextView r2;
    private TextView r;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);

        Intent intent =getIntent();
        String floorName = intent.getStringExtra("floorName");

        //UI 객체생성
        floor = (TextView)findViewById(R.id.buildingname);
        btn = (Button)findViewById(R.id.closebtn);
        r1= (TextView)findViewById(R.id.resulttext1);
        r2= (TextView)findViewById(R.id.resulttext2);
        r= (TextView)findViewById(R.id.result);

        floor.setText(floorName);

        btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

    }

}
