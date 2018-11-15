package ng.dat.ar;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Location;
import android.opengl.Matrix;
import android.os.AsyncTask;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ng.dat.ar.Utils.ReqeustHttpURLConnection;
import ng.dat.ar.helper.LocationHelper;
import ng.dat.ar.model.ARPoint;

/**
 * Created by ntdat on 1/13/17.
 */

public class AROverlayView extends View {

    Context context;
    private float[] rotatedProjectionMatrix = new float[16];
    private Location currentLocation;
    private List<ARPoint> arPoints;
    private String[] buildingNameList;

    private ARActivity ar;


    public AROverlayView(Context context) {
        super(context);
        this.context = context;

        String url = "http://52.78.123.18/outdoorTotal.php";

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
                    JSONObject json = new JSONObject(s);
                    for(int i=1;i<=28;i++){
                        String name="";
                        double lat=0;
                        double lon=0;
                        double alt=0;
                        if(json.getString("bid").equals(i)) {  //for문의 i
                            name = json.getString("bname");
                            lat = json.getDouble("latitude");
                            lon = json.getDouble("longitude");
                            alt = json.getDouble("altitude");

                            arPoints.add(new ARPoint(name, lat, lon, alt));
                        }
                    }
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

    public void updateRotatedProjectionMatrix(float[] rotatedProjectionMatrix) {
        this.rotatedProjectionMatrix = rotatedProjectionMatrix;
        this.invalidate();
    }

    public void updateCurrentLocation(Location currentLocation){
        this.currentLocation = currentLocation;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (currentLocation == null) {
            return;
        }

        final int radius = 30;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        paint.setTextSize(60);

        int z=0;
        for (int i = 0; i < arPoints.size(); i ++) {
            float[] currentLocationInECEF = LocationHelper.WSG84toECEF(currentLocation);
            float[] pointInECEF = LocationHelper.WSG84toECEF(arPoints.get(i).getLocation());
            float[] pointInENU = LocationHelper.ECEFtoENU(currentLocation, currentLocationInECEF, pointInECEF);

            float[] cameraCoordinateVector = new float[4];
            Matrix.multiplyMV(cameraCoordinateVector, 0, rotatedProjectionMatrix, 0, pointInENU, 0);

            // cameraCoordinateVector[2] is z, that always less than 0 to display on right position
            // if z > 0, the point will display on the opposite
            if (cameraCoordinateVector[2] < 0) {
                float x  = (0.5f + cameraCoordinateVector[0]/cameraCoordinateVector[3]) * canvas.getWidth();
                float y = (0.5f - cameraCoordinateVector[1]/cameraCoordinateVector[3]) * canvas.getHeight();
                if(currentLocation.getLatitude()-arPoints.get(i).getLocation().getLatitude()<=0.0002 &&
                        currentLocation.getLatitude() - arPoints.get(i).getLocation().getLatitude()>=0.0002) {
                    canvas.drawCircle(x, y, radius, paint);
                    canvas.drawText(arPoints.get(i).getName(), x - (30 * arPoints.get(i).getName().length() / 2), y - 80, paint);
                    buildingNameList[z] = arPoints.get(i).getName();
                    z++;
                }
            }
        }
    }

    public String[] getBuildingNameList(){
        return buildingNameList;
    }
}
