package com.example.chatbot.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.chatbot.R;
import com.example.chatbot.databinding.FragmentBotBinding;
import com.example.chatbot.model.BotModel;
import com.example.chatbot.utils.CommonUtils;
import com.example.chatbot.view.adapter.BotAdapter;
import com.example.chatbot.view.dialog.CustomAddBotDialog;
import com.example.chatbot.viewmodel.MainViewModel;
import java.util.List;

public class BotFragment extends Fragment implements View.OnClickListener {

    private FragmentBotBinding binding;
    private CustomAddBotDialog customAddBotDialog;
    private BotAdapter botAdapter;
    private MainViewModel mainViewModel;
    private CommonUtils commonUtils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBotBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ObjectInitialization();
        recyclerSetup();
        liveDataObserver();
        binding.fab.setOnClickListener(this);
    }

    public void ObjectInitialization(){
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        commonUtils = new CommonUtils();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == binding.fab.getId())
            showAddBotDialog();
    }

    public void liveDataObserver() {
        mainViewModel.getMutableData().observe(getActivity(), new Observer<List<BotModel>>() {
            @Override
            public void onChanged(List<BotModel> botModels) {
                mainViewModel.sortList();
                botAdapter.notifyDataSetChanged();
            }
        });
    }

    public void recyclerSetup() {
        botAdapter = new BotAdapter(mainViewModel.getMutableData().getValue());
        binding.recyclerViewBot.setHasFixedSize(true);
        binding.recyclerViewBot.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewBot.setAdapter(botAdapter);
    }

    public void showAddBotDialog() {
        customAddBotDialog = new CustomAddBotDialog(getContext(), new CustomAddBotDialog.CustomDialogListner() {
            @Override
            public void onSubmitClick(String botName) {
                if (checkBotExits(botName))
                    Toast.makeText(getActivity(), getString(R.string.bot_exist_txt), Toast.LENGTH_SHORT).show();
                else {
                    BotModel botModel = new BotModel();
                    botModel.setName(botName);
                    botModel.setCreationdate(commonUtils.getDate());
                    mainViewModel.setUserMutableLiveData(botModel);
                    customAddBotDialog.dismiss();
                }
            }
        });
        customAddBotDialog.show();
    }

    public boolean checkBotExits(String botName) {
        for (BotModel b : mainViewModel.getMutableData().getValue()) {
            if (b.getName().equalsIgnoreCase(botName)) {
                return true;
            }
        }
        return false;
    }
}

