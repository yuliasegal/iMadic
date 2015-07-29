package com.philips.iMadic.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;

import com.philips.iMadic.R;


public class AddMedicationActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_add_medication);
      //  Intent intent = getIntent();
      //  String message = intent.getStringExtra(EXTRA_MESSAGE);

        // Create the text view
    //    TextView textView = new TextView(this);
    //    textView.setTextSize(40);
     //   textView.setText(message);

        // Set the text view as the activity layout
      //  setContentView(textView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String medicationId = editText.getText().toString();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("medicationId", medicationId);
// TODO Add extras or a data URI to this intent as appropriate.
        setResult(Activity.RESULT_OK, resultIntent);
        finish();

    /*    // Do something in response to button
        Intent intent = new Intent(this, AddMedicationActivity.class);

        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);*/
    }
}
