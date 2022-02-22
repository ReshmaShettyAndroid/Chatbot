package com.example.chatbot.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbot.R;
import com.example.chatbot.databinding.RowChatHistoryBinding;
import com.example.chatbot.model.Message;

import java.util.List;

public class ChatHistoryAdapter extends RecyclerView.Adapter<ChatHistoryAdapter.ChatHistoryHolder> {

    private List<Message> messageList;
    private RowChatHistoryBinding rowChatHistoryBinding;
    private Context context;

    public ChatHistoryAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ChatHistoryAdapter.ChatHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        rowChatHistoryBinding = RowChatHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ChatHistoryAdapter.ChatHistoryHolder(rowChatHistoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHistoryAdapter.ChatHistoryHolder holder, int position) {
        Message message = messageList.get(position);

        if (message.getSender().equalsIgnoreCase(context.getString(R.string.user_txt))) {
            //show sendertextview and set data
            holder.rowChatHistoryBinding.txtSendMsg.setText(message.getMessage());
            holder.rowChatHistoryBinding.txtSendDate.setText(message.getMessdate());
            holder.rowChatHistoryBinding.LytSenderRow.setVisibility(View.VISIBLE);
            holder.rowChatHistoryBinding.LytReceiverRow.setVisibility(View.GONE);
        } else {
            //show receiver textview and set data
            holder.rowChatHistoryBinding.txtReceiverMsg.setText(message.getMessage());
            holder.rowChatHistoryBinding.txtReceiverDate.setText(message.getMessdate());
            holder.rowChatHistoryBinding.LytSenderRow.setVisibility(View.GONE);
            holder.rowChatHistoryBinding.LytReceiverRow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class ChatHistoryHolder extends RecyclerView.ViewHolder {
        RowChatHistoryBinding rowChatHistoryBinding;

        public ChatHistoryHolder(@NonNull RowChatHistoryBinding binding) {
            super(binding.getRoot());
            rowChatHistoryBinding = binding;
        }
    }
}

