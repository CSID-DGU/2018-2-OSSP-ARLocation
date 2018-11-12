package ng.dat.ar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Button btn = (Button)findViewById(R.id.list1);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(0xED, 0x7D, 0x31)));

        btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),PopupActivity.class);
                startActivity(intent);
            }
        });
    }
}
