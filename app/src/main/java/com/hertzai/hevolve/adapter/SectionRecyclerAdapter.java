package com.hertzai.hevolve.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hertzai.hevolve.R;
import com.hertzai.hevolve.gson.BottomTab;

import java.util.ArrayList;

public class SectionRecyclerAdapter  extends RecyclerView.Adapter<SectionRecyclerAdapter.ViewHolder>{

    private Context context;
    private ArrayList<BottomTab> list;
    private SectionInterface mInterface;

    public SectionRecyclerAdapter(Context context,ArrayList<BottomTab> list,SectionInterface mInterface) {
        this.context = context;
        this.list = list;
        this.mInterface = mInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.item_section,parent,false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.image.setImageResource(list.get(position).getImage());
        holder.title.setText(list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface SectionInterface{
        void onItemClicked(Integer in);
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private TextView title;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.mainImage);
            title = itemView.findViewById(R.id.mainTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                mInterface.onItemClicked(getAdapterPosition());
                                            }
                                        }
            );
        }
    }

}
