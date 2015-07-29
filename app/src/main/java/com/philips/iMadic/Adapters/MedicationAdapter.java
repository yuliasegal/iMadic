package com.philips.iMadic.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.philips.iMadic.Models.Medicine;
import com.philips.iMadic.R;
import com.philips.iMadic.Services.MyMedicationsService;

import java.util.ArrayList;

/**
 * Created by 310210574 on 2015-07-28.
 */
public class MedicationAdapter extends ArrayAdapter<Medicine> {
    private final Context context;
    private final ArrayList<Medicine> values;

    MyMedicationsService _service;

    //, View.OnTouchListener ontouch,View.OnClickListener onclick
    public MedicationAdapter(Context context, ArrayList<Medicine> values, MyMedicationsService service) {
        super(context, R.layout.layout_medication, values);
        this.context = context;
        this.values = values;
        this._service = service;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_medication, parent, false);

        TextView tvName = (TextView) rowView.findViewById(R.id.medication_name);
        TextView tvDescription = (TextView) rowView.findViewById(R.id.medication_description);

        Medicine m = values.get(position);
        tvName.setText(m._name);
        tvDescription.setText(m._INGREDIENT) ;


        // Change the icon for Windows and iPhone

        return rowView;
    }
}