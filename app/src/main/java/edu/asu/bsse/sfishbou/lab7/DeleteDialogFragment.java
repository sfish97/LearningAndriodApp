package edu.asu.bsse.sfishbou.lab7;
//https://developer.android.com/guide/topics/ui/dialogs
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class DeleteDialogFragment extends DialogFragment {

    public interface DeleteDialogListener{
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    DeleteDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.deleteDialogMessage)
                .setPositiveButton(R.string.deleteDialogPositive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //DELETE THE PLACE
                        listener.onDialogPositiveClick(DeleteDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.deleteDialogNegative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDialogNegativeClick(DeleteDialogFragment.this);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try{
            listener = (DeleteDialogListener)context;
        }catch(ClassCastException e){
            throw new ClassCastException("Must implement DeleteDialogListener");
        }
    }

}
