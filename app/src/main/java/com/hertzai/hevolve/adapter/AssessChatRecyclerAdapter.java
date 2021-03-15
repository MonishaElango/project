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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hertzai.hevolve.R;
import com.hertzai.hevolve.models.appModel.QuestionNo;

import java.util.ArrayList;

public class AssessChatRecyclerAdapter extends RecyclerView.Adapter<AssessChatRecyclerAdapter.ViewHolder> {

    private int lastPosition = -1;

    private Context context;
    private ArrayList<QuestionNo> messageList ;
    private ChatInterface mListener;


    public AssessChatRecyclerAdapter(Context context, ArrayList<QuestionNo> messageList, ChatInterface mListener) {
        this.context = context;
        this.messageList = messageList;
        this.mListener = mListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.item_assess_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(messageList.get(position).isReply){

            holder.replyLL.setVisibility(View.VISIBLE);
            holder.messageLL.setVisibility(View.GONE);
            holder.questionTV.setVisibility(View.VISIBLE);


//            for(int i = 0 ; i< messageList.get(position).message.size() ; i++){
//                holder.repyTV.setText(messageList.get(position).message);
//            }
            if(messageList.get(position).question.equals("0")){
                holder.questionTV.setVisibility(View.GONE);
            }
            else
            {
              holder.questionTV.setVisibility(View.VISIBLE);

                holder.questionTV.setText(messageList.get(position).question);

            }
            holder.repyTV.setText(TextUtils.join("\n " ,  messageList.get(position).message));
            setAnimation1(holder.itemView , position);






            if(messageList.get(position).getOptionList() != null){

                    holder.optionRV.setVisibility(View.VISIBLE);


                    holder.mAdapter = new OptionsAdapter(context, messageList.get(position).getOptionList(), new OptionsAdapter.OptionInterface() {
                        @Override
                        public void onOptionCLicked(String option) {
                            mListener.onOptionClicked(option);
                        }
                    });

                holder.optionRV.setLayoutManager(new GridLayoutManager(context,3));
                holder.optionRV.setAdapter(holder.mAdapter);
            }

        }else {

            holder.replyLL.setVisibility(View.GONE);
            holder.questionTV.setVisibility(View.GONE);
            holder.optionRV.setVisibility(View.GONE);
            holder.messageLL.setVisibility(View.VISIBLE);
            holder.messageTV.setText(TextUtils.join("\n " , messageList.get(position).message));
            setAnimation(holder.itemView,position);

        }


    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            viewToAnimate.startAnimation(AnimationUtils.loadAnimation(context,R.anim.anim_right));
            lastPosition = position;
        }

    }
    private void setAnimation1(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            viewToAnimate.startAnimation(AnimationUtils.loadAnimation(context,R.anim.anim_left));
            lastPosition = position;
        }

    }

    @Override
    public int getItemCount() {
            return messageList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView messageTV ,repyTV,questionTV;
        private CardView messageCV ,replyCV;
        private RecyclerView optionRV;
        private LinearLayout replyLL ,messageLL;
        private OptionsAdapter mAdapter;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageCV = itemView.findViewById(R.id.cardView);
            messageTV = itemView.findViewById(R.id.message_tv);
            optionRV = itemView.findViewById(R.id.options_rv);
            questionTV = itemView.findViewById(R.id.question_no_tv);
            replyLL = itemView.findViewById(R.id.replyLL);
            repyTV = itemView.findViewById(R.id.reply_tv);
            messageLL = itemView.findViewById(R.id.messageLL);
            replyCV =itemView.findViewById(R.id.cardView1);


            messageCV.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_2));
            replyCV.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_g));


    }

    }

    public interface ChatInterface{
        void onOptionClicked(String option);
    }
}

