package com.example.chatbot.viewmodel;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.chatbot.model.BotModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private Context context;
    private List<BotModel> chatlist = new ArrayList<>();
    private MutableLiveData<List<BotModel>> chatLiveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<List<BotModel>> getMutableData() {
        if (chatLiveData == null) {
            chatLiveData = new MutableLiveData<>();
            loadChatHistory();
        }
        return chatLiveData;
    }

    public void setUserMutableLiveData(BotModel botModel) {
        chatlist.add(botModel);
        chatLiveData.setValue(chatlist);
    }

    public void loadChatHistory() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("ChatHistory", null);
        Type type = new TypeToken<List<BotModel>>() {
        }.getType();
        chatlist = gson.fromJson(json, type);
        if (chatlist == null) {
            chatlist = new ArrayList<>();
        }
        chatLiveData.setValue(chatlist);

    }

    public void saveChatHistory() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(chatlist);
        editor.putString("ChatHistory", json);
        editor.apply();
    }

    public void sortList() {
        Collections.sort(chatlist, new Comparator<BotModel>() {
            DateFormat f = new SimpleDateFormat("dd-MM-yyyy  h:m a");

            @Override
            public int compare(BotModel lhs, BotModel rhs) {
                try {
                    return f.parse(lhs.getCreationdate()).compareTo(f.parse(rhs.getCreationdate()));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
        Collections.reverse(chatlist);
    }


}
