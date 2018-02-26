package reimburse.cuc.com.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.List;

import reimburse.cuc.com.base.Consumption;
import reimburse.cuc.com.reimburse.R;
/**
 * Created by hp1 on 2017/8/26.
 */
public class TestRecyclerViewAdapter extends RecyclerView.Adapter<TestRecyclerViewAdapter.MyViewHolder> {

    private final Context context;
    private List<Consumption> datas;

    public TestRecyclerViewAdapter(Context context, List<Consumption> datas) {
        this.context = context;
        this.datas = datas;
    }

    /**
     * 创建view和viewholder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_shop_cart,null);
        return new MyViewHolder(itemView);
    }

    /**
     * 绑定数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        Consumption consu = datas.get(position);
        holder.check.setChecked(consu.isSelected());
        holder.content.setText(consu.getContent());
        holder.date.setText(consu.getDate());
        holder.money.setText(consu.getMoney());
        holder.picurls.setText(consu.getPicUris());
        holder.picurls.setVisibility(View.INVISIBLE);
    }

    /**
     * 得到总条数
     * @return
     */
    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox check;
        TextView content;
        TextView date;
        TextView money;
        TextView picurls;
        public MyViewHolder(View itemView) {
            super(itemView);
            check = (CheckBox) itemView.findViewById(R.id.cb_expense_submit);
            content = (TextView) itemView.findViewById(R.id.tv_title_cart);
            date = (TextView) itemView.findViewById(R.id.tv_date_cart);
            money = (TextView) itemView.findViewById(R.id.tv_money_cart);
            picurls = (TextView) itemView.findViewById(R.id.tv_picuris_cart);
        }
    }
}
