package com.example.mindmaper;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class EditTextDialogFragment extends DialogFragment {

    interface EditingTextResultReceiver{
        void receiveEditingTextResult(String text);
    }

    private EditingTextResultReceiver receiver;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        receiver = (EditingTextResultReceiver)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        receiver = null;
    }

    private EditText edtDialog;
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View mainView =inflater.inflate(R.layout.edit_text_dialog_fragment, null);
        builder.setView(mainView);

        Bundle args = getArguments();
        String text = "";

        if(args != null){
            text = args.getString("text");
        }

        this.edtDialog = (EditText) mainView.findViewById(R.id.edtDialog);
        this.edtDialog.setText(text);

        return builder
                .setTitle("Редактировать текст")
                .setView(mainView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        receiver.receiveEditingTextResult(edtDialog.getText().toString());
                    }
                })
                .setNegativeButton("Отмена", null)
                .create();

    }


}
