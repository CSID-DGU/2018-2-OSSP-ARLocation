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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ng.dat.ar.Utils.DBInfo;
import ng.dat.ar.Utils.ReqeustHttpURLConnection;
import ng.dat.ar.helper.LocationHelper;
import ng.dat.ar.model.ARPoint;

/**
 * Created by ntdat on 1/13/17.
 */
/*TODO: php파일로 테이블을 json으로 출력할 때 jsonarray 형식으로 출력->for문 쓰지말고 json 메소드로 바꾸기

 */
public class AROverlayView extends View {

    Context context;
    private float[] rotatedProjectionMatrix = new float[16];
    private Location currentLocation;
    private DBInfo dbInfo;



    public AROverlayView(Context context) {
        super(context);
        this.context = context;
        dbInfo = new DBInfo(context);
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
        for (int i = 0; i < dbInfo.arPoints.size(); i ++) {
            float[] currentLocationInECEF = LocationHelper.WSG84toECEF(currentLocation);
            float[] pointInECEF = LocationHelper.WSG84toECEF(dbInfo.arPoints.get(i).getLocation());
            float[] pointInENU = LocationHelper.ECEFtoENU(currentLocation, currentLocationInECEF, pointInECEF);

            float[] cameraCoordinateVector = new float[4];
            Matrix.multiplyMV(cameraCoordinateVector, 0, rotatedProjectionMatrix, 0, pointInENU, 0);

            // cameraCoordinateVector[2] is z, that always less than 0 to display on right position
            // if z > 0, the point will display on the opposite
            if (cameraCoordinateVector[2] < 0) {
                float x  = (0.5f + cameraCoordinateVector[0]/cameraCoordinateVector[3]) * canvas.getWidth();
                float y = (0.5f - cameraCoordinateVector[1]/cameraCoordinateVector[3]) * canvas.getHeight();
                if((currentLocation.getLatitude()-dbInfo.arPoints.get(i).getLocation().getLatitude()<=0.0005 &&
                        currentLocation.getLatitude()-dbInfo.arPoints.get(i).getLocation().getLatitude()>=-0.0005) ||
                        (currentLocation.getLongitude() - dbInfo.arPoints.get(i).getLocation().getLongitude() <= 0.0005 &&
                        currentLocation.getLongitude() - dbInfo.arPoints.get(i).getLocation().getLongitude() >= -0.0005)) {
                        canvas.drawCircle(x, y, radius, paint);
                        canvas.drawText(dbInfo.arPoints.get(i).getName(), x - (30 * dbInfo.arPoints.get(i).getName().length() / 2), y - 80, paint);
                        dbInfo.buildingNameList[z] = dbInfo.arPoints.get(i).getName();
                        z++;
                }
            }
        }
    }

    public String[] getBuildingNameList(){
        return dbInfo.buildingNameList;
    }
}
