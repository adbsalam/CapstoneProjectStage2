package com.salam.capstoneprojectstage2.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.salam.capstoneprojectstage2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class app_details_widget extends AppWidgetProvider {
    public static String UPDATE_ACTION = "ActionUpdateSinglenoteWidget";
    static String CASE_DETAILS = "";
    static String idofitem;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String caseLaw, String title) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_details_widget);
        views.setTextViewText(R.id.appwidget_text, title);
        views.setTextViewText(R.id.case_details_widget, caseLaw);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //GET CASE ID FOR QUERY
        super.onReceive(context, intent);
        String action = intent.getAction();
        Bundle extras = intent.getExtras();
        idofitem = extras.getString("id");
        if (action != null && action.equals(UPDATE_ACTION)) {
            // updateAppWidget(context, appWidgetManager, id , titleofrecipie);
            ShowItemsNames(context, idofitem);
        } else { super.onReceive(context, intent); }
    }
    //CASE DETAILS QUERY
    private void ShowItemsNames(final Context context, final String idtocheck) {


        Uri.Builder builder = new Uri.Builder();
        builder.scheme(context.getString(R.string.HTTP))
                .authority(context.getString(R.string.AUTHORITY))
                .appendPath("v1")
                .appendPath(context.getString(R.string.CASES_D))
                .appendPath(idtocheck);
        String URL2 = builder.build().toString();


        RequestQueue Video_Queue = Volley.newRequestQueue((context));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL2, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray casebodyOBJ = jsonObject.getJSONArray("citations");
                    JSONObject volumeOBJ = jsonObject.getJSONObject("volume");
                    JSONObject courtOBJ = jsonObject.getJSONObject("court");
                    JSONObject juriOBJ = jsonObject.getJSONObject("jurisdiction");
                    //STRING TO SET TEXTVIEW OF WIDGET THAT GETS DETAULS OF THE CASE
                    CASE_DETAILS = casebodyOBJ.getJSONObject(0).getString("type") + "  " + casebodyOBJ.getJSONObject(0).getString("cite") + "\n"+
                            jsonObject.getString("name_abbreviation") + "\nDecision Date:" + jsonObject.getString("decision_date")+"\n" +
                            courtOBJ.getString("name") + "(abr)" + courtOBJ.getString("name_abbreviation")+"\n" +
                            juriOBJ.getString("name") + " " + juriOBJ.getString("name_long");
                    final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    ComponentName name = new ComponentName(context, app_details_widget.class);
                    int[] appWidgetId = AppWidgetManager.getInstance(context).getAppWidgetIds(name);
                    final int N = appWidgetId.length;
                    if (N < 1)
                    {
                        return ;
                    }
                    else {
                        int id = appWidgetId[N-1];

                        updateAppWidget(context, appWidgetManager, id , CASE_DETAILS, jsonObject.getString("name_abbreviation"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Video_Queue.add(stringRequest);

    }








}

