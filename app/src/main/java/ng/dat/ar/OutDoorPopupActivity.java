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
import ng.dat.ar.model.ARPoint;

public class OutDoorPopupActivity extends Activity {

    private TextView building;
    private Button btn;
    private TextView r1;
    private TextView r2;
    private TextView r;
    private String url_bname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_outdoor_popup);

        Intent intent =getIntent();
        String buildingName = intent.getStringExtra("buildingName");

        //UI 객체생성
        building = (TextView)findViewById(R.id.buildingname);
        btn = (Button)findViewById(R.id.closebtn);
        r1= (TextView)findViewById(R.id.resulttext1);
        r2= (TextView)findViewById(R.id.resulttext2);
        r= (TextView)findViewById(R.id.result);

        building.setText(buildingName);


        String url_start="http://52.78.123.18/outdoor.php?bname=%27";
        url_bname=buildingName;
        String url_end="%27";
        String url= url_start + url_bname + url_end;

        // AsyncTask를 통해 HttpURLConnection 수행.
        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();

        r1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/NanumBarunpenB.ttf"));
        building.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/NanumBarunpenB.ttf"));
        r2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/NanumBarunpenB.ttf"));
        r.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/NanumBarunpenB.ttf"));

        //X버튼 눌러서 팝업 지우기
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

            try {

                JSONArray array = new JSONObject(s).getJSONArray("information");
                    JSONObject jObject = array.getJSONObject(0);

                    if(jObject.getString("building_name").equals(url_bname))
                        r.setText(jObject.getString("building_content"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
