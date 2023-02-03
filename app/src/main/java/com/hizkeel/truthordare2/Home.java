package com.hizkeel.truthordare2;

import static com.hizkeel.truthordare2.JsonQuestion.readJson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    ProgressDialog progressDialog;
    public static String code;
    public static String test_id;
    EditText etCode;
    public static String author, category, description, title, duration, noOfQuestion, language;

    private List<Item2> itemList = new ArrayList<>();
    private List<Item2> itemListTrend = new ArrayList<>();

    // 2 horizontal  recyclerview
    private RecyclerView recyclerView, recyclerView3;

    // adapter for the recycler view.
    private ItemAdapter2 mAdapter ;

    private ItemAdapter2  mAdapterTrend;

    UserData userData;


    TextView btnStart, tvGreet;

    LinearLayout btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.home:
                        return true;

                    case R.id.notification:
                        startActivity(new Intent(getApplicationContext(), Notification.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });



        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);

        recyclerView3 = (RecyclerView) findViewById(R.id.recyclerView3);

        mAdapter = new ItemAdapter2(itemList,  Home.this);

        mAdapterTrend = new ItemAdapter2(itemListTrend,  Home.this);



        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( Home.this,  LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new DividerItemDecoration( getActivity(), LinearLayoutManager.HORIZONTAL));

        recyclerView.setAdapter((RecyclerView.Adapter) mAdapter);

        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager( Home.this,  LinearLayoutManager.VERTICAL, false);
        recyclerView3.setLayoutManager(mLayoutManager3);
        recyclerView3.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new DividerItemDecoration( getActivity(), LinearLayoutManager.HORIZONTAL));

        recyclerView3.setAdapter((RecyclerView.Adapter) mAdapterTrend);



//        loadData();

//        loadAssessmentNew();
//        loadAssessmentTrend();

        updateNewList();
//        updateTrendList();

//        loadData();
//        loadData2();


    }

    private void loadData() {

        Item2 item = new Item2("titlex", "descriptionx", "creatorx", "viewsx", "ratingx", "codex");
        itemList.add(item);

        item = new Item2("titlex", "descriptionx", "creatorx", "viewsx", "ratingx", "codex");
        itemList.add(item);




        mAdapter.notifyDataSetChanged();
    }

    private void loadData2() {

        Item2 item = new Item2("titlex", "descriptionx", "creatorx", "viewsx", "ratingx", "codex");
        itemList.add(item);

        item = new Item2("titlex", "descriptionx", "creatorx", "viewsx", "ratingx", "codex");
        itemList.add(item);




        mAdapter.notifyDataSetChanged();
    }



    public void view(View v) {
        Intent intent = new Intent(this, VibeInfo.class);
        startActivity(intent);
    }

    public void progProc(){
        progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Processing"); // Setting Title
        //progressDialog.setIcon(R.drawable.app_logo);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

    }

    public void updateNewList(){

        String iop = readJson(getApplicationContext(), "new_list");

        Toast.makeText(getApplicationContext(), "x"+ iop, Toast.LENGTH_LONG).show();
        JSONObject obj = null;



        try {
            obj = new JSONObject(iop);

            JSONArray m_jArry = obj.getJSONArray("list");

            for(int i = 0;  i <  10; i++){

                String title = m_jArry.getJSONObject(i).getString("title");
                String description = m_jArry.getJSONObject(i).getString("description");
                String creator = m_jArry.getJSONObject(i).getString("creator");
                String code = m_jArry.getJSONObject(i).getString("code");
                String review = m_jArry.getJSONObject(i).getString("views");
                String rating = m_jArry.getJSONObject(i).getString("rating");


//                Toast.makeText(getContext(), "title:"+title, Toast.LENGTH_LONG).show();

                Item2 item = new Item2(title, "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been ever since the 1500s, when an unknown printer\n", "creatorx", "viewsx", "ratingx", code);
                itemList.add(item);


                mAdapter.notifyDataSetChanged();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void updateTrendList(){

        String iop = readJson(getApplicationContext(), "trend_list");
        JSONObject obj = null;
        try {
            obj = new JSONObject(iop);

            JSONArray m_jArry = obj.getJSONArray("list");

            for(int i = 0;  i <  10; i++){

                String title = m_jArry.getJSONObject(i).getString("title");

                String code = m_jArry.getJSONObject(i).getString("testCode");

                String author = m_jArry.getJSONObject(i).getString("author");

//                Toast.makeText(getContext(), "title:"+title, Toast.LENGTH_LONG).show();

                Item2 itemTrend = new Item2("titlex", "descriptionx", "creatorx", "viewsx", "ratingx", "codex");
                itemListTrend.add(itemTrend);


                mAdapterTrend.notifyDataSetChanged();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




}