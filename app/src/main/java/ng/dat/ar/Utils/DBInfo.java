package ng.dat.ar.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ng.dat.ar.model.ARPoint;


public class DBInfo {
    private Context context;
    public List<ARPoint> arPoints;
    public String[] buildingNameList;

    public DBInfo(Context context){
        this.context = context;

        String url = "http://52.78.123.18/oT.php";

        // AsyncTask를 통해 HttpURLConnection 수행.
        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();
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

            arPoints = new ArrayList<ARPoint>();

            try {
                String name=null;
                double lat=0;
                double lon=0;
                double alt=0;

                JSONArray array = new JSONObject(s).getJSONArray("BUILDING");
                for(int i=0; i<array.length(); i++){
                    JSONObject jObject = array.getJSONObject(i);

                    name = jObject.optString("building_name");
                    lat = jObject.optDouble("building_latitude");
                    lon=jObject.optDouble("building_longitude");
                    alt = jObject.optDouble("building_altitude");

                    arPoints.add(new ARPoint(name,lat,lon,alt));
                }
                    /*JSONArray jsonArray = new JSONArray(array);
                    if(jsonArray!=null){
                        for(int i=0;i<jsonArray.length();i++){
                            name= jsonArray.getJSONObject(i).getString("building_name");
                            lat = jsonArray.getJSONObject(i).getDouble("building_latitude");
                            lon = jsonArray.getJSONObject(i).getDouble("building_longtitude");
                            alt = jsonArray.getJSONObject(i).getDouble("building_altitude");

                            arPoints.add(new ARPoint(name,lat,lon,alt));
                        }
                    }

                   for(int i=0;i<array.length();i++){
                        JSONObject row = array.getJSONObject(i);
                        name = row.getString("building_name");
                        lat = row.getDouble("building_latitude");
                        lon = row.getDouble("building_longtitude");
                        alt = row.getDouble("building_altitude");

                        System.out.println(name);
                        System.out.println(lat);
                        System.out.println(lon);
                        System.out.println(alt);

                        arPoints.add(new ARPoint(name, lat, lon, alt));

                   }*/
                //arPoints.add(new ARPoint("동양파라빌", 37.5740069, 127.0205818, 92));

            } catch (JSONException e) {
                e.printStackTrace();
            }



            /*Demo points
            arPoints = new ArrayList<ARPoint>() {{
                add(new ARPoint("정보문화관", 37.5595638,126.9963624,0));
                add(new ARPoint("학군단1", 37.558605, 126.998672, 92));
                add(new ARPoint("학군단2", 37.558594, 126.998749, 92));
                add(new ARPoint("학군단3", 37.558746, 126.998795, 92));
                add(new ARPoint("학군단4", 37.558765, 126.998741, 92 ));
                add(new ARPoint("동양파라빌", 37.5740069, 127.0205818, 92));
                add(new ARPoint("원흥별관", 37.55865, 126.99872, 69.2));
            }};*/

            buildingNameList = new String[arPoints.size()];
            for(int i=0; i<arPoints.size();i++){
                buildingNameList[i] = null;
            }

        }

    }


}
