package reimburse.cuc.com.reimburse;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import reimburse.cuc.com.adaptor.MyRecyclerViewAdapter;

/**
 * Created by hp1 on 2017/8/20.
 */
public class RecyclerViewActivity extends Activity implements View.OnClickListener {
    private String[] uris = new String[]{
            "/storage/emulated/0/SohuDownload/a04d8bd826e28e02.JPEG",
            "/storage/emulated/0/ImageSelector/CameraImage/ImageSelector_20170819_214104.JPEG",
            "/storage/emulated/0/ImageSelector/CameraImage/ImageSelector_20170819_214005.JPEG",
            "/storage/emulated/0/myphoto.jpg"
    };
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private Button btn_add;
    private Button btn_del;
    private Button btn_list;
    private Button btn_grid;
    private Button btn_flow;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recyclerview);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_del = (Button) findViewById(R.id.btn_delete);
        btn_list = (Button) findViewById(R.id.btn_list);
        btn_grid = (Button) findViewById(R.id.btn_grid);
        btn_flow = (Button) findViewById(R.id.btn_flow);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_test);

        myRecyclerViewAdapter = new MyRecyclerViewAdapter(RecyclerViewActivity.this,uris);
        recyclerView.setAdapter(myRecyclerViewAdapter);


        btn_add.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_list.setOnClickListener(this);
        btn_grid.setOnClickListener(this);
        btn_flow.setOnClickListener(this);


        //设置RecyclerView的适配器


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                break;
            case R.id.btn_delete:
                break;
            case R.id.btn_list:
                recyclerView.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this,LinearLayoutManager.VERTICAL,false));
                break;
            case R.id.btn_grid:
                break;
            case R.id.btn_flow:
                break;
        }

    }
}
