package ym.jw.yi;

import android.content.Context;

import java.util.List;

import ym.jw.yi.Utils.DBInfo;
import ym.jw.yi.model.APPoint;

public class APView {
    private Context context;
    private List<APPoint> apPoints;
    private DBInfo dbInfo;
    public APView(Context context, DBInfo dbInfo){
        this.context = context;
        this.dbInfo = dbInfo;
    }

    public String searchingFloorName(String currentAPMacAddress){
        for(int i = 0; i< dbInfo.apPoints.size(); i++){
            if(currentAPMacAddress.equals(dbInfo.apPoints.get(i).getMacAddress())){
                return dbInfo.apPoints.get(i).getFloorName();
            }
        }
        return "일치하는 정보가 없음";
    }
}
