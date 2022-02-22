package com.example.chatbot.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.chatbot.R;
import com.example.chatbot.databinding.DialogAddBotBinding;

public class CustomAddBotDialog extends Dialog {
    private Context context;
    private CustomDialogListner customDialogListner;
    private DialogAddBotBinding binding;

    public CustomAddBotDialog(@NonNull Context context, CustomDialogListner customDialogListner) {
        super(context);
        this.context = context;
        this.customDialogListner = customDialogListner;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        binding = DialogAddBotBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setDialogDimension();

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.txtName.getText().toString();
                if (name.isEmpty() || name == null)
                    Toast.makeText(context, context.getString(R.string.enter_bot_name_txt), Toast.LENGTH_SHORT).show();
                else customDialogListner.onSubmitClick(name);
            }
        });
    }

    public void setDialogDimension() {
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.80);
        int height = (int) (ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setLayout(width, height);
    }

    public interface CustomDialogListner {
        public void onSubmitClick(String botName);
    }
}
