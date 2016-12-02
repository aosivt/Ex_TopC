
package com.example.auoschepkov.ex_topc.For_fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.example.auoschepkov.ex_topc.dev_cl.Singleton.Customization_Singleton;
import com.example.auoschepkov.ex_topc.dev_cl.Singleton.SingletonAdapter;
import com.example.auoschepkov.ex_topc.var_cl.Var_class;

import tc.ex_dr_v8.R;

/**
 * Created by au.oschepkov on 13.03.2015.
 */
public class authorization_fragment extends Fragment {

    public final static String KEY_MSG_1 = "FRAGMENT1_MSG";
    public final static String KEY_MSG_2 = "FRAGMENT2_MSG";
    public final static String KEY_MSG_3 = "FRAGMENT3_MSG";
    public static FragmentManager myFragmentManager;
    public FrameLayout container;
    public authorization_fragment myFragment1;
    com.example.auoschepkov.ex_topc.interface_cl.messageBox mes;
    TextView textMsg;
    Button bt;
    GridView ex;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.authorization_frag, null);
        ex = (GridView) view.findViewById(R.id.exa);
        ex.setAdapter(new SingletonAdapter((new Var_class()).getMContext(),
                (new Customization_Singleton().setArray_
                        ((new Var_class()).get_listviewitem_ex2(), (new Var_class()).get_listviewitem_ex2()))));

        ex.setNumColumns(5);

        return view;
    }

}
