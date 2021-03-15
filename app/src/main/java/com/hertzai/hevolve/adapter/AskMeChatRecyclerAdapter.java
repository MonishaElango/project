package com.hertzai.hevolve.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hertzai.hevolve.R;
import com.hertzai.hevolve.gson.AskMessageItem;

import java.util.ArrayList;
import java.util.Collections;



public class AskMeChatRecyclerAdapter extends RecyclerView.Adapter<AskMeChatRecyclerAdapter.ViewHolder> {


    private int lastPosition = -1;
    private Context contextAsk;
    private ArrayList<AskMessageItem> messageList1;


    public AskMeChatRecyclerAdapter(Context contextAsk, ArrayList<AskMessageItem> messageList1) {
        this.contextAsk = contextAsk;
        this.messageList1 = messageList1;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contextAsk).inflate(R.layout.item_askme_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (messageList1.get(position).isReply) {

            holder.replyLL.setVisibility(View.VISIBLE);
            holder.messageLL.setVisibility(View.GONE);
            holder.repyTV.setText(TextUtils.join("\n ", Collections.singleton(messageList1.get(position).message)));
            setAnimation1(holder.itemView, position);


        } else {
            holder.replyLL.setVisibility(View.GONE);
            holder.messageLL.setVisibility(View.VISIBLE);
            holder.messageTV.setText(TextUtils.join("\n ", Collections.singleton(messageList1.get(position).message)));
            setAnimation(holder.itemView, position);

        }

    }

    private void setAnimation(View viewToAnimate, int position) {

        if (position > lastPosition) {
            viewToAnimate.startAnimation(AnimationUtils.loadAnimation(contextAsk, R.anim.anim_right));
            lastPosition = position;
        }
    }

    private void setAnimation1(View viewToAnimate, int position) {
        if (position > lastPosition) {
            viewToAnimate.startAnimation(AnimationUtils.loadAnimation(contextAsk, R.anim.anim_left));
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return messageList1.size();
    }

    public ArrayList<AskMessageItem> getResults() {
        return messageList1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView messageTV, repyTV;
        private CardView messageCV, replyCV;
        private LinearLayout replyLL, messageLL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageCV = itemView.findViewById(R.id.AskCardView);
            messageTV = itemView.findViewById(R.id.AskMessage_tv);
            replyLL = itemView.findViewById(R.id.AskReplyLL);
            repyTV = itemView.findViewById(R.id.AskReply_tv);
            messageLL = itemView.findViewById(R.id.AskMessageLL);
            replyCV = itemView.findViewById(R.id.AskCardView1);


            messageCV.setBackground(ContextCompat.getDrawable(contextAsk, R.drawable.bg_2));
            replyCV.setBackground(ContextCompat.getDrawable(contextAsk, R.drawable.bg_g));

        }
    }


}