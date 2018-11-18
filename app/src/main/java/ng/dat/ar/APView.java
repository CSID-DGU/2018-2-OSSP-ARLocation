package ng.dat.ar;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ng.dat.ar.model.APPoint;

public class APView {
    private Context context;
    private List<APPoint> apPoints;
    public APView(Context context){
        this.context = context;
        apPoints = new ArrayList<APPoint>() {{
            add(new APPoint("B4:5D:50:6A:43:F2", "신공학관 3층","알파실, 컴퓨터공학과 실습실 1,2, 컴퓨터공학과 학생회실"));
            add(new APPoint("B4:5D:50:6A:43:E2", "신공학관 3층","알파실, 컴퓨터공학과 실습실 1,2, 컴퓨터공학과 학생회실"));
            add(new APPoint("70:3A:0E:AD:02:12","신공학관 3층", "알파실, 컴퓨터공학과 실습실 1,2, 컴퓨터공학과 학생회실"));
            add(new APPoint("A8:BD:27:80:15:92", "신공학관 3층","알파실, 컴퓨터공학과 실습실 1,2, 컴퓨터공학과 학생회실"));
        }};
    }

    public String searchingFloorName(String currentAPMacAddress){
        for(int i = 0; i< apPoints.size(); i++){
            if(currentAPMacAddress.equals(apPoints.get(i).getMacAddress())){
                return apPoints.get(i).getFloorName();
            }
        }
        return "일치하는 정보가 없음";
    }
}
