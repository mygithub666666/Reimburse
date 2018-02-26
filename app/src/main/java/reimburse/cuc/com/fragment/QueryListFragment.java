package reimburse.cuc.com.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import reimburse.cuc.com.base.BaseFragment;
import reimburse.cuc.com.bean.BaoXiaoDan;
import reimburse.cuc.com.bean.Constants;
import reimburse.cuc.com.reimburse.BarChartActivity;
import reimburse.cuc.com.reimburse.LauncherActivity;
import reimburse.cuc.com.reimburse.LineChartActivity;
import reimburse.cuc.com.reimburse.PieChartActivity;
import reimburse.cuc.com.reimburse.R;
import reimburse.cuc.com.util.StreamTools;

/**
 * Created by hp1 on 2017/8/6.
 */
public class QueryListFragment extends BaseFragment {

    private static final String TAG = QueryListFragment.class.getSimpleName();
    //private LineChart lineChart;

    private Button time_stat_button;
    private Button project_stat_button;
    private Button expenseStat;


    @Override
    protected View initView() {



        View view = View.inflate(mContext, R.layout.data_chart, null);
        time_stat_button = (Button) view.findViewById(R.id.timeStat);
        project_stat_button = (Button) view.findViewById(R.id.proojectStat);
        expenseStat = (Button) view.findViewById(R.id.expenseStat);

        time_stat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),LineChartActivity.class);
                startActivity(intent);
            }
        });

        project_stat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"project_stat",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(),BarChartActivity.class);
                startActivity(intent);
            }
        });
        expenseStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"expenseStat",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(),PieChartActivity.class);
                startActivity(intent);
            }
        });
        return  view;


    }

    @Override
    protected void initData() {
        Log.e(TAG, "审核状态Fragment的数据初始化1111111111111111111111111111111111111");
        super.initData();
    }





}
