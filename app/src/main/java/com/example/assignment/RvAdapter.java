package com.example.assignment;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.assignment.DataModel;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<DataModel> dataModelArrayList;
    private  Context ctx ;

    public RvAdapter(Context ctx, ArrayList<DataModel> dataModelArrayList){
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public RvAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.rv_one, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RvAdapter.MyViewHolder holder, int position) {


        String url = dataModelArrayList.get(position).getImgURL();
        //security reasons
        String Url = url.replace("http","https");
        Picasso.get().load(Url).into(holder.iv);
        holder.title.setText(dataModelArrayList.get(position).getTitle());
        holder.description.setText(dataModelArrayList.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView description, title, city;
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);

            description = (TextView) itemView.findViewById(R.id.country);
            title = (TextView) itemView.findViewById(R.id.name);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }

    }
}