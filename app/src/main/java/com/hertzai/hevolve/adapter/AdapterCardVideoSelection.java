package com.hertzai.hevolve.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hertzai.hevolve.R;
import com.hertzai.hevolve.views.HomeActivity;
import com.hertzai.hevolve.models.appModel.MainModel;

import java.util.ArrayList;

public class AdapterCardVideoSelection extends RecyclerView.Adapter<AdapterCardVideoSelection.viewModel> {
    ArrayList<MainModel> maincardModels;
    RecyclerViewClickListener listener;
   Context context;
    public AdapterCardVideoSelection(HomeActivity context, ArrayList<MainModel> maincardModels, RecyclerViewClickListener listener)
    {
        this.context= context;
        this.maincardModels=maincardModels;
        this.listener = listener;
    }



    @NonNull
    @Override
    public AdapterCardVideoSelection.viewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_item_maincard,parent,false);

        return new AdapterCardVideoSelection.viewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCardVideoSelection.viewModel holder, int position) {
        holder.imageView.setImageResource(maincardModels.get(position).getTeacherImages());

    }

    @Override
    public int getItemCount() {
        return maincardModels.size();
    }


    public interface RecyclerViewClickListener
    {
        void onClick(int position);


    }

    public class viewModel extends RecyclerView.ViewHolder{
        ImageView imageView;

        public viewModel(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listener.onClick(getAdapterPosition());
                }
            });
        }
    }
}
