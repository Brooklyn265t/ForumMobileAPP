package com.example.forum;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;

public class CustomDialogFragment extends DialogFragment {
    private FirebaseAuth auth;
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle("Диалоговое окно")
                .setMessage("Для закрытия нажмите ОК")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setView(R.layout.custom_dialog)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Activity activity = getActivity();
                        if (activity != null) {
                            auth.signOut();
                            Intent exitaccount = new Intent(activity, MainActivity.class);
                            startActivity(exitaccount);
                        }
                    }
                })
                .setNegativeButton("Отмена", null)
                .create();
    }
}
