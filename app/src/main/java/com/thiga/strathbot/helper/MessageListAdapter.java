package com.thiga.strathbot.helper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.thiga.strathbot.R;
import com.thiga.strathbot.models.Message;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter {

    private static final String TAG = "where";

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private static final int VIEW_TYPE_OPTION_BUTTON = 3;
    private static final int VIEW_TYPE_GIF_IMAGE = 4;

    private List<Message> messageList;
    private Context mContext;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

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
        else if(message.getSide().equals("left"))
            return VIEW_TYPE_MESSAGE_RECEIVED;
        else if(message.getSide().equals("center"))
            return VIEW_TYPE_OPTION_BUTTON;
        else
            return VIEW_TYPE_GIF_IMAGE;
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
        else if(viewType == VIEW_TYPE_OPTION_BUTTON){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_option_button, parent, false);
            return new OptionButtonHolder(view, mListener);
        }
        else if(viewType == VIEW_TYPE_GIF_IMAGE){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_gif, parent, false);
            return new GifHolder(view);
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
                break;
            case VIEW_TYPE_OPTION_BUTTON:
                ((OptionButtonHolder) holder).bind(message);
                break;
            case VIEW_TYPE_GIF_IMAGE:
                ((GifHolder) holder).bind(message);
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
        TextView messageText, timeText;

        public UserMessageHolder(View view) {
            super(view);

            messageText = view.findViewById(R.id.text_message_body);
            timeText = view.findViewById(R.id.text_message_time);
        }

        void bind(Message message){
            messageText.setText(message.getUserMessage());
            DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
            timeText.setText(dateFormat.format(message.getCurrentTime()));
        }
    }

    private class BotMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;
        public BotMessageHolder(View view) {
            super(view);

            messageText = view.findViewById(R.id.text_message_body);
            timeText = view.findViewById(R.id.text_message_time);
        }

        void bind(Message message){
            messageText.setText(message.getBotMessage());
            DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
            timeText.setText(dateFormat.format(message.getCurrentTime()));
        }

    }

    private class OptionButtonHolder extends RecyclerView.ViewHolder{
        MaterialButton messageText;
        public OptionButtonHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            messageText = itemView.findViewById(R.id.text_message_body);

            Log.d("mListener", mListener.toString());

            messageText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

        void bind(Message message){
            messageText.setText(message.getOptionMessage());
        }
    }

    private class GifHolder extends RecyclerView.ViewHolder{

        ImageView gif;
        public GifHolder(View itemView) {
            super(itemView);

            gif = itemView.findViewById(R.id.gif);
        }
        void bind(Message message){
            Glide
                    .with(mContext)
                    .load(message.getGifUrl())
                    .into(gif);
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
