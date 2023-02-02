package com.hizkeel.truthordare2;

import static android.widget.Toast.LENGTH_SHORT;

import static com.hizkeel.truthordare2.JsonQuestion.createJson;
import static com.hizkeel.truthordare2.JsonQuestion.readJson;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends AppCompatActivity implements Validator.ValidationListener{

    ProgressDialog progressDialog;

    BaseApplication ba;



    private Validator validator;


    @NotEmpty
    @Email
    EditText etEmail;

    @Password
    EditText etPassword;



    String  email, id, password ;

    public static String USER_ID, FIRST_NAME, LAST_NAME;

    public static SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //initialization of elements
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);



        //validation of data.
        validator = new Validator(this);
        validator.setValidationListener(this);

        sp = getSharedPreferences("login",MODE_PRIVATE);

        if(sp.getBoolean("logged",false) ){


            login2();
        }


        ImageView imageView= (ImageView)findViewById(R.id.action_bar_back);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                Toast.makeText(getApplicationContext(),"backward Button is clicked",Toast.LENGTH_LONG).show();

            }
        });


    }






    public boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


    @Override
    public void onValidationSucceeded() {
        //Toast.makeText(this, "Form validated!", Toast.LENGTH_SHORT).show();
        login();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void btnLogin(View v){

        validator.validate();


    }

    public void btnLogin2(View v){

        loadAssessmentNew();




    }


    public void gotoSignUp(View v){

        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);

    }

    public void forgotPassword(View v){

        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);

    }



    public void go(){

        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }


    public void progProc(){
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("Processing"); // Setting Title
        progressDialog.setIcon(R.drawable.icon);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
    }



    private void login(){

        email = etEmail.getText().toString();
        password = etPassword.getText().toString();



        progProc();
//        progressON();

        String URL = "http://api.truthdare.hizkeel.com/v1/api.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("true")) {
                                progressDialog.dismiss();

                                loadVibesNew();

                                sp.edit().putString("Semail", etEmail.getText().toString().trim()).apply();
                                sp.edit().putString("Spassword", etPassword.getText().toString().trim()).apply();



                                sp.edit().putBoolean("logged", true).apply();


//                                Toast.makeText(StudentDetails.this, "successfull", LENGTH_SHORT).show();




                                JSONObject datao = jsonObject.getJSONObject("data");
                                try {

                                    USER_ID = datao.getString("user_id");
//                                    Toast.makeText(Login.this, "us_id"+USER_ID, LENGTH_SHORT).show();

                                    FIRST_NAME = datao.getString("first_name");
                                    LAST_NAME = datao.getString("last_name");
                                    String email = datao.getString("email");
                                    String state = datao.getString("state");
                                    String country = datao.getString("country");

                                    com.hizkeel.truthordare2.UserData userData = new com.hizkeel.truthordare2.UserData(getApplicationContext());


                                    userData.storeData(FIRST_NAME, LAST_NAME, email, country, state );




                                    Toast.makeText(Login.this, "Welcome: "+userData.getLastname().toString(), LENGTH_SHORT).show();



                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }

//                                go();


                            } else {
                                progressDialog.dismiss();
//                                progressOFF();
                                Toast.makeText(Login.this, jsonObject.getString("message"), LENGTH_SHORT).show();
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
//                        progressOFF();
                        Toast.makeText(Login.this, "Seems your network is bad. Kindly restart app if this persist"+error, LENGTH_SHORT).show();
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("command", "sign_up");
                params.put("email", email);
                params.put("password", password);



                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        requestQueue.add(stringRequest);




    }



    private void login2(){

        etEmail.setText(sp.getString("Semail",""));
        etPassword.setText(sp.getString("Spassword",""));

        //post to backend.
        progProc();
        String URL = "http://api.truthdare.hizkeel.com/v1/api.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("true")) {

                                sp.edit().putBoolean("logged", true).apply();

                                loadVibesNew();

                                JSONObject datao = jsonObject.getJSONObject("data");
                                try {
                                    USER_ID = datao.getString("user_id");

                                    FIRST_NAME = datao.getString("first_name");
                                    LAST_NAME = datao.getString("last_name");
                                    String email = datao.getString("email");
                                    String state = datao.getString("state");
                                    String country = datao.getString("country");

                                    com.hizkeel.truthordare2.UserData userData = new com.hizkeel.truthordare2.UserData(getApplicationContext());
                                    userData.storeData(FIRST_NAME, LAST_NAME, email, country, state );


                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }

//                                go();


                            } else {

                                progressDialog.dismiss();
                                Toast.makeText(Login.this, jsonObject.getString("message"), LENGTH_SHORT).show();
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
                        Toast.makeText(Login.this, "Seems your network is bad. Kindly restart app if this persist"+error, LENGTH_SHORT).show();
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("command", "sign_up");
                params.put("email", sp.getString("Semail","") );
                params.put("password", sp.getString("Spassword",""));



                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        requestQueue.add(stringRequest);


    }


    public void progressON() {
//        BaseApplication.getInstance().progressON(Login.this, "rtjhghjnull");
        ba = new BaseApplication();
        ba.progressON(this, null);
    }

    public void progressON(String message) {
//        BaseApplication.getInstance().progressON(this, message);
        ba = new BaseApplication();
        ba.progressON(this, message);
    }

    public void progressOFF() {
        ba.progressOFF();
    }

    public void loadVibesNew(){



        String URL = "http://api.truthdare.hizkeel.com/v1/api.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("true")) {
//                                progressDialog.dismiss();
//                                Toast.makeText(getContext(), "successfullyyy"+response.toString(), LENGTH_SHORT).show();


                                // create a json file and store the response in local memory
                                boolean x = createJson(getApplicationContext(), "new_list", response);

                                String iop = readJson(getApplicationContext(), "new_list");

//                                Toast.makeText(getApplicationContext(), "x"+ iop, Toast.LENGTH_LONG).show();

                                loadVibesTrend();

//                                Toast.makeText(getContext(), "x:" + x, Toast.LENGTH_LONG).show();


                            } else {
//                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), LENGTH_SHORT).show();
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

                        Toast.makeText(getApplicationContext(), "Seems your network is bad. Kindly restart app if this persist"+error, LENGTH_SHORT).show();
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("command", "get_assessment_type");
                params.put("type", "new");





                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);



    }

    public void loadVibesTrend(){

//        progProc();

        String URL = "http://api.truthdare.hizkeel.com/v1/api.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("true")) {
                                progressDialog.dismiss();
//                                Toast.makeText(getContext(), "successfullyyy"+response.toString(), LENGTH_SHORT).show();


                                // create a json file and store the response in local memory
                                boolean x = createJson(getApplicationContext(), "trend_list", response);


                                go();
//                                Toast.makeText(getContext(), "x:" + x, Toast.LENGTH_LONG).show();

                                if(x){
//                                    updateTrendList();

//                                    Toast.makeText(getContext(), "yes", Toast.LENGTH_LONG).show();
                                } else {
//                                    Toast.makeText(getContext(), "no", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), LENGTH_SHORT).show();
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

                        Toast.makeText(getApplicationContext(), "Seems your network is bad. Kindly restart app if this persist"+error, LENGTH_SHORT).show();
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("command", "get_assessment_type");
                params.put("type", "trend");





                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);



    }



    public void loadAssessmentNew(){

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
//                                Toast.makeText(getContext(), "successfullyyy"+response.toString(), LENGTH_SHORT).show();


                                // create a json file and store the response in local memory
                                boolean x = createJson(getApplicationContext(), "new_list", response);

                                String iop = readJson(getApplicationContext(), "new_list");

                                Toast.makeText(getApplicationContext(), "x"+ iop, Toast.LENGTH_LONG).show();

//                                loadAssessmentTrend();



                                    go();

//                                Toast.makeText(getContext(), "x:" + x, Toast.LENGTH_LONG).show();


                            } else {
//                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), "Seems your network is bad. Kindly restart app if this persist"+error, LENGTH_SHORT).show();
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("command", "get_assessment_type");
                params.put("type", "new");





                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);



    }


    public void loadAssessmentTrend(){

//        progProc();

        String URL = "http://api.question.hizkeel.com/v1/api.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("true")) {
                                progressOFF();
//                                Toast.makeText(getContext(), "successfullyyy"+response.toString(), LENGTH_SHORT).show();


                                // create a json file and store the response in local memory
                                boolean x = createJson(getApplicationContext(), "trend_list", response);


                                go();
//                                Toast.makeText(getContext(), "x:" + x, Toast.LENGTH_LONG).show();

                                if(x){
//                                    updateTrendList();

//                                    Toast.makeText(getContext(), "yes", Toast.LENGTH_LONG).show();
                                } else {
//                                    Toast.makeText(getContext(), "no", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                progressOFF();
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), LENGTH_SHORT).show();
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
                        progressOFF();
                        // loadTest();
                        Toast.makeText(getApplicationContext(), "Seems your network is bad. Kindly restart app if this persist"+error, LENGTH_SHORT).show();
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("command", "get_assessment_type");
                params.put("type", "trend");





                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);



    }




}