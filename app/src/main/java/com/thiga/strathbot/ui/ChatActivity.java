package com.thiga.strathbot.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.gson.JsonObject;
import com.thiga.strathbot.R;
import com.thiga.strathbot.api.ApiService;
import com.thiga.strathbot.api.ApiUrl;
import com.thiga.strathbot.helper.MessageListAdapter;
import com.thiga.strathbot.helper.SharedPrefManager;
import com.thiga.strathbot.models.Message;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "Chat";
    private static final String ONPAUSE_TAG = "OnPause";
    private static final String ONDESTROY_TAG = "OnDestroy";

    private Context mContext = ChatActivity.this;
    private View recyclerHolder;
    private RecyclerView messageRecycler;
    private MessageListAdapter messageListAdapter;
    private List<Message> messages = new ArrayList<>();

    private MaterialButton sendButton;
    private EditText editTextChatbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerHolder = findViewById(R.id.recyclerview_message_list_holder);
        sendButton = findViewById(R.id.button_chatbox_send);
        editTextChatbox = findViewById(R.id.edit_text_chatbox);

        messages.add(new Message("hello", null, "right"));
        messages.add(new Message(null, "Hi! I'm Stratbot", "left"));
        messages.add(new Message(null, null, "NOOOOO!", "center"));
        messages.add(new Message(null, null, null, "https://media.giphy.com/media/iI54Q04tvKQA3Nv8O7/giphy.gif", "left_gif"));
        messageListAdapter = new MessageListAdapter(this, messages);
        messageRecycler = findViewById(R.id.recyclerview_message_list);
        messageRecycler.setHasFixedSize(true);
        messageRecycler.setLayoutManager(new LinearLayoutManager(this));
        messageListAdapter = new MessageListAdapter(mContext, messages);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(String.valueOf(editTextChatbox.getText()));
            }
        });

        messageRecycler.setAdapter(messageListAdapter);
        messageRecycler.scrollToPosition(messages.size()-1);

        messageListAdapter.setOnItemClickListener(new MessageListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Message message = messages.get(position);
                if(message.getOptionMessage().equals("escalate")){
                    escalate();
                }
                else if(message.getOptionMessage().equals("no thanks")) {
                    // Remove escalate button
                    messages.remove(messages.size()-1);
                    //Remove no thanks button
                    messages.remove(messages.size()-1);

                    // Present the option that user chose as a user message
                    messages.add(new Message("No thanks", null, "right"));

                    messages.add(new Message(null, "Alright. Anything else I can help you with? Feel free to ask.", "left"));
                    messageRecycler.setAdapter(messageListAdapter);
                    messageRecycler.scrollToPosition(messages.size()-1);
                }
//                messageListAdapter.notifyItemChanged(position);
            }
        });

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

//        messageRecycler.setAdapter(messageListAdapter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(ONPAUSE_TAG, "WHY ARE YOU PAUSING?");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(ONDESTROY_TAG, "WHY ARE YOU DESTROYING?");
    }

//    public void onClick(View view) {
//        sendMessage(String.valueOf(editTextChatbox.getText()));
////        receiveMessage();
//    }

    // How will I incorporate User ID into message?
    private void sendMessage(String messageText){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_message", messageText);

        // Opted not to use user_id for now
//        jsonObject.addProperty("user_id", SharedPrefManager.getInstance(this).getUser().getId());

        // Using username instead
        jsonObject.addProperty("user_username", SharedPrefManager.getInstance(this).getUser().getUsername());
//        Log.d("wtf", jsonObject.toString());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<JsonObject> call = service.sendMessage(
                jsonObject
        );

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG+" post response", response.body().toString());
                String userMessage = String.valueOf(editTextChatbox.getText());
                messages.add(new Message(userMessage, null, "right"));
                messageRecycler.setAdapter(messageListAdapter);
                messageRecycler.scrollToPosition(messages.size()-1);
                editTextChatbox.setText("");
                receiveMessage(response.body());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(mContext, "Error connecting to StrathBot. Try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

    // Old receiveMessage method. It worked but it meant I had to make an additional GET request which seemed inefficient

//    private void receiveMessage(){
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(ApiUrl.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        ApiService service = retrofit.create(ApiService.class);
//
//        Call<JsonObject> call = service.getMessage();
//
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
////                String botMessage = response.body().get("bot_message").getAsString();
////                String gifUrl = response.body().get("gif_url").getAsString();
//
//                Log.d(TAG, response.body().toString());
////                messages.add(new Message(null, botMessage, "left"));
////                if(response.body().get("gif_url") != null) {
////                    String gif_url = response.body().get("gif_url").getAsString();
////                    messages.add(new Message(null, null, null, gif_url, "left"));
////                }
////                Message last_message = messages.get(messages.size()-1);
////                if(last_message.getBotMessage().equals("Sorry I didn't quite get that. Kindly try again.")){
////                    messages.add(new Message( null,"Would you like to escalate this query to an administrator?", "left"));
////                    messages.add(new Message(null, null, "escalate", "center"));
////                    messages.add(new Message(null, null, "no thanks", "center"));
////                }
//                messageRecycler.setAdapter(messageListAdapter);
//                editTextChatbox.setText("");
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Toast.makeText(mContext, "Error connecting to StrathBot. Try again.", Toast.LENGTH_LONG).show();
//            }
//        });
////        call.enqueue(new Callback<Message>() {
////            @Override
////            public void onResponse(Call<Message> call, Response<Message> response) {
////                Log.d(TAG, response.body().getBotMessage());
////            }
////
////            @Override
////            public void onFailure(Call<Message> call, Throwable t) {
////
////            }
////        });
//    }

    private void receiveMessage(JsonObject jsonObject){
        String botMessage = jsonObject.get("bot_message").getAsString();
        messages.add(new Message(null, botMessage, "left"));
        if(jsonObject.has("gif_url")){
            String gifUrl = jsonObject.get("gif_url").getAsString();
            messages.add(new Message(null, null, null, gifUrl, "left_gif"));
        }
        Message last_message = messages.get(messages.size()-1);
        try {
            if (last_message.getBotMessage().equals("Sorry I didn't quite get that. Kindly try again.")) {
                messages.add(new Message(null, "Would you like to escalate this query to an administrator?", "left"));
                messages.add(new Message(null, null, "escalate", "center"));
                messages.add(new Message(null, null, "no thanks", "center"));
            }
        } catch (NullPointerException npe){}
        messageRecycler.setAdapter(messageListAdapter);
        messageRecycler.scrollToPosition(messages.size()-1);
        editTextChatbox.setText("");
    }

    private void escalate(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_username", SharedPrefManager.getInstance(this).getUser().getUsername());
        jsonObject.addProperty("escalated", true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<ResponseBody> call = service.escalate(
                jsonObject
        );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Remove escalate button
                messages.remove(messages.size()-1);
                //Remove no thanks button
                messages.remove(messages.size()-1);

                messages.add(new Message("Escalate", null, "right"));

                messages.add(
                        new Message(null, "Your query has been escalated to an administrator and will be answered soon.", "left")
                );
                messageRecycler.setAdapter(messageListAdapter);
                messageRecycler.scrollToPosition(messages.size()-1);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, "Error connecting to StrathBot. Try again.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
