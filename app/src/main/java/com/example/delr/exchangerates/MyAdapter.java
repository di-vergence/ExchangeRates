package com.example.delr.exchangerates;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by delr on 07.03.16.
 */
class MyAdapter extends ArrayAdapter<Item> {
    private final Context context;
    private  ArrayList<Item> values = new ArrayList<>();

    MyAdapter(Context context, ArrayList<Item> values) {
        super(context, R.layout.my_list_item, values);
        this.context = context;
        this.values = values;
    }
    public void setValues(ArrayList<Item> values){
        this.values.clear();
        this.values.addAll(values);
        this.notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.my_list_item, parent, false);
        if (values != null) {
            TextView t1 = (TextView) rowView.findViewById(R.id.textView1);
            TextView t2 = (TextView) rowView.findViewById(R.id.textView2);
            TextView t3 = (TextView) rowView.findViewById(R.id.textView3);
//            if (t1 != null)
            t1.setText(values.get(position).getCode());
            t2.setText(values.get(position).getBid());
            t3.setText(values.get(position).getAst());
        }
        return rowView;
    }
}
