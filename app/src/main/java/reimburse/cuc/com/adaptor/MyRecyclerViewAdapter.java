package reimburse.cuc.com.adaptor;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;

import reimburse.cuc.com.reimburse.R;
import reimburse.cuc.com.reimburse.RecyclerViewActivity;

/**
 * Created by hp1 on 2017/8/20.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private String[] uris;
    public MyRecyclerViewAdapter(Context context, String[] uris) {
        this.context = context;
        this.uris = uris;
    }

    /**
     * 相当于ListV
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_recyclerview,null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String uri = uris[position];
        holder.iv.setImageURI(Uri.fromFile(new File(uri)));
    }

    /**
     * 得到总条数
     * @return
     */
    @Override
    public int getItemCount() {
        return uris.length;
    }

    class  MyViewHolder extends  RecyclerView.ViewHolder{

        ImageView iv;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_icon);
        }


    }
}
