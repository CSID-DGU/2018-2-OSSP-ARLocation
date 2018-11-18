package ng.dat.ar;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ng.dat.ar.Utils.ReqeustHttpURLConnection;

public class InDoorPopupActivity extends Activity {
    private TextView floor;
    private Button btn;
    private TextView r1;
    private TextView r2;
    private TextView r3;
    private TextView r4;
    private TextView r;
    private String url_mac;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_indoor_popup);

        Intent intent =getIntent();
        String floorName = intent.getStringExtra("floorName");
        String macAddress = intent.getStringExtra("macAddress");
        //UI 객체생성
        floor = (TextView)findViewById(R.id.floorname);
        btn = (Button)findViewById(R.id.closebtn);
        r1= (TextView)findViewById(R.id.resulttext1);
        r2= (TextView)findViewById(R.id.resulttext2);
        r3= (TextView)findViewById(R.id.resulttext3);
        r4= (TextView)findViewById(R.id.resulttext4);
        r= (TextView)findViewById(R.id.result);

        floor.setText(floorName);

        String url_start="http://52.78.123.18/indoor.php?macAddr=%27";
        url_mac =macAddress;
        String url_end="%27";
        String url= url_start + url_mac + url_end;

        // AsyncTask를 통해 HttpURLConnection 수행.
        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();

        floor.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/NanumBarunpenB.ttf"));
        btn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/NanumBarunpenB.ttf"));
        r1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/NanumBarunpenB.ttf"));
        r2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/NanumBarunpenB.ttf"));
        r3.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/NanumBarunpenB.ttf"));
        r4.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/NanumBarunpenB.ttf"));
        r.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/NanumBarunpenB.ttf"));

        btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            ReqeustHttpURLConnection requestHttpURLConnection = new ReqeustHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            //tv_outPut.setText(s);

            try {

                JSONArray array = new JSONObject(s).getJSONArray("information");
                JSONObject jObject = array.getJSONObject(0);

                floor.setText(jObject.getString("indoor_floor"));
                r.setText(jObject.getString("indoor_content"));


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
