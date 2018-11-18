package ng.dat.ar;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ng.dat.ar.Utils.ReqeustHttpURLConnection;
import ng.dat.ar.model.APPoint;
import ng.dat.ar.model.ARPoint;

public class APView {
    private Context context;
    private List<APPoint> apPoints;

    public APView(Context context) {

        this.context = context;

       /* String url = "http://52.78.123.18/indoorTotal.php";

        // AsyncTask를 통해 HttpURLConnection 수행.
        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();*/

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

            apPoints = new ArrayList<APPoint>();

            try {
                JSONObject json = new JSONObject(s);
                for (int i = 1; i <= 28; i++) {
                    String mac = "";
                    String f_name = "";
                    String f_info = "";

                    if (json.getString("id").equals(i)) {  //TODO: indoor 디비 보고 바꾸기
                        mac = json.getString("id");
                        f_name=json.getString("id");
                        f_info=json.getString("id");

                        apPoints.add(new APPoint(mac, f_name, f_info));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            /*Demo points
             apPoints = new ArrayList<APPoint>() {{
            add(new APPoint("B4:5D:50:6A:43:F2", "신공학관 3층", "알파실, 컴퓨터공학과 실습실 1,2, 컴퓨터공학과 학생회실"));
            }};*/

        }

    }


    public String searchingFloorName(String currentAPMacAddress) {
        for (int i = 0; i < apPoints.size(); i++) {
            if (currentAPMacAddress.equals(apPoints.get(i).getMacAddress())) {
                return apPoints.get(i).getFloorName();
            }
        }
        return "일치하는 정보가 없음";
    }
}
