package reimburse.cuc.com.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import reimburse.cuc.com.base.Consumption;
import reimburse.cuc.com.bean.Caigou;
import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2017/8/26.
 */
public class CaigouExpenseSubmitAdapter extends RecyclerView.Adapter<CaigouExpenseSubmitAdapter.CaigouViewHolder>{

    private List<Caigou> caigouList;
    private Context context;
    public CaigouExpenseSubmitAdapter() {

    }

    public CaigouExpenseSubmitAdapter(Context context,List<Caigou> caigouList) {
        this.context = context;
        this.caigouList = caigouList;
    }

    @Override
    public CaigouViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context,R.layout.recycleview_item_xiafei,null);
        return new CaigouViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CaigouViewHolder holder, int position) {
        Caigou consu = caigouList.get(position);
        holder.check.setChecked(consu.isSelected());
        holder.content.setText(consu.getType());
        holder.date.setText(consu.getCaigou_riqi());
        holder.money.setText(consu.getCaigou_jine());
        holder.picurls.setText(consu.getCaigou_fapiao_urls());
        holder.picurls.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return caigouList.size();
    }

    class CaigouViewHolder extends RecyclerView.ViewHolder{

        CheckBox check;
        TextView content;
        TextView date;
        TextView money;
        TextView picurls;
        public CaigouViewHolder(View itemView) {
            super(itemView);
            check = (CheckBox) itemView.findViewById(R.id.cb_expense_submit);
            content = (TextView) itemView.findViewById(R.id.tv_title_cart);
            date = (TextView) itemView.findViewById(R.id.tv_date_cart);
            money = (TextView) itemView.findViewById(R.id.tv_money_cart);
            picurls = (TextView) itemView.findViewById(R.id.tv_picuris_cart);
        }

    }


}
