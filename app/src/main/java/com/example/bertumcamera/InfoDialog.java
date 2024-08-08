package com.example.bertumcamera;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Html;

public class InfoDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(Html.fromHtml("<p text-align='justify'>Данный функционал будет реализован в следующей версии " +
                        "приложения.</p><p text-align='justify'>Оператор свяжется с Вами для уточнения " +
                        "деталей поставки запчастей по номеру Вашего телефона.</p></center>"))
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }
}
