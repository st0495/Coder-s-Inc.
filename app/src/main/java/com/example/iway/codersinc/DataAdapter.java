package com.example.iway.codersinc;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.example.iway.codersinc.notifyme.MyReceiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<DataModel> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textstarttime;
        TextView textendtime;
        TextView textstartdate;
        TextView textenddate;
        TextView textsitename;
        ImageView imageViewIcon;
        TextView textshare;
        TextView textsync;
        TextView textnotify;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textstarttime = (TextView) itemView.findViewById(R.id.textstarttime);
            this.textendtime  = (TextView) itemView.findViewById(R.id.textendtime);
            this.textsitename=(TextView)itemView.findViewById(R.id.textsitename);
            this.textstartdate=(TextView)itemView.findViewById(R.id.textstartdate);
            this.textenddate=(TextView)itemView.findViewById(R.id.textenddate);
            this.textshare=(TextView)itemView.findViewById(R.id.shareit);
            this.textsync=(TextView)itemView.findViewById(R.id.textsync);
            this.textnotify=(TextView)itemView.findViewById(R.id.textnotify);
            // this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    public DataAdapter(ArrayList<DataModel> data,Context context) {
        this.dataSet = data;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);


        //view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textstarttime = holder.textstarttime;
        TextView textendtime = holder.textendtime;
        TextView textsitename=holder.textsitename;
        TextView textenddate=holder.textenddate;
        TextView textstartdate=holder.textstartdate;
        TextView textshare = holder.textshare;
        TextView textsync = holder.textsync;
        TextView textnotifyme=holder.textnotify;
        // ImageView imageView = holder.imageViewIcon;

        textViewName.setText(dataSet.get(listPosition).getName());
        textstarttime.setText(dataSet.get(listPosition).getStarttime());
        textendtime.setText(dataSet.get(listPosition).getEndtime());
        textsitename.setText(dataSet.get(listPosition).getSitename());
        textstartdate.setText(dataSet.get(listPosition).getStartdate());
        textenddate.setText(dataSet.get(listPosition).getEnddate());
        textshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, dataSet.get(listPosition).getLink());
                sendIntent.setType("text/plain");
                Intent.createChooser(sendIntent, "Share via");
                context.startActivity(sendIntent);
            }
        });
        textsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calintent = new Intent(Intent.ACTION_INSERT);
                calintent.setData(CalendarContract.Events.CONTENT_URI);
                calintent.setType("vnd.android.cursor.item/event");
                calintent.putExtra(CalendarContract.Events.TITLE, dataSet.get(listPosition).getName());
                calintent.putExtra(CalendarContract.Events.EVENT_LOCATION, dataSet.get(listPosition).getSitename());
                context.startActivity(calintent);
            }
        });


        textnotifyme.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String type= dataSet.get(listPosition).getType();
                if(type.equals("present")){
                    Toast.makeText(context, "This contest has already started", Toast.LENGTH_SHORT).show();
                }
                else {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.MONTH, 9);
                    calendar.set(Calendar.YEAR, 2016);
                    calendar.set(Calendar.DAY_OF_MONTH, 16);

                    calendar.set(Calendar.HOUR_OF_DAY, 13);
                    calendar.set(Calendar.MINUTE, 25);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.AM_PM, Calendar.PM);
                    Toast.makeText(context, "Your Responce has been Received : you'll be notified", Toast.LENGTH_SHORT).show();
                }

              /*  Intent myIntent = new Intent(MainActivity.this, MyReceiver.class);
                if(PendingIntent.getBroadcast(getClass(), 0,
                        myIntent,
                        PendingIntent.FLAG_NO_CREATE) == null) {
                    pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
*/
                }
        });

        //imageView.setImageResource(dataSet.get(listPosition).getImage());


    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void setFilter(List<DataModel> countryModels) {
        dataSet = new ArrayList<>();
        dataSet.addAll(countryModels);
        notifyDataSetChanged();
    }

}
