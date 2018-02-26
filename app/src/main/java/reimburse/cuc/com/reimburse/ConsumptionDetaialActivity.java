package reimburse.cuc.com.reimburse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLDisplay;

import reimburse.cuc.com.adaptor.GlideRecyclerViewAdaptor;

/**
 * Created by hp1 on 2017/8/20.
 */
public class ConsumptionDetaialActivity extends Activity {

    private EditText xiaofeicontent;
    private EditText xiaofeiriqi;
    private EditText xiaofeijine;
    private RecyclerView xiaofei_detail_recyclerView;
    private List<String> picUris;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baoxiaodan_xiaofeidetail);
        xiaofeicontent = (EditText) findViewById(R.id.editText_content1);
        xiaofeiriqi = (EditText) findViewById(R.id.editText_date1);
        xiaofeijine = (EditText) findViewById(R.id.et_money1);
        xiaofei_detail_recyclerView = (RecyclerView) findViewById(R.id.reciclerview_form1);

        picUris = new ArrayList<String>();
        Intent intent = getIntent();

        /**
         intent.putExtra("content",consumption.getContent());
         intent.putExtra("date",consumption.getDate());
         intent.putExtra("money",consumption.getMoney());
         intent.putExtra("picURIS",consumption.getPicUris());
         */
        String content = intent.getStringExtra("content");
        String date = intent.getStringExtra("date");
        String money = intent.getStringExtra("money");
        String picURIS = intent.getStringExtra("picURIS");
        Log.e("----汇总后的URI-------",picURIS);
        String[] split = picURIS.split(",");
        for (String str : split){
            picUris.add(str);
            Log.e("=======从Listview中得到的uri",str);
        }
        xiaofeicontent.setText(content);
        xiaofeiriqi.setText(date);
        xiaofeijine.setText(money);

        xiaofei_detail_recyclerView.setAdapter(new GlideRecyclerViewAdaptor(ConsumptionDetaialActivity.this, picUris));
        xiaofei_detail_recyclerView.setLayoutManager(new GridLayoutManager(ConsumptionDetaialActivity.this,3,GridLayoutManager.VERTICAL,false));
    }
}
