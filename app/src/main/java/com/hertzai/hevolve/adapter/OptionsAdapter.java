package com.hertzai.hevolve.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hertzai.hevolve.R;

import java.util.ArrayList;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.ViewHolder>{

    private Context context;
    private ArrayList<String> messageList ;
    private OptionInterface mListener;


    public OptionsAdapter(Context context, ArrayList<String> optionList , OptionInterface mListener) {
        this.context = context;
        this.messageList = optionList;
        this.mListener = mListener;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.item_suggestion,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.optionTV.setText(messageList.get(position));
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    interface  OptionInterface{
        void onOptionCLicked(String option);

    }
    public class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView optionTV ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            optionTV = itemView.findViewById(R.id.option_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onOptionCLicked(messageList.get(getAdapterPosition()));
                }
            });
        }
    }
}