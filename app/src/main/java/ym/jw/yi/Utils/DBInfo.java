package ym.jw.yi.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ym.jw.yi.model.APPoint;
import ym.jw.yi.model.ARPoint;

/**
 * author: Jiwoon Kim
 * date: 2018-10-20
 * purpose: 어플 시작 동작 구현
 */
public class DBInfo {
    private Context context;
    public List<ARPoint> arPoints;
    public List<APPoint> apPoints;
    public String[] buildingNameList;

    public DBInfo(Context context) {
        this.context = context;

        //php 연결
        String outdoorurl = "http://52.78.123.18/oT.php";
        String indoorurl = "http://52.78.123.18/iT.php";

        // AsyncTask를 통해 HttpURLConnection 수행.
        OutDoorNetworkTask outDoornetworkTask = new OutDoorNetworkTask(outdoorurl, null);
        outDoornetworkTask.execute();
        InDoorNetworkTask inDoorNetworkTask = new InDoorNetworkTask(indoorurl, null);
        inDoorNetworkTask.execute();
    }

    //실외 동작
    public class OutDoorNetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public OutDoorNetworkTask(String url, ContentValues values) {

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

            arPoints = new ArrayList<ARPoint>();

            try {
                String name = null;
                double lat = 0;
                double lon = 0;
                double alt = 0;

                JSONArray array = new JSONObject(s).getJSONArray("BUILDING");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jObject = array.getJSONObject(i);

                    name = jObject.optString("building_name");
                    lat = jObject.optDouble("building_latitude");
                    lon = jObject.optDouble("building_longitude");
                    alt = jObject.optDouble("building_altitude");

                    arPoints.add(new ARPoint(name, lat, lon, alt));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }


            buildingNameList = new String[arPoints.size()];
            for (int i = 0; i < arPoints.size(); i++) {
                buildingNameList[i] = null;
            }

        }

    }

    //실내 동작
    public class InDoorNetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public InDoorNetworkTask(String url, ContentValues values) {

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

            apPoints = new ArrayList<APPoint>();

            try {
                String macAddress = null;
                String floorName = null;
                String content = null;

                JSONArray array = new JSONObject(s).getJSONArray("INDOOR");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jObject = array.getJSONObject(i);

                    macAddress = jObject.optString("indoor_macAddr");
                    floorName = jObject.optString("indoor_floor");
                    content = jObject.optString("indoor_content");


                    apPoints.add(new APPoint(macAddress, floorName, content));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }

    }

}
