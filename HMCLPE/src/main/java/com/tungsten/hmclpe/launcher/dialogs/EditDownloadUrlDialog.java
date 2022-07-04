package com.tungsten.hmclpe.launcher.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.tungsten.hmclpe.R;

public class EditDownloadUrlDialog extends Dialog implements View.OnClickListener {

    public interface CallBacks {
        void onSuccess(String url);

        void onCancel();
    }

    private CallBacks callBacks;

    private EditText editText;
    private Button positive;
    private Button negative;

    public EditDownloadUrlDialog(@NonNull Context context, CallBacks callBacks) {
        super(context);
        this.callBacks = callBacks;
        setContentView(R.layout.dialog_edit_download_url);
        setCancelable(false);
        init();
    }

    private void init() {
        editText = findViewById(R.id.download_name);
        positive = findViewById(R.id.download);
        negative = findViewById(R.id.cancel);
        positive.setOnClickListener(this);
        negative.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == positive) {
            if (!editText.getText().toString().equals("")) {
                callBacks.onSuccess(editText.getText().toString());
                dismiss();
            } else {
                Toast.makeText(getContext(), getContext().getText(R.string.dialog_download_modpack_url_empty), Toast.LENGTH_SHORT).show();
            }
        } else if (v == negative) {
            callBacks.onCancel();
            dismiss();
        }
    }
}
