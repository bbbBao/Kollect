package com.example.kollect;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddFavoriteArtistDialog extends AppCompatDialogFragment {
    private EditText editgroup;
    private AddFavoriteArtistDialogListener addFavoriteArtistDialogListener;
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.favorite_group_dialog, null);

        builder.setView(view).setTitle("enter your favorite").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String groupname = editgroup.getText().toString();
                        addFavoriteArtistDialogListener.applyTexts2(groupname);
                    }
                });
        editgroup = view.findViewById(R.id.editgroup);
        return builder.create();

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            addFavoriteArtistDialogListener = (AddFavoriteArtistDialogListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString()+
                    "must implement AddGroupDialogListener");
        }
    }
    public interface AddFavoriteArtistDialogListener{
        void applyTexts2(String groupname);
    }

}