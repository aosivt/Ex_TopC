package com.example.auoschepkov.ex_topc;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.auoschepkov.ex_topc.BD_conect.DatabaseHelper;
import com.example.auoschepkov.ex_topc.BD_conect.Insert_SQLite;
import com.example.auoschepkov.ex_topc.BD_conect.Select_SQLite;
import com.example.auoschepkov.ex_topc.Con_ax.AsyncCallWS;
import com.example.auoschepkov.ex_topc.For_fragments.authorization_fragment;
import com.example.auoschepkov.ex_topc.For_fragments.filling_fragment;
import com.example.auoschepkov.ex_topc.For_fragments.result_fragment;
import com.example.auoschepkov.ex_topc.interface_cl.messageBox;
import com.example.auoschepkov.ex_topc.var_cl.Var_class;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import tc.ex_dr_v8.R;

/**
 * Created by au.oschepkov on 11.03.2015.
 */
public class Main_form extends ActionBarActivity {
    public DatabaseHelper dbh;
    static FragmentManager myFragmentManager;
    FrameLayout container;
    authorization_fragment auth;
    filling_fragment fill_frag;
    result_fragment res_frag;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form1);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Var_class.MContext = Main_form.this;
        Var_class.break_insert_ax = false;


        messageBox mb = new messageBox();
        mb.enter_dialog(Main_form.this).show();

    }

    public void Start_func() {
        Var_class.message_for_connection_web = ((new messageBox()).createProgressDialog((new Var_class()).getMContext()));
        Var_class.message_for_connection_web.show();

        if (!isOnline()) {

            ((ProgressDialog) Var_class.message_for_connection_web).incrementProgressBy(10);
            Toast.makeText(getApplicationContext(),

                    "Нет соединения с интернетом!", Toast.LENGTH_LONG).show();

            Var_class.error_connect = "Нет подключение к интернету, возможные причины: \n Не включена 'ПЕРЕДАЧА ДАННЫХ', \n нет денежных средств на счету сим карты";
            func_for_local_session();
            //func_reboot_application();
            return;
        } else {
            ((ProgressDialog) Var_class.message_for_connection_web).incrementProgressBy(30);
            Toast.makeText(getApplicationContext(),
                    "Получено соединение с интернетом", Toast.LENGTH_LONG).show();
            AsyncCallWS task = new AsyncCallWS();
            Var_class.if_call = 1;

            if (Var_class.call_transact == false) {
                task.execute();
                Var_class.call_transact = true;

            }

        }
    }

    public void showing_func() {

        container = (FrameLayout) findViewById(R.id.container);
        myFragmentManager = getSupportFragmentManager();
        auth = new authorization_fragment();
        fill_frag = new filling_fragment();
        res_frag = new result_fragment();


        FragmentTransaction fragmentTransaction = myFragmentManager
                .beginTransaction();
        if (Var_class.result_array_send[0].toString().equals("")) {
            fragmentTransaction.add(R.id.container, auth, (new Var_class()).getAuthoFrag());
        } else {this.func_result_frag();}
        fragmentTransaction.commit();

    }

    public void func_result_fragment() {
        FragmentTransaction fragmentTransaction = myFragmentManager
                .beginTransaction();
        fragmentTransaction.remove(auth);
        fragmentTransaction.replace(R.id.container, fill_frag, (new Var_class()).getFillingFrag());
        fragmentTransaction.commit();
    }

    public void func_result_fragment_new() {
        FragmentTransaction fragmentTransaction = myFragmentManager
                .beginTransaction();
        fragmentTransaction.remove(fill_frag);
        fragmentTransaction.replace(R.id.container, fill_frag, (new Var_class()).getFillingFrag());
        fragmentTransaction.commit();
    }

    public void func_result_frag() {
        FragmentTransaction fragmentTransaction = myFragmentManager
                .beginTransaction();
        fragmentTransaction.remove(fill_frag);
        fragmentTransaction.replace(R.id.container, res_frag, (new Var_class()).getResultFrag());
        fragmentTransaction.commit();

    }

    public void func_result_frag_restart() {

        FragmentTransaction fragmentTransaction = myFragmentManager
                .beginTransaction();
        fragmentTransaction.remove(res_frag);
        fragmentTransaction.add(R.id.container, res_frag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public ArrayAdapter<String> func_result_send() {
        return new ArrayAdapter<String>(this, R.layout.item, R.id.tvText, (new Var_class()).getTest1());
    }

    public ArrayAdapter<String> func_result_insert() {
        return new ArrayAdapter<String>(this, R.layout.item, R.id.tvText, (new Var_class()).getresult_array());
    }

    public Context func_getCont() {
        return Main_form.this;
    }

    public FragmentManager func_return_FM() {
        return myFragmentManager;
    }

    public void func_reboot_application() {
        Var_class.call_transact = false;
        AlertDialog.Builder builder = new AlertDialog.Builder((new Var_class()).getMContext());
        builder.setTitle("Ошибка получения данных")
                .setMessage(Var_class.error_connect)
                .setCancelable(false)
                .setNegativeButton("Перезагрузить",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent i = getBaseContext().getPackageManager()
                                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    protected boolean isOnline() {
        String cs = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(cs);
        if (cm.getActiveNetworkInfo() == null) {
            return false;
        }
        return true;
    }


    public void func_day_on() {
        setTheme(R.style.AppTheme_Day);
    }

    public void func_day_off() {
        setTheme(R.style.AppTheme);
    }

    public void internet_using()
    {

        //(new Update_SQLite()).func_update_local_session_at_user();

        if ((new Select_SQLite().func_sel_users_for(
                func_hex_users(func_hex_users(Var_class.Item_connection_for_webservice[1]))))<1)
        {
            new Insert_SQLite().
                    func_insert_SQLite_conw(
                            func_hex_users(func_hex_users(
                                    new Var_class().getItem_connection_for_webservice()[1].toString())));
        }

        new Insert_SQLite().
                func_filling_tab_session(
                        Var_class.listviewitem_it1,
                        Var_class.listviewitem_it2, "session_it", "item1", "item2");

        new Insert_SQLite().
                func_filling_tab_session(
                        Var_class.listviewitem_sc1,
                        Var_class.listviewitem_sc2, "session_sc","sc1","sc2");

        new Insert_SQLite().
                func_filling_tab_session(
                        Var_class.listviewitem_ts1,
                        Var_class.listviewitem_ts2, "session_ts","ts1","ts2");

        new Insert_SQLite().
                func_filling_tab_session(
                        Var_class.listviewitem_ex1,
                        Var_class.listviewitem_ex2, "session_ex","ex1","ex2");
    }

    public void func_for_local_session()
    {

        Var_class.message_for_connection_web.dismiss();

        (new Select_SQLite()).func_filling_array();
        if (Var_class.listviewitem_ex1.length == 0 ||
                (new Select_SQLite().func_sel_users_for(func_hex_users(func_hex_users(Var_class.Item_connection_for_webservice[1]))))<1)
        {
            Toast.makeText(Var_class.MContext,
                    "Нет данных для работы в локальной сессии - необходимо перезагрузить приложение с подключением к интернету", Toast.LENGTH_LONG).show();
            func_reboot_application();
        }
        else
        {
            showing_func();
            Var_class.Item_connection_for_webservice[2] = "local session";
            //Var_class.Item_connection_for_webservice[1] = "local session";
            Var_class.listviewitem_ex1[0] = "Локальная сессия";
            Toast.makeText(Var_class.MContext,
                    "Подключена локальная сессия - данные будут отправленны только после перезагрузки приложения с подключением к интернет", Toast.LENGTH_LONG).show();
        }
    }
    private String func_hex_users(String user_name)
    {
        MessageDigest md = null;
        String mess = "";
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.reset();
            md.update(user_name.getBytes());
            byte[] sha256 = md.digest();
            String tmp = "";
            for (int i = 0; i < sha256.length; i++) {
                tmp = (Integer.toHexString(0xFF & sha256[i]));
                if (tmp.length() == 1) {
                    mess += "0" + tmp;
                } else {
                    mess += tmp;
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return mess;
    }

}