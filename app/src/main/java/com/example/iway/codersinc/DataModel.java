package com.example.iway.codersinc;

/**
 * Created by IWAY on 19-09-2016.
 */
public class DataModel {

    String name;
    String starttime;
    String endtime;
    String sitename;
    String startdate;
    String enddate;
    String link;
    String type;


    public DataModel(String sitename , String name, String startdate, String starttime,String enddate, String endtime,String link,String type)//, int id_)//, int image)
    {
        this.sitename=sitename;
        this.name = name;
        this.starttime = starttime;
        this.endtime = endtime;
        this.startdate=startdate;;
        this.enddate=enddate;
        this.link=link;
        this.type=type;
    }

    public String getName() {
        return name;
    }
    public String getStarttime() { return starttime; }
    public String getEndtime(){ return  endtime;}
    public String getStartdate(){ return startdate; }
    public String getEnddate() { return enddate; }
    public String getSitename() { return  sitename; }
    public String getLink() { return link; }
    public String getType() { return type; }
}