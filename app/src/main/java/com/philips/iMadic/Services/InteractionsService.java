package com.philips.iMadic.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.philips.iMadic.Models.Interaction;
import com.philips.iMadic.Models.InteractionResult;
import com.philips.iMadic.Models.Medicine;

public class InteractionsService  {

    /** Called when the activity is first created. */
    public List<Interaction> GetInteractions(String[] drugs) {
        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //final EditText status = (EditText)findViewById(R.id.statussInt);


       //drugs = new String[]{"207106","152923","656659"};

        String input = callRXnAV(drugs);
        List<Interaction> interactions = new ArrayList<Interaction>();
// status.setText(input.toString());
        try {
            JSONObject json = null;
            try {
                json = new JSONObject(input);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }

            if (!json.has("fullInteractionTypeGroup"))
                return interactions;

            Object fullInteractionTypeGroupObj = json.get("fullInteractionTypeGroup");

            if (fullInteractionTypeGroupObj != null) {
                JSONArray fullInteractionTypeGroup = (JSONArray)fullInteractionTypeGroupObj;
                JSONArray fullInteractionType = ((JSONObject) fullInteractionTypeGroup.get(0)).getJSONArray("fullInteractionType");

                // foreach fullInteractionType
                for (int index = 0; index < fullInteractionType.length(); index++) {
                    JSONObject firstInteraction = (JSONObject) fullInteractionType.get(index);
                    JSONArray interactionPair = firstInteraction.getJSONArray("interactionPair");
                    JSONArray interactionConcept = ((JSONObject) interactionPair.get(0)).getJSONArray("interactionConcept");
                    JSONObject minConceptItem1 = (JSONObject) ((JSONObject) interactionConcept.get(0)).get("minConceptItem");
                    String rxcui1 = minConceptItem1.getString("rxcui");
                    String name1 = minConceptItem1.getString("name");

                    JSONObject minConceptItem2 = (JSONObject) ((JSONObject) interactionConcept.get(1)).get("minConceptItem");
                    String rxcui2 = minConceptItem2.getString("rxcui");
                    String name2 = minConceptItem2.getString("name");

                    String description = ((JSONObject) interactionPair.get(0)).getString("description");

                    Interaction model = new Interaction();
                    model.MedicineAName = name1;
                    model.MedicineACode = rxcui1;
                    model.MedicineBName = name2;
                    model.MedicineBCode = rxcui2;
                    model.InteractionConfilct = description;

                    interactions.add(model);
                }

                //((JSONObject).get(2)).getJSONArray("interactionPair")

            }
            return interactions;
            //Log.i(RxnavActivity.class.getName(), json.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return interactions;

    }

    public String callRXnAV(String[] drugs ) {
        if (drugs == null || drugs.length==0) return "";
        StringBuilder drugsIDs = new StringBuilder();
        drugsIDs.append("https://rxnav.nlm.nih.gov/REST/interaction/list.json?rxcuis=");
        for (   int i = 0 ;i<drugs.length;i++ ){

            drugsIDs.append(drugs[i]);
            if (i<drugs.length-1)
            {drugsIDs.append("+");}
        }

        StringBuilder builder = new StringBuilder();
        HttpURLConnection http = null;
        String urlStr = drugsIDs.toString(); // "https://rxnav.nlm.nih.gov/REST/interaction/list.json?rxcuis=207106+152923+656659";

        URL url;
        HttpResponse response = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(urlStr));
            response = client.execute(request);

            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                System.out.println(builder.toString());
            } else {
                // Log.e(ParseJSON.class.toString(), "Failed to download file");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return   builder.toString();
    }

}