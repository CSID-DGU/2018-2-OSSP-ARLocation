package ng.dat.ar.model;

public class APPoint {
    private String macAddress;
    private String floorName;
    private String floorInfo;

    public APPoint(String macAddress, String floorName, String floorInfo){
        this.macAddress = macAddress;
        this.floorName = floorName;
        this.floorInfo = floorInfo;
    }
    public void setMacAddress(String str){
        macAddress = str;
    }
    public void setFloorName(String str){
        floorName = str;
    }
    public void setFloorInfo(String str){
        floorInfo = str;
    }
    public String getMacAddress(){ return macAddress;}
    public String getFloorName(){return floorName;}
    public String getFloorInfo(){return floorInfo;}
}
