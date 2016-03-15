package com.example.delr.exchangerates;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {
    private ArrayList<Item> data = new ArrayList<>() ;
    private static final String TAG2 = "tag2";
    private static final String TAG1 = "tag1";
    private final String URL = "http://api.fixer.io/latest";
// Add the buttons
//    build

    private ProgressDialog progress;
    private ArrayList<Item> generateData(){
        ArrayList<Item> values = new ArrayList<>();
        values.add(new Item("USD", "1", "5"));
        values.add(new Item("RUB", "8", "3"));
        values.add(new Item("UIN", "23", "10"));
        values.add(new Item("LFD", "41", "5.0"));
        values.add(new Item("USD", "12", "50"));
        for(int i = 0; i < 100; i ++) {
            values.add(Item.next());
        }
        return values;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myscreen);
        final MyAdapter adapter = new MyAdapter(this, new ArrayList<Item>());
        Button button1 = (Button) findViewById(R.id.button);
        ListView lvMain = (ListView) findViewById(R.id.listView);
        final RequestQueue queue = Volley.newRequestQueue(this);

        final JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject rates = (JSONObject) response.get("rates");
                            Log.d("RATES", "JSON ratess =" + rates.toString());
                            Iterator<String> keys = rates.keys();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                String val = rates.getString(key);
                                data.add(new Item(key, val, val + 1));
                            }
                            adapter.setValues(data);
                            data.clear();
                            if (progress != null) {
                                progress.dismiss();
                                progress.hide();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        alertDialogBuilder.setTitle("TimeoutError");
                        alertDialogBuilder
                                .setMessage("Click yes to exit!")
                                .setCancelable(false)
                                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // if this button is clicked, close
                                        // current activity
                                        MainActivity.this.finish();
                                    }
                                })
                                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        dialog.cancel();
                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                        progress.dismiss();
                        progress.hide();

                        error.printStackTrace();
                    }
                });
        jsonReq.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonReq);
        progress = ProgressDialog.show(this, "Prog", "waiting...                                      ");
        progress.setTitle("Please Wait!!");
        progress.setMessage("Wait!!");
        progress.setCancelable(true);
        progress.setIndeterminate(true);
        lvMain.setAdapter(adapter);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress = ProgressDialog.show(v.getContext(), "Prog", "waiting...                                      ");
                progress.setTitle("Please Wait!!");
                progress.setMessage("Wait!!");
                progress.setCancelable(true);
                progress.setIndeterminate(true);
                queue.add(jsonReq);
            }

        });

    }
}
