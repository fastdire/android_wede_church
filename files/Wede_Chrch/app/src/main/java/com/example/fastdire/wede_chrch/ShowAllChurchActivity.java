package com.example.fastdire.wede_chrch;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;

public class ShowAllChurchActivity extends AppCompatActivity {

    ListView ChurchListView;
    ProgressBar progressBar;
    String HttpUrl = "http://192.168.43.169/CRUD/AllChurchData.php";
    List<String> IdList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_all_church);

        ChurchListView = findViewById(R.id.listview1);

        progressBar = findViewById(R.id.progressBar);

        new GetHttpResponse(ShowAllChurchActivity.this).execute();

        //Adding ListView Item click Listener.
        ChurchListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO Auto-generated method stub

                Intent intent = new Intent(ShowAllChurchActivity.this,ShowSingleRecordActivity.class);

                // Sending ListView clicked value using intent.
                intent.putExtra("ListViewValue", IdList.get(position).toString());

                startActivity(intent);

                //Finishing current activity after open next activity.
                finish();

            }
        });
    }

    // JSON parse class started from here.
    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        public Context context;

        String JSonResult;

        List<Church> churchList;

        public GetHttpResponse(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            // Passing HTTP URL to HttpServicesClass Class.
            HttpServicesClass httpServicesClass = new HttpServicesClass(HttpUrl);
            try
            {
                httpServicesClass.ExecutePostRequest();

                if(httpServicesClass.getResponseCode() == 200)
                {
                    JSonResult = httpServicesClass.getResponse();

                    if(JSonResult != null)
                    {
                        JSONArray jsonArray = null;

                        try {
                            jsonArray = new JSONArray(JSonResult);

                            JSONObject jsonObject;

                            Church church;

                            churchList = new ArrayList<Church>();

                            for(int i=0; i<jsonArray.length(); i++)
                            {
                                church = new Church();

                                jsonObject = jsonArray.getJSONObject(i);

                                // Adding Church Id TO  Array.
                                IdList.add(jsonObject.getString("id").toString());

                                //Adding Church Name.
                                church.ChurchName = jsonObject.getString("church_name").toString();

                                churchList.add(church);

                            }
                        }
                        catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    Toast.makeText(context, httpServicesClass.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)

        {
            progressBar.setVisibility(View.GONE);

            ChurchListView.setVisibility(View.VISIBLE);

            ListAdapterClass adapter = new ListAdapterClass(churchList, context);

            ChurchListView.setAdapter(adapter);

        }
    }
}