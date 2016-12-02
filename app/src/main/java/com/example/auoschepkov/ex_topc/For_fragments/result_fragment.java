package com.example.auoschepkov.ex_topc.For_fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.auoschepkov.ex_topc.BD_conect.Select_SQLite;
import com.example.auoschepkov.ex_topc.Main_form;
import com.example.auoschepkov.ex_topc.interface_cl.messageBox;
import com.example.auoschepkov.ex_topc.var_cl.Var_class;

import tc.ex_dr_v8.R;

/**
 * Created by au.oschepkov on 16.03.2015.
 */
public class result_fragment extends Fragment {
    com.example.auoschepkov.ex_topc.interface_cl.messageBox mes;
    TextView textMsg;
    Button bt;
    GridView ex;
    ToggleButton TB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.result_frag, null);
        ex = (GridView) view.findViewById(R.id.result_array);
        ex.setNumColumns(6);
        ex.setColumnWidth(80);
        ex.setVerticalSpacing(5);
        ex.setHorizontalSpacing(5);

        String data = "Экскаватор: " + Var_class.result_array[1] + "\n" +
                "Вы подключены как: " + Var_class.listviewitem_ex1[0];




        textMsg = (TextView) view.findViewById(R.id.ex_title);
        textMsg.setText(data);
        Linkify.addLinks(textMsg, Linkify.ALL);

        textMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageBox mb = new messageBox();
                Var_class.call_transact = false;
                //(new ShowTimer()).showTimer(500000);
                //(new ShowTimer()).showTimer(ShowTimer.SECONDS_TO_COUNTDOWN * ShowTimer.MILLIS_PER_SECOND);
                mb.enter_dialog(Var_class.MContext).show();

            }
        });

        TB = (ToggleButton) view.findViewById(R.id.TB_light);

        TB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean on = ((ToggleButton) v).isChecked();
                if (on) {((Main_form) (new Var_class()).getMContext()).func_day_on();}
                else {((Main_form) (new Var_class()).getMContext()).func_day_off();}

                ((Main_form) (new Var_class()).getMContext()).func_result_frag_restart();
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {

        } else {
            ex.setAdapter((new Select_SQLite()).getAdapterForResultFragment());
        }

        Button bt_send = (Button) view.findViewById(R.id.send_result);
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Main_form) (new Var_class()).getMContext()).func_result_fragment_new();
            }
        });

        return view;
    }

    public void replace_gv_result() {
        ex.setAdapter((new Select_SQLite()).getAdapterForResultFragment());
    }

}
