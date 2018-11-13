package ng.dat.ar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        LinearLayout linear1 = (LinearLayout) findViewById(R.id.linearLayout1);
        Intent intent =getIntent();
        String[] buildingNameList = intent.getStringArrayExtra("buildingNameList");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(0xED, 0x7D, 0x31)));

        Button btn[] = new Button[buildingNameList.length];
        for(int i=0; i<buildingNameList.length; i++){
            if(buildingNameList[i]==null) break;

            btn[i]= new Button(this);
            btn[i].setText(buildingNameList[i]);
            btn[i].setId(i);
            linear1.addView(btn[i]);
            btn[i].setOnClickListener(new Button.OnClickListener(){
                public void onClick(View v){
                    Intent intent = new Intent(getApplicationContext(),PopupActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
