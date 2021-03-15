package com.hertzai.hevolve.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hertzai.hevolve.R;
import com.hertzai.hevolve.views.GridViewActivity;
import com.hertzai.hevolve.models.appModel.MainModel;

import java.util.ArrayList;

public class MainAdapterGridView extends RecyclerView.Adapter<MainAdapterGridView.viewModel> {

    ArrayList<MainModel> mainModels;
    GridViewActivity context;
    MainRecyclerViewClickListener listener;


    public MainAdapterGridView(ArrayList<MainModel> mainModels, GridViewActivity context, MainRecyclerViewClickListener listener) {
        this.mainModels = mainModels;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MainAdapterGridView.viewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

          View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_item,parent,false);

       return new viewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapterGridView.viewModel holder, int position) {

        holder.imageView.setImageResource(mainModels.get(position).getTeacherImages());

    }


    public interface  MainRecyclerViewClickListener
    {
        void onClick(int position);
    }

    @Override
    public int getItemCount() {
        return mainModels.size();
    }

    public class viewModel extends RecyclerView.ViewHolder {
        ImageView imageView;

        public viewModel(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onClick(getAdapterPosition());
                    }
                });


        }
    }
}
