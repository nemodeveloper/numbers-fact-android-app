package ru.nemodev.number.fact.ui.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.nemodev.number.fact.R;
import ru.nemodev.number.fact.utils.AndroidUtils;


public abstract class BaseFragment extends Fragment {

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(AndroidUtils.getString(R.string.load_message));

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void showLoader() {
        progressDialog.show();
    }

    protected void hideLoader() {
        progressDialog.dismiss();
    }
}
