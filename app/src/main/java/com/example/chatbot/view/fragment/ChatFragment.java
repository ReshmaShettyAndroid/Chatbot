package com.example.chatbot.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.chatbot.R;
import com.example.chatbot.databinding.FragmentChatBinding;
import com.example.chatbot.model.BotModel;
import com.example.chatbot.model.Message;
import com.example.chatbot.utils.CommonUtils;
import com.example.chatbot.view.activity.MainActivity;
import com.example.chatbot.view.adapter.ChatHistoryAdapter;
import com.example.chatbot.viewmodel.MainViewModel;

public class ChatFragment extends Fragment {

    private FragmentChatBinding binding;
    private CommonUtils commonUtils;
    private ChatHistoryAdapter chatHistoryAdapter;
    private BotModel botModel;
    private String bot = "Bot";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentChatBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        commonUtils = new CommonUtils();
        botModel = (BotModel) getArguments().getSerializable("Botmodel");
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(botModel.getName());
        recyclerSetup();

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = binding.edtChatbox.getText().toString();
                binding.edtChatbox.setText("");
                updateMessageinView(msg);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavHostFragment.findNavController(ChatFragment.this).navigate(R.id.action_ChatFragment_to_BotFragment);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateMessageinView(String msg) {
        if (msg.toString().isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.please_enter_mssage_txt), Toast.LENGTH_SHORT).show();
        } else {
            Message message = new Message(getContext().getString(R.string.user_txt), msg.toString(), commonUtils.getDate());
            botModel.getMessages().add(message);
            botModel.setLastMessage(msg);
            botModel.setCreationdate(commonUtils.getDate());
            binding.recyclerChat.setVisibility(View.VISIBLE);
            binding.emptyChatLayout.setVisibility(View.GONE);
            if (botModel.getMessages().size() > 1) {
                chatHistoryAdapter.notifyDataSetChanged();
            } else {
                chatHistoryAdapter = new ChatHistoryAdapter(getContext(), botModel.getMessages());
                binding.recyclerChat.setAdapter(chatHistoryAdapter);
            }

            binding.btnSend.setEnabled(false);
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message(bot, msg + " " + msg, commonUtils.getDate());
                    botModel.getMessages().add(message);
                    botModel.setLastMessage(msg + " " + msg);
                    botModel.setCreationdate(commonUtils.getDate());
                    if (binding != null) {
                        binding.btnSend.setEnabled(true);
                        chatHistoryAdapter.notifyDataSetChanged();
                    }
                }
            }, 1000);
        }
    }

    public void recyclerSetup() {
        binding.recyclerChat.setHasFixedSize(true);
        binding.recyclerChat.setLayoutManager(new LinearLayoutManager(getContext()));
        chatHistoryAdapter = new ChatHistoryAdapter(getContext(), botModel.getMessages());
        if (botModel.getMessages() != null && botModel.getMessages().size() > 0) {
            binding.emptyChatLayout.setVisibility(View.GONE);
            binding.recyclerChat.setVisibility(View.VISIBLE);
            binding.recyclerChat.setAdapter(chatHistoryAdapter);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
