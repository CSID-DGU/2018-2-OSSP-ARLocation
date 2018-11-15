package ng.dat.ar;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ng.dat.ar.model.APPoint;

public class APView {
    private Context context;
    private List<APPoint> arPoints;
    public APView(Context context){
        this.context = context;
        arPoints = new ArrayList<APPoint>() {{
            add(new APPoint("B4:5D:50:6A:43:F2", "신공학관 3층","알파실, 컴퓨터공학과 실습실 1,2, 컴퓨터공학과 학생회실"));
        }};
    }

    public String searchingFloorName(String currentAPMacAddress){
        for(int i=0; i<arPoints.size();i++){
            if(currentAPMacAddress.equals(arPoints.get(i).getMacAddress())){
                return arPoints.get(i).getFloorName();
            }
        }
        return "일치하는 정보가 없음";
    }
}
