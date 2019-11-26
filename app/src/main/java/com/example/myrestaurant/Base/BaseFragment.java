package com.example.myrestaurant.Base;

import android.content.Context;
import androidx.fragment.app.Fragment;
import com.afollestad.materialdialogs.MaterialDialog;

public class BaseFragment extends Fragment {

    protected BaseActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (BaseActivity) context;
    }

    protected MaterialDialog showMessage(int titleResId, int messageResId, int posResText) {
        return activity.showMessage(titleResId, messageResId, posResText);

    }

    protected MaterialDialog showConfirmationMessage(int titleResId, int messageResId, int posResText, MaterialDialog.SingleButtonCallback onPosAction) {
        return activity.showConfirmationMessage(titleResId, messageResId, posResText, onPosAction);

    }

    protected MaterialDialog showMessage(String title, String message, String posText) {

        return activity.showMessage(title, message, posText);
    }

    protected MaterialDialog showProgressBar(int message) {

        return activity.showProgressBar(message);
    }

    public void hideProgressBar() {
        activity.hideProgressBar();
    }
}
