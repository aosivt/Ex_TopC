package com.example.auoschepkov.ex_topc.dev_cl.SpreadSheet;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TabHost;

import com.example.auoschepkov.ex_topc.Main_form;
import com.example.auoschepkov.ex_topc.dev_cl.Singleton.Customization_Button;
import com.example.auoschepkov.ex_topc.dev_cl.Singleton.Singleton_gv;
import com.example.auoschepkov.ex_topc.var_cl.Var_class;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import tc.ex_dr_v8.R;

/**
 * Created by au.oschepkov on 13.03.2015.
 */
public class SpreadSheetAdapter extends BaseAdapter {

    public Context context;
    GridView result_gv;
    private List<Singleton_gv> idnames;

    public SpreadSheetAdapter(Context context, List<Singleton_gv> products) {
        this.context = context;
        this.idnames = products;
    }

    @Override
    public int getCount() {
        return idnames.size();
    }

    @Override
    public Object getItem(int position) {
        return idnames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Customization_Button button;

        if (convertView == null) {
            button = new Customization_Button(context);
            button.setText(idnames.get(position).getName());
        } else {
            button = (Customization_Button) convertView;
        }
        button.setId(position);
        button.setTextSize(50);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((Main_form) context).func_result_fragment();

                TabHost tab = (TabHost) ((TabHost) v.getParent().getParent().getParent().getParent().getParent()).findViewById(R.id.tabHost2);
                if (tab.getCurrentTab() == 0) { //транспортное средство

                    Var_class.result_array[3] = ((Singleton_gv) getItem(position)).getName();
                    Var_class.result_array_send[1] = ((Singleton_gv) getItem(position)).getidname();
                    Var_class.result_array_showing[1] = ((Singleton_gv) getItem(position)).getName();
                } else if (tab.getCurrentTab() == 1) { //номенклатура

                    Var_class.result_array[5] = ((Singleton_gv) getItem(position)).getName();
                    Var_class.result_array_send[2] = ((Singleton_gv) getItem(position)).getidname();
                    Var_class.result_array_showing[2] = ((Singleton_gv) getItem(position)).getName();
                } else if (tab.getCurrentTab() == 2) { //склады

                    Var_class.result_array[7] = ((Singleton_gv) getItem(position)).getName();
                    Var_class.result_array_send[3] = ((Singleton_gv) getItem(position)).getidname();
                    Var_class.result_array_showing[3] = ((Singleton_gv) getItem(position)).getidname();
                }

                tab.setCurrentTab(tab.getCurrentTab() + 1);

            }
        });

        long start = System.currentTimeMillis();
        Date timeDiff = new Date(start);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        Var_class.result_array_send[4] = android.text.format.DateFormat.getDateFormat(context).format(new Date());
        Var_class.result_array_send[5] = timeFormat.format(timeDiff);

        Var_class.result_array_showing[4] = android.text.format.DateFormat.getDateFormat(context).format(new Date());
        Var_class.result_array_showing[5] = timeFormat.format(timeDiff);


        Var_class.result_array[9] = android.text.format.DateFormat.getDateFormat(context).format(new Date());
        Var_class.result_array[11] = timeFormat.format(timeDiff);


        return button;
    }

}