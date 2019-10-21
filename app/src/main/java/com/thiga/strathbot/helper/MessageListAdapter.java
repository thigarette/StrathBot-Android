package com.thiga.strathbot.helper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thiga.strathbot.R;
import com.thiga.strathbot.models.Message;

import org.w3c.dom.Text;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter {

    private static final String TAG = "where";

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private List<Message> messageList;
    private Context mContext;

    public MessageListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public MessageListAdapter(Context mContext, List<Message> messageList) {
        this.messageList = messageList;
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        if(message.getSide().equals("right"))
            return VIEW_TYPE_MESSAGE_SENT;
        else
            return VIEW_TYPE_MESSAGE_RECEIVED;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if(viewType == VIEW_TYPE_MESSAGE_SENT){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_user, parent, false);
            Log.d(TAG ,"Yo where this message at?");
            return new UserMessageHolder(view);
        }
        else if(viewType == VIEW_TYPE_MESSAGE_RECEIVED){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_bot, parent, false);
            Log.d(TAG ,"Yo where this message at?");
            return new BotMessageHolder(view);
        }
        Log.d(TAG, "That's what I thought");
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messageList.get(position);
        switch (holder.getItemViewType()){
            case VIEW_TYPE_MESSAGE_SENT:
                ((UserMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((BotMessageHolder) holder).bind(message);
        }
    }

//    @Override
//    public void onBindViewHolder(@NonNull MessageListAdapter.ViewHolder holder, int position) {
//        Message message = messageList.get(position);
//        holder.textViewMessage.setBotMessage(message.getBotMessage());
//    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    private class UserMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        public UserMessageHolder(View view) {
            super(view);

            messageText = view.findViewById(R.id.text_message_body);
        }

        void bind(Message message){
            messageText.setText(message.getUserMessage());
        }
    }

    private class BotMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        public BotMessageHolder(View view) {
            super(view);

            messageText = view.findViewById(R.id.text_message_body);
        }

        void bind(Message message){
            messageText.setText(message.getBotMessage());
        }

    }

//    public class ViewHolder extends RecyclerView.ViewHolder{
//
//        public TextView textViewMessage;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            textViewMessage = itemView.findViewById(R.id.text_message_body);
//        }
//    }
}
