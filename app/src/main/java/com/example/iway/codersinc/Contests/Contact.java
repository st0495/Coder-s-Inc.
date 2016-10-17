package com.example.iway.codersinc.Contests;

/**
 * Created by Shashank on 25-Sep-16.
 */

/**
 * Created by Shashank on 24-Sep-16.
 */
public class Contact {

    //private variables
    String Name;
    String Site;
    String Begindate;
    String Enddate;
    String Begin;
    String End;
    String Type;
    String Link;

    // Empty constructor
    public Contact(){

    }
    // constructor
    public Contact(String Name, String Type ,String Site, String Begindate, String Begin,String Enddate, String End,String Link ){
        this.Name = Name;
        this.Type=Type;
        this.Site=Site;
        this.Begindate=Begindate;

        this.Begin = Begin;
        this.Enddate=Enddate;
        this.End = End;
        this.Link=Link;

    }

    // constructor
    public Contact(String Name, String Begin,String End){
        this.Name = Name;
        this.Begin=Begin;
        this.End=End;
    }
    // getting ID
    public String getName(){
        return this.Name;
    }

    // setting id
    public void setName(String Name){
        this.Name = Name;
    }

    // getting name
    public String getBegin(){
        return this.Begin;
    }

    // setting name0
    public void setBegin(String Begin){
        this.Begin = Begin;
    }

    // getting phone number
    public String getEnd(){
        return this.End;
    }

    // setting phone number
    public void setEnd(String End){
        this.End = End;
    }
    public String getType(){
        return this.Type;
    }
    public String setType(String Type){
        this.Type =Type;
        return Type;
    }
    public String getSite(){
        return this.Site;
    }
    public void setSite(String Site){
        this.Site=Site;
    }
    public String getBegindate(){
        return this.Begindate;
    }
    public void setBegindate(String Begindate){
        this.Begindate=Begindate;
    }
    public  String getEnddate(){
        return this.Enddate;

    }
    public void setEnddate(String Enddate){
        this.Enddate=Enddate;
    }
    public  String getLink(){
        return  this.Link;
    }
    public  void setLink(String Link){
        this.Link=Link;
    }

}
