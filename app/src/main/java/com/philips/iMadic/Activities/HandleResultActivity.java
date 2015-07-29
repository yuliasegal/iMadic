package com.philips.iMadic.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.philips.iMadic.Adapters.ExpandableListAdapter;
import com.philips.iMadic.Models.Interaction;
import com.philips.iMadic.Models.InteractionResult;
import com.philips.iMadic.R;
import com.philips.iMadic.iMadicApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HandleResultActivity extends ActionBarActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    InteractionResult interactionResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactions);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        // Init headers & data children
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {


            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });

    }

    /*
    * Preparing the list data
    */
    private void prepareListData() {


        interactionResults = new InteractionResult();
        interactionResults.SetResults(iMadicApp.Interactions) ;

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        List<Interaction> list = interactionResults.GetInteractions();

        // Build header & child list
        for(int i =0; i < list.size();i++){
            listDataHeader.add(list.get(i).MedicineAName +  " + " + list.get(i).MedicineBName);
            List<String> children = new ArrayList<>();

            children.add(list.get(i).InteractionConfilct);
            listDataChild.put(list.get(i).MedicineAName +  " + " +  list.get(i).MedicineBName,children);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_handle_result, menu);
        return true;
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

        return super.onOptionsItemSelected(item);
    }

    class ResultsAdapter extends BaseAdapter {
        private List<Interaction> interactions;

        public ResultsAdapter(List<Interaction> interactions) {
            super();
            this.interactions = interactions;
        }

        public void setAdapterData(List<Interaction> newList) {
            this.interactions = newList;
        }

        @Override
        public int getCount() {
            return interactions.size();
        }

        @Override
        public Object getItem(int i) {
            return interactions.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = getLayoutInflater();
               // view = inflater.inflate(R.layout.product_list_row, null);
            }

            final Interaction currentProduct = interactions.get(i);
           /* TextView productDescription = (TextView)view.findViewById(R.id.nameTextView);
            String name = currentProduct.MedicineAName;
            String quantity = currentProduct.MedicineAName;
            productDescription.setText(quantity + "   " + name);
            ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
            imageView.setImageBitmap(currentProduct.image);

            ImageButton removeBtn = (ImageButton)view.findViewById(R.id.removeProductFromList);
           removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        com.example.ori.finalproject.ProductsListModel.getInstance().removeProductFromList(currentProduct.Code);
                        productsInList = com.example.ori.finalproject.ProductsListModel.getInstance().getProductListByGroupId(com.example.ori.finalproject.Globals.getInstance().getUserGroupId());
                        setAdapterData(productsInList);
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            */
            return view;
            }
        }
}
