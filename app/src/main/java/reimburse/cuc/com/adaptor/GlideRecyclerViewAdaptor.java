package reimburse.cuc.com.adaptor;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import reimburse.cuc.com.reimburse.R;

/**
 * Created by hp1 on 2017/8/19.
 */
public class GlideRecyclerViewAdaptor extends RecyclerView.Adapter<GlideRecyclerViewAdaptor.ViewHolder> {

    private Context mContext;
    private List<String> uris;
   /* private String[] uris = new String[]{
            "/storage/emulated/0/SohuDownload/a04d8bd826e28e02.JPEG",
            "/storage/emulated/0/ImageSelector/CameraImage/ImageSelector_20170819_214104.JPEG",
            "/storage/emulated/0/ImageSelector/CameraImage/ImageSelector_20170819_214005.JPEG",
            "/storage/emulated/0/myphoto.jpg"
    };*/


    public GlideRecyclerViewAdaptor(Context mContext,List<String> uris) {
        this.mContext = mContext;
        this.uris = uris;
    }

    public GlideRecyclerViewAdaptor() {

    }

    public GlideRecyclerViewAdaptor(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = View.inflate(mContext, R.layout.item_glide_recyclerview, null);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, mContext.getResources().getDisplayMetrics());
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200f, mContext.getResources().getDisplayMetrics());

        Glide.with(mContext)
                .load(Uri.fromFile(new File(uris.get(position))))
                .placeholder(R.mipmap.ic_launcher) //占位图
                .error(R.mipmap.ic_launcher)  //出错的占位图
                .override(60,60) //图片显示的分辨率 ，像素值 可以转化为DP再设置
                .centerCrop()
                .fitCenter()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return uris == null ? 0 : uris.size();
    }

    static  class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_glider_recyclerview);
        }
    }
}
