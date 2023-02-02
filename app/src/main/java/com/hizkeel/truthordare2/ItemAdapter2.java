package com.hizkeel.truthordare2;

import static android.widget.Toast.LENGTH_SHORT;
import static com.hizkeel.truthordare2.JsonQuestion.createJson;
import static com.sttudiom.assessment.model.JsonQuestion.createJson;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sttudiom.assessment.view.TestDescription;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemAdapter2 extends RecyclerView.Adapter<ItemAdapter2.MyViewHolder> {

    private static String PICKED_CODE_  ;
    ProgressDialog progressDialog;
    Context context;

    public static String author, category, description, title, duration, noOfQuestion, language;

    private List<Item2> itemList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, author, category, code;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.item_title);
            author = (TextView) view.findViewById(R.id.item_author);
            category = (TextView) view.findViewById(R.id.item_category);
            code = (TextView) view.findViewById(R.id.item_code);
        }
    }


    public ItemAdapter2(List<Item2> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ass2, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item2 item = itemList.get(position);
        holder.title.setText(item.getTitle());
        holder.category.setText(item.getCategory());
        holder.author.setText(item.getAuthor());
        holder.code.setText(item.getCode());
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
//                Toast.makeText(context, item.toString(), Toast.LENGTH_SHORT).show();


//                Toast.makeText(context, item.getCode().toString(), Toast.LENGTH_LONG).show();

                AssItemAdapter.PICKED_CODE = item.getCode().toString();

                loadTest();





            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public void progProc(){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Fetching Data"); // Setting Title
        progressDialog.setIcon(R.drawable.icon);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

    }


    public void loadTest(){


        String code = AssItemAdapter.PICKED_CODE;



        progProc();




        String URL = "http://api.question.hizkeel.com/v1/api.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("true")) {
                                progressDialog.dismiss();
//                                Toast.makeText(context, "successfull", LENGTH_SHORT).show();
                                AssItemAdapter.author = jsonObject.getString("author");
                                AssItemAdapter.title = jsonObject.getString("title");
                                AssItemAdapter.duration = jsonObject.getString("duration");
                                AssItemAdapter.category = jsonObject.getString("category");
                                AssItemAdapter.description = jsonObject.getString("description");
                                AssItemAdapter.language = jsonObject.getString("language");
                                AssItemAdapter.noOfQuestion = jsonObject.getString("no_of_questions");
//                                test_id =  jsonObject.getString("id");


                                // create a json file and store the response in local memory
                                boolean x = createJson(context, "qupp", response);
                                if(x){
//                                    go();
                                    Intent intent = new Intent(context, VibeInfo.class);

                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                    context.startActivity(intent);
//                                    Toast.makeText(context, "Test Code is Valid", LENGTH_SHORT).show();
                                } else {
//                                    Toast.makeText(context, "Test Code is not Valid", LENGTH_SHORT).show();
                                }

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(context, jsonObject.getString("message"), LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //what to do if it encounter error
                        progressDialog.dismiss();
                        // loadTest();
                        Toast.makeText(context, "Seems your network is bad. Kindly restart app if this persist"+error, LENGTH_SHORT).show();

                        Log.i("testtxyz", "oops ");

                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("command", "get_test");
                params.put("code", code);





                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(stringRequest);



    }

}

