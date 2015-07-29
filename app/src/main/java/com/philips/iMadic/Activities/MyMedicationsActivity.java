package com.philips.iMadic.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.philips.iMadic.Adapters.MedicationAdapter;
import com.philips.iMadic.Models.Medicine;
import com.philips.iMadic.R;
import com.philips.iMadic.Services.InteractionsService;
import com.philips.iMadic.Services.MedicinesCache;
import com.philips.iMadic.Services.MyMedicationsService;
import com.philips.iMadic.iMadicApp;

import java.util.ArrayList;
import java.util.Date;


public class MyMedicationsActivity extends ActionBarActivity {

    MyMedicationsService _service;
    ArrayList<Medicine> _myList;
    MedicationAdapter _mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        _service  = new MyMedicationsService();

        _myList = _service.GetAllMyMedicines();

        InitList();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add:
                openAdd();
                return true;
            case R.id.action_check:
                CheckInteractions();
                return true;
            case R.id.action_settings:
                //openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

   // @Override
   /* protected void onListItemClick(ListView l, View v, int position, long id) {
        String item =  l.getItemAtPosition(position); // (String) this.getListAdapter().getItem(position);
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
    }*/

    final int ADD_MEDICATION_ACTIVITY = 0;

    private void openAdd()
    {
        //Intent intent = new Intent(this, AddMedicationActivity.class);
       // Intent intent = new Intent(this, AndroidBarcodeQrExample.class);
      //  startActivityForResult(intent, ADD_MEDICATION_ACTIVITY);

        //AndroidBarcodeQrExample s = new AndroidBarcodeQrExample();

        scanBar();
    }

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";


    //product barcode mode
    public void scanBar() {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
           // showDialog(this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    //on ActivityResult method

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (ADD_MEDICATION_ACTIVITY) : {
                if (resultCode == Activity.RESULT_OK) {
                    String contents = data.getStringExtra("SCAN_RESULT");
                    String format = data.getStringExtra("SCAN_RESULT_FORMAT");

                    // Check if the code exists - add it to user scan
                    if(MedicinesCache.getInstance().IsMedicineExists(contents)){

                        Medicine m = MedicinesCache.getInstance().GetByCode(contents);

                        _service.AddMedication(m);
                        _mAdapter.notifyDataSetChanged();
                    }

                }
                break;
            }
        }
    }

    private void CheckInteractions()
    {
        String[] arr = new String[_myList.size()];

        for (int i = 0 ; i < _myList.size(); ++i) {
            arr[i] = _myList.get(i)._RXCUI;

        }

        InteractionsService s = new InteractionsService();
        iMadicApp.Interactions =  s.GetInteractions(arr);

        if (iMadicApp.Interactions.size() == 0)
        {
            ShowOkDialog();

        }
        else {
            Intent intent = new Intent(this, HandleResultActivity.class);
            startActivity(intent);
        }
    }

    private void ShowOkDialog() {
            new AlertDialog.Builder(this)
                    .setTitle("Interactions check")
                    .setMessage("No interactions found")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
// continue with delete
                        }
                    })
                    .setIcon(R.drawable.check_icon)
                    .show();

    }

    private void InitList() {
        // Get ListView object from xml
        ListView listView = (ListView) findViewById(R.id.list);

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        //       android.R.layout.simple_list_item_1, android.R.id.text1, values);
        _mAdapter = new MedicationAdapter(this, _myList, _service);
        //setListAdapter(adapter);

        // Assign adapter to ListView
        listView.setAdapter(_mAdapter);

        // ListView listView = getListView();
        // Create a ListView-specific touch listener. ListViews are given special treatment because
        // by default they handle touches for their list items... i.e. they're in charge of drawing
        // the pressed state (the list selector), handling list item clicks, etc.
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        listView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    Medicine m = _mAdapter.getItem(position);
                                    _service.DeleteMedication(m);
                                    _mAdapter.remove(m);
                                }
                                _mAdapter.notifyDataSetChanged();
                            }
                        });
        listView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        listView.setOnScrollListener(touchListener.makeScrollListener());


    }



}


