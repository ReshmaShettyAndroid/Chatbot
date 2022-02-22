package com.example.chatbot.view.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbot.R;
import com.example.chatbot.databinding.RowAddbotBinding;
import com.example.chatbot.model.BotModel;

import java.util.List;

public class BotAdapter extends RecyclerView.Adapter<BotAdapter.BotViewHolder> {

    private List<BotModel> botModelList;
    private RowAddbotBinding rowAddbotBinding;

    public BotAdapter(List<BotModel> botModelList) {
        this.botModelList = botModelList;
    }

    @NonNull
    @Override
    public BotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        rowAddbotBinding = RowAddbotBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BotViewHolder(rowAddbotBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BotViewHolder holder, int position) {
        BotModel botModel = botModelList.get(position);
        holder.rowAddbotBinding.txtBotName.setText(botModel.getName());
        holder.rowAddbotBinding.txtLastMsg.setText(botModel.getLastMessage());
        holder.rowAddbotBinding.txtTime.setText(botModel.getCreationdate());
        holder.rowAddbotBinding.lytParentRow.setTag(botModel);
        holder.rowAddbotBinding.lytParentRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BotModel selectedBotModel = (BotModel) view.getTag();
                Bundle bundle = new Bundle();
                bundle.putSerializable("Botmodel", selectedBotModel);
                Navigation.findNavController(view).navigate(R.id.action_BotFragment_to_ChatFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return botModelList.size();
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder {
        RowAddbotBinding rowAddbotBinding;

        public BotViewHolder(@NonNull RowAddbotBinding binding) {
            super(binding.getRoot());
            rowAddbotBinding = binding;
        }
    }
}
