package com.thiga.strathbot.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.thiga.strathbot.models.ObjectId;
import com.thiga.strathbot.models.User;

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mContext;

    private static final String SHARED_PREF_NAME = "strathbot";

    private static final String KEY_USER_ID = "keyuserfirstid";
    private static final String KEY_USER_FIRST_NAME = "keyuserfirstname";
    private static final String KEY_USER_LAST_NAME = "keyuserlastname";
    private static final String KEY_USER_USERNAME = "keyuserusername";
    private static final String KEY_USER_EMAIL = "keyuseremail";
    private static final String KEY_USER_MOBILE_NUMBER = "keyusermobilenumber";

    private SharedPrefManager(Context context){
        mContext = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean login(User user){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String oid = gson.toJson(user.getId());
        editor.putString(KEY_USER_ID, oid);
        editor.putString(KEY_USER_FIRST_NAME, user.getFirstName());
        editor.putString(KEY_USER_LAST_NAME, user.getLastName());
        editor.putString(KEY_USER_USERNAME, user.getUsername());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.putString(KEY_USER_MOBILE_NUMBER, user.getMobileNumber());
        editor.apply();
        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USER_USERNAME,null)!=null)
            return true;
        return false;
    }

    public User getUser(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_USER_ID, null);
        ObjectId objectId = gson.fromJson(json, ObjectId.class);
        return new User(
                objectId,
                sharedPreferences.getString(KEY_USER_FIRST_NAME,null),
                sharedPreferences.getString(KEY_USER_LAST_NAME,null),
                sharedPreferences.getString(KEY_USER_USERNAME,null),
                sharedPreferences.getString(KEY_USER_EMAIL,null),
                sharedPreferences.getString(KEY_USER_MOBILE_NUMBER,null)
                );
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;

    }
}
