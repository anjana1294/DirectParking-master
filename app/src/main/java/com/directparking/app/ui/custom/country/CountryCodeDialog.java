package com.directparking.app.ui.custom.country;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.directparking.app.R;

import java.util.List;

class CountryCodeDialog {

    static void openCountryCodeDialog(CountryCodePicker codePicker) {
        Context context = codePicker.getContext();
        final Dialog dialog = new Dialog(context);
        codePicker.refreshCustomMasterList();
        codePicker.refreshPreferredCountries();
        List<Country> masterCountries = Country.getCustomMasterCountryList(codePicker);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setContentView(R.layout.layout_country_picker_dialog);
        RecyclerView recyclerView_countryDialog = (RecyclerView) dialog.findViewById(R.id.rv_countries);
        final TextView textViewTitle = (TextView) dialog.findViewById(R.id.tv_title);
        textViewTitle.setText(codePicker.getDialogTitle());
        final EditText editText_search = (EditText) dialog.findViewById(R.id.et_search);
        editText_search.setHint(codePicker.getSearchHintText());
        TextView textView_noResult = (TextView) dialog.findViewById(R.id.tv_empty);
        textView_noResult.setText(codePicker.getNoResultFoundText());
        final CountryCodeAdapter cca = new CountryCodeAdapter(context, masterCountries, codePicker, editText_search, textView_noResult, dialog);
        if (!codePicker.isSelectionDialogShowSearch()) {
            Toast.makeText(context, "Found not to show search", Toast.LENGTH_SHORT).show();
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) recyclerView_countryDialog.getLayoutParams();
            params.height = RecyclerView.LayoutParams.WRAP_CONTENT;
            recyclerView_countryDialog.setLayoutParams(params);
        }
        recyclerView_countryDialog.setLayoutManager(new LinearLayoutManager(context));
        recyclerView_countryDialog.setAdapter(cca);
        dialog.show();
    }
}