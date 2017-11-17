package com.novemio.android.components.adapter;//package com.xix.cleanMvpArchitecture.presentation.base.adapter;
//
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.app.AlertDialog;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import com.rtrk.smarthome.R;
//import java.io.Serializable;
//import org.androidannotations.annotations.EFragment;
//import org.androidannotations.annotations.FragmentArg;
//
///**
// * @author xix
// */
//@EFragment
//public class FragmentDialogContainer extends BaseDialogFragment {
//
//    private Fragment mFragment = null;
//    @FragmentArg OnButtonClickedListener mOnButtonClickedListener = null;
//    @FragmentArg Integer mTitleID = null;
//
//    public interface OnButtonClickedListener extends Serializable {
//
//        public boolean onOkClicked(FragmentDialogContainer dialog);
//
//        public void onCancelClicked(FragmentDialogContainer dialog);
//    }
//
//    @Override protected int setupFragmentLayout() {
//        return R.layout.dialog_fragment_conatiner;
//    }
//
//    @Override protected Dialog createDialog(Bundle savedInstanceState) {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setView(inflateView());
//        builder.setTitle(mTitleID);
//        builder.setPositiveButton(R.string.ok, null);
//        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//            @Override public void onClick(DialogInterface dialogInterface, int i) {
//                if (mOnButtonClickedListener != null) {
//                    mOnButtonClickedListener.onCancelClicked(FragmentDialogContainer.this);
//                }
//            }
//        });
//        AlertDialog lDialog = builder.create();
//        lDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override public void onShow(DialogInterface dialogInterface) {
//                ((AlertDialog) lDialog).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
//
//                    @Override public void onClick(View view) {
//                        if (mOnButtonClickedListener != null) {
//                            if (mOnButtonClickedListener.onOkClicked(FragmentDialogContainer.this)) {
//                                dismiss();
//                            }
//                        }
//                    }
//                });
//            }
//        });
//        return lDialog;
//    }
//
//    @Nullable @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View lView = super.onCreateView(inflater, container, savedInstanceState);
//        showFragment();
//        return lView;
//    }
//
//    private void showFragment() {
//        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//        transaction.replace(R.id.dialog_fragment_container, mFragment);
//        transaction.commitAllowingStateLoss();
//    }
//
//    public void show(FragmentManager manager, Fragment fragment, String tag) {
//        super.show(manager, tag);
//        setCancelable(false);
//        mFragment = fragment;
//    }
//}
