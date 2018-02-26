package reimburse.cuc.com.reimburse;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.yongchun.library.view.ImageSelectorActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import reimburse.cuc.com.adaptor.GlideRecyclerViewAdaptor;
import reimburse.cuc.com.base.Consumption;
import reimburse.cuc.com.bean.Cost;
import reimburse.cuc.com.util.DatabaseHelper;
import reimburse.cuc.com.util.DividerListItemDecoration;

/**
 * Created by hp1 on 2017/8/19.
 */
public class FormActivity extends Activity {
    private EditText et_content;
    private EditText et_date;
    private EditText et_money;
    private Button btn_save;
    private Button btn_picSelect;
    private ImageView imgview_select;

    private StringBuilder picUris;


    private String content;
    private String date;
    private String money;

    private RecyclerView recyclerView;
    private List<String> uris;

    private DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_activity);
        et_content = (EditText) findViewById(R.id.editText_content);
        et_date = (EditText) findViewById(R.id.editText_date);
        //setDate(et_date);
        defaultDate(et_date);
        et_money = (EditText) findViewById(R.id.et_money);
        btn_save = (Button) findViewById(R.id.save_zhangdan);
        btn_picSelect = (Button) findViewById(R.id.picSelect);
        recyclerView = (RecyclerView) findViewById(R.id.reciclerview_form);
        //initCostData();

        picUris = new StringBuilder();
        uris = new ArrayList<String>();
        btn_picSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelectorActivity.start(FormActivity.this, 6, ImageSelectorActivity.MODE_MULTIPLE, true, true, true);
            }
        });

        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  save(v);
                  startActivity(new Intent(getApplicationContext(), NoteActivity.class));
                  finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE){
            ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
            for (String str : images){
                uris.add(str);
                if(images.indexOf(str) == images.size()-1){
                    picUris.append(str);
                }else{
                    picUris.append(str+",");
                }
                //picUris.append(str);
                Log.e("--------选择的图片uri---", str);
            }

            Log.e("--------图片uri汇总---",picUris.toString());
            if(uris.size() > 0){
                recyclerView.setAdapter(new GlideRecyclerViewAdaptor(FormActivity.this, uris));
                //recyclerView.setLayoutManager(new LinearLayoutManager(FormActivity.this,LinearLayoutManager.HORIZONTAL,false));
                recyclerView.setLayoutManager(new GridLayoutManager(FormActivity.this,3,GridLayoutManager.VERTICAL,false));
                recyclerView.addItemDecoration(new DividerListItemDecoration(FormActivity.this,DividerListItemDecoration.VERTICAL_LIST));
            }
        }
    }

    public void save(View view){
        content = et_content.getText().toString();
        date = et_date.getText().toString();
        money = et_money.getText().toString();
        Consumption consumption = new Consumption(content,date,money,picUris.toString());
        databaseHelper.insertConsumption(consumption);

    }

    public void defaultDate(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        et_date.setText(year+"-"+(month+1)+"-"+day);
    }
    public void setDate(View view){
        SetDateDialog sdd = new SetDateDialog();
        sdd.show(getFragmentManager(),"datepicker");
    }
    //创建日期选择对话框
    @SuppressLint("ValidFragment")
    class SetDateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(getActivity(),this,year,month,day);
            return  dpd;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            et_date.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
        }
    }

    private void initCostData() {
        databaseHelper.deleteAllData();
    }
}
