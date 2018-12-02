package ym.jw.yi;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsMessage;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ym.jw.yi.Utils.ReqeustHttpURLConnection;

public class InDoorPopupActivity extends Activity {
    private TextView floor;
    private Button btn;
    private TextView r1;
    private TextView r2;
    private TextView r3;
    private TextView r4;
    private TextView r;
    private String url_mac;
    private String speak_string;

    private final int CHECK_CODE = 0x1;

    private Speaker speaker;

    private ToggleButton toggle;
    private CompoundButton.OnCheckedChangeListener toggleListener;

    private BroadcastReceiver smsReceiver;


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

        speak_string=r1.getText().toString()+floor.getText().toString()+r2.getText().toString()+
                r3.getText().toString()+r.getText().toString()+r4.getText().toString();

        toggle = (ToggleButton)findViewById(R.id.speak);

        toggleListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                if(isChecked){
                    speaker.allow(true);
                    speaker.speak(speak_string);
                    toggle.setBackgroundDrawable(getResources().getDrawable(R.drawable.speaker));
                }else{
                    speaker.allow(false);
                    toggle.setBackgroundDrawable(getResources().getDrawable(R.drawable.speaker_off));
                }
            }
        };
        toggle.setOnCheckedChangeListener(toggleListener);

        checkTTS();
        initializeSMSReceiver();
        registerSMSReceiver();

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

                floor.setText(jObject.getString("indoor_floor"));
                r.setText(jObject.getString("indoor_content"));


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void checkTTS(){
        Intent check = new Intent();
        check.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(check, CHECK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CHECK_CODE){
            if(resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
                speaker = new Speaker(this);
            }else {
                Intent install = new Intent();
                install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(install);
            }
        }
    }

    private void initializeSMSReceiver(){
        smsReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {

                Bundle bundle = intent.getExtras();
                if(bundle!=null){
                    Object[] pdus = (Object[])bundle.get("pdus");
                    for(int i=0;i<pdus.length;i++){
                        byte[] pdu = (byte[])pdus[i];
                        SmsMessage message = SmsMessage.createFromPdu(pdu);
                        String text = message.getDisplayMessageBody();
                        String sender = getContactName(message.getOriginatingAddress());
                        speaker.speak(text);
                    }
                }

            }
        };
    }

    private void registerSMSReceiver() {
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsReceiver, intentFilter);
    }

    private String getContactName(String phone){
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phone));
        String projection[] = new String[]{ContactsContract.Data.DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if(cursor.moveToFirst()){
            return cursor.getString(0);
        }else {
            return "unknown number";
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsReceiver);
        speaker.destroy();
    }

}
