package com.example.auoschepkov.ex_topc.Con_ax;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.auoschepkov.ex_topc.BD_conect.Delete_row_from_cache;
import com.example.auoschepkov.ex_topc.Main_form;
import com.example.auoschepkov.ex_topc.var_cl.Var_class;

/**
 * Created by au.oschepkov on 16.03.2015.
 */
public class AsyncCallWS extends AsyncTask<String, Void, Void> {

    public String[] restore_mas_st;
    //public Context context;
//public AlertDialog.Builder messega_pro;

    @Override
    protected Void doInBackground(String... params)
    {

        if  (Var_class.if_call == 1)
            {
            call_func_tra func_t = new call_func_tra();
            func_t.transact_all();
            //if_call=0;
            }
        if  (Var_class.if_call == 2)
            {
            call_func_tra ax_web = new call_func_tra();
            ///Var_class.result_array_send[0] = Var_class.result_array[1];
            //убрано в следствии того что в insert_ax передается вновь формированный массив result_array_send
            Var_class.ansver_qu = ax_web.func_insert_ax_web(Var_class.result_array_send_ax);
            }
        return null;

    }

    @Override
    protected void onPostExecute(Void result) {
        // ((MainActivity) context).setTitle("Данные приняты");

        if (Var_class.if_call == 1) {
            if (Var_class.success_select) {
                ((ProgressDialog) Var_class.message_for_connection_web).incrementProgressBy(100);
                Var_class.message_for_connection_web.dismiss();
                Var_class.if_call = 2;
                (new ShowTimer()).showTimer(ShowTimer.SECONDS_TO_COUNTDOWN * ShowTimer.MILLIS_PER_SECOND);

                new Delete_row_from_cache().func_del_all_tab_sessions(); ///удаление всех записей в таблице
                ((Main_form) (new Var_class()).getMContext()).internet_using();
                ((Main_form) (new Var_class()).getMContext()).showing_func();


            } else {
                //Var_class.message_for_connection_web.dismiss();
                ((ProgressDialog) Var_class.message_for_connection_web).incrementProgressBy(65);
                Toast.makeText(Var_class.MContext,
                        "Ошибка получения данных", Toast.LENGTH_LONG).show();
                ((Main_form) (new Var_class()).getMContext()).func_reboot_application();
            }

            //     ((MainActivity) context).setTitle("Экскаватор №"+MainActivity.result_array[0].toString());
        }
        if (Var_class.if_call == 2) {
            (new Insert_ax()).func_cach();
        /*    AlertDialog.Builder builder = new AlertDialog.Builder((new Var_class()).getMContext());
            builder.setTitle(Var_class.ansver_qu + Var_class.id_tab_cache)
                    .setMessage("Context Main_form получен!")
                    .setCancelable(false)
                    .setNegativeButton("ОК, иду на кухню",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();*/

            //call_func_tra ax_web = new call_func_tra();
            // ax_web.func_insert_ax_web(Var_class.result_array_send);
            if (!Var_class.success_select) {
                //Var_class.count_call_insert = Var_class.count_call_insert + 1;
                if (Var_class.count_call_insert > 5 && Var_class.count_call_insert < 100) {
                    Toast.makeText(Var_class.MContext,
                            Var_class.error_connect, Toast.LENGTH_LONG).show();
                } else {
                    //Var_class.count_call_insert = 6;
                }
            } else {
                Var_class.count_call_insert = 0;
            }


        }
        // new String_mas().func_mes("Данные приняты",context);
    }

    @Override
    protected void onPreExecute() {

        //((MainActivity) context).setTitle("Обмен данными...");
        //new String_mas().func_mes("Обмен данными...", context);
    }

    @Override
    protected void onProgressUpdate(Void... values) {

    }


}

