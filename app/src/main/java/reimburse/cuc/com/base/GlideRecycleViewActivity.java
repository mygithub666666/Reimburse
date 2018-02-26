package reimburse.cuc.com.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import reimburse.cuc.com.adaptor.GlideRecyclerViewAdaptor;
import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2017/8/19.
 */
public class GlideRecycleViewActivity extends Activity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_glide_recycleview);
        recyclerView = (RecyclerView) findViewById(R.id.rv_glide);
        init();
    }

    private void init(){
        GlideRecyclerViewAdaptor glideRecyclerViewAdaptor = new GlideRecyclerViewAdaptor(this);
        recyclerView.setAdapter(glideRecyclerViewAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }
}
