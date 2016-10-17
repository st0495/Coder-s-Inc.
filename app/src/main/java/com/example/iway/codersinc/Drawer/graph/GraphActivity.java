package com.example.iway.codersinc.Drawer.graph;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.iway.codersinc.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.select.Elements;
//import org.jsoup.nodes.Element;

public class GraphActivity extends Activity {

    private Document htmlDocument;
    // private String htmlPageUrl = "https://inducesmile.com/";

    private String htmlPageUrl = "https://www.codechef.com/users/";
    private TextView parsedHtmlNode;
    private String htmlContentInStringFormat;
    private String tableId="problem_stats";
    private String[] arr=new String [9];
    private String[] arr1=new String [9];
    Float r[]=new Float[9];
    EditText a;
    String h;
    float sum =(float) 0;



    private TextView txtinfo;
    LinearLayout lvOne, lvTwo, lvThree, lvFour, lvFive, lvparent;
    TextView txtOne, txtTwo, txtThree, txtFour, txtFive;
    Button btnundo, btnsave;
    PieView pieView;
    Uri outputFileUri;
    OutputStream outStream = null;
   // String[] petNames = new String[] { "Accepted", "WA", "CTE", "RTE", "RTE" };
   // int[] percentage = new int[] { 55, 25, 10, 5, 5 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        a=(EditText)findViewById(R.id.editText1);
        Button htmlTitleButton = (Button)findViewById(R.id.button);

        if (htmlTitleButton != null) {
            htmlTitleButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    h=a.getText().toString();
                    htmlPageUrl=htmlPageUrl+h;
                    JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                    jsoupAsyncTask.execute();
                }
            });
        }



        //parsedHtmlNode = (TextView)findViewById(R.id.html_content);


       /* btnundo = (Button) findViewById(R.id.btnundo);
        btnsave = (Button) findViewById(R.id.btnsave);
        btnsave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                SaveImage();
                Intent intent=new Intent(MainActivity.this,PieView.class);
                MainActivity.this.startActivity(intent);
            }
        });

        btnundo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                pieView.removeSelectedPie();
                txtinfo.setText("Total Submissions");
            }
        });
        */




    }

  /*  public void SaveImage() {
        lvparent.buildDrawingCache();
        Bitmap bm = lvparent.getDrawingCache();

        try {
            File root = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "PieChartsExample" + File.separator);
            root.mkdirs();
            File sdImageMainDirectory = new File(root, "piechart.jpg");
            outputFileUri = Uri.fromFile(sdImageMainDirectory);
            outStream = new FileOutputStream(sdImageMainDirectory);
        } catch (Exception e) {
            Toast.makeText(this, "Error occured. Please try again later.",
                    Toast.LENGTH_SHORT).show();
        }

        try {
            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
            Toast.makeText(this, "Created successfully!", Toast.LENGTH_SHORT)
                    .show();
        } catch (Exception e) {
        }
    }
    */
  private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

      @Override
      protected void onPreExecute() {
          super.onPreExecute();
      }

      @Override
      protected Void doInBackground(Void... params) {
          try {
              htmlDocument = Jsoup.connect(htmlPageUrl).get();
              // htmlContentInStringFormat = htmlDocument.title()
              org.jsoup.nodes.Element table = htmlDocument.getElementById(tableId);
              Elements tds = table.getElementsByTag("td");
              int i=0;
              int t=0;
              int s=0;
              for (org.jsoup.nodes.Element td : tds) {
                  i++;
                  if(i<=9)
                  {
                      arr1[s]=td.text();
                      System.out.println("\n"+arr1[s]);
                      s++;

                  }
                  if(i>9)
                  {arr[t]=td.text();
                      System.out.println("\n"+arr[t]);
                      t++;
                  }
              }

              for(int j=4;j<=8;j++)
              {
                  r[j]=Float.parseFloat(arr[j]);
                  sum = (float)(sum + r[j]);
               }

          } catch (IOException e) {
              e.printStackTrace();
          }
          return null;
      }

      @Override
      protected void onPostExecute(Void result) {
          //parsedHtmlNode.setText(htmlContentInStringFormat);
          // parsedHtmlNode.setText(htmlDocument);
          setContentView(R.layout.activity_graph);

          txtinfo = (TextView) findViewById(R.id.txtinfo);
          pieView = (PieView) findViewById(R.id.pie_view);

          lvOne = (LinearLayout) findViewById(R.id.lvOne);
          lvTwo = (LinearLayout) findViewById(R.id.lvTwo);
          lvThree = (LinearLayout) findViewById(R.id.lvThree);
          lvFour = (LinearLayout) findViewById(R.id.lvFour);
          lvFive = (LinearLayout) findViewById(R.id.lvFive);
          lvparent = (LinearLayout) findViewById(R.id.lvparent);

          txtOne = (TextView) findViewById(R.id.txtOne);
          txtTwo = (TextView) findViewById(R.id.txtTwo);
          txtThree = (TextView) findViewById(R.id.txtThree);
          txtFour = (TextView) findViewById(R.id.txtFour);
          txtFive = (TextView) findViewById(R.id.txtFive);
          set(pieView);


      }
  }

    private void set(PieView pieView) {
        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<PieHelper>();

        int color0 = Color.rgb(0, 128, 255);
        int color1 = Color.rgb(252, 3, 71);
        int color2 = Color.rgb(117, 91, 4);
        int color3 = Color.rgb(3, 7, 173);
        int color4 = Color.rgb(20, 156, 82);

        pieHelperArrayList.add(new PieHelper(((r[4]/sum)*100) , color4));
        pieHelperArrayList.add(new PieHelper(((r[5]/sum)*100), color1));

        pieHelperArrayList.add(new PieHelper(((r[6]/sum)*100), color2));

        pieHelperArrayList.add(new PieHelper(((r[7]/sum)*100), color3));

        pieHelperArrayList.add(new PieHelper(((r[8]/sum)*100), color0));

        lvFive.setBackgroundColor(color0);
        txtOne.setText(arr1[4].toString());
        lvTwo.setBackgroundColor(color1);
        txtTwo.setText(arr1[5].toString());
        lvThree.setBackgroundColor(color2);
        txtThree.setText(arr1[6].toString());
        lvFour.setBackgroundColor(color3);
        txtFour.setText(arr1[7].toString());
        lvOne.setBackgroundColor(color4);
        txtFive.setText(arr1[8].toString());

        pieView.setDate(pieHelperArrayList);
        pieView.setOnPieClickListener(new PieView.OnPieClickListener() {
            @Override
            public void onPieClick(int index) {
                if (index != PieView.NO_SELECTED_INDEX) {
                    txtinfo.setText(r[index+4] + " "
                            + arr1[index+4] + ".");
                } else {
                    txtinfo.setText("No selected pie");
                }
            }
        });
    }
}