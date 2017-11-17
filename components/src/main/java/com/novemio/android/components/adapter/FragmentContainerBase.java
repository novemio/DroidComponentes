package com.novemio.android.components.adapter;//package com.xix.cleanMvpArchitecture.presentation.base.adapter;
//
//import android.app.Activity;
//import android.content.Context;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.util.Log;
//import com.rtrk.smarthome.R;
//import com.xix.cleanMvpArchitecture.presentation.base.mvp.BaseFragment;
//import java.util.Stack;
//
///**
// * A simple {@link Fragment} subclass.
// */
//
//public abstract class FragmentContainerBase extends BaseFragment {
//
//    private  int MIN_SIZE_OF_STACK = 1;
//    Stack<BaseFragment> fragmentStack = new Stack<>();
//    private BaseFragment mCurrentFrag;
//    private int mTransitionMode = FragmentTransaction.TRANSIT_NONE;
//    private int mTagCount;
//    private String LOG_TAG;
//    private FragmentContainerBaseListener interactionListener;
//    public FragmentContainerBase(String LOG_TAG) {
//        super(LOG_TAG);
//        this.LOG_TAG = LOG_TAG;
//    }
//
//
//    @Override public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        onAttachBase(activity);
//    }
//
//    protected void setMinStackSIze(int minStackSIze) {
//        this.MIN_SIZE_OF_STACK = minStackSIze;
//    }
//
//    protected void onAttachBase(Context context) {
//        try {
//            interactionListener = (FragmentContainerBaseListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(getActivity().toString());
//        }
//    }
//
//    @Override public void onResume() {
//        Log.v(LOG_TAG, "---> onResume()");
//        initTitle();
//        super.onResume();
//        Fragment fragment = getCurrentFrag();
//        if (fragment != null) {
//            fragment.onResume();
//        }
//        Log.v(LOG_TAG, "onResume() <--- ");
//    }
//
//    public abstract void initTitle();
//
//    @Nullable protected Fragment getCurrentFrag() {
//        //Attempt to used stored current fragment
//        if (mCurrentFrag != null) {
//            return mCurrentFrag;
//        }
//        //if not, try to pull it from the stack
//        else {
//            if (!fragmentStack.isEmpty()) {
//                return getChildFragmentManager().findFragmentByTag(fragmentStack.peek().getTag());
//            } else {
//                return null;
//            }
//        }
//    }
//
//    /**
//     * Clears the current tab's stack to get to just the bottom Fragment.
//     */
//    public void clearStack() {
//        setupBackButton(false);
//        Log.v(LOG_TAG, "---> clearStack()");
//        // Only need to start popping and reattach if the stack is greater than 1
//        if (fragmentStack.size() > MIN_SIZE_OF_STACK) {
//            Fragment fragment;
//            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//            ft.setTransition(mTransitionMode);
//
//            //Pop all of the fragments on the stack and remove them from the FragmentManager
//            while (fragmentStack.size() > MIN_SIZE_OF_STACK) {
//                fragment = getCurrentFrag();
//                if (fragment != null) {
//                    ft.remove(fragment);
//                    Log.d(LOG_TAG, "clearStack: removed" + fragment.getTag());
//                } else {
//                    Log.w(LOG_TAG, "clearStack: notRemovedFromStack");
//                }
//                fragmentStack.pop();
//            }
//
//            //Attempt to reattach previous fragment
//            fragment = reattachPreviousFragment(ft);
//            //If we can't reattach, either pull from the stack, or create a new base fragment
//            if (fragment != null) {
//                ft.commitAllowingStateLoss();
//            } else {
//                if (!fragmentStack.isEmpty()) {
//                    fragment = fragmentStack.peek();
//                    ft.add(R.id.fragment_container, fragment, fragment.getTag());
//                    ft.commitAllowingStateLoss();
//                }
//            }
//
//            mCurrentFrag = (BaseFragment) fragment;
//            //if (mNavListener != null) {
//            //  mNavListener.onFragmentTransaction(mCurrentFrag);
//            //}
//        }
//        Log.v(LOG_TAG, "<--- clearStack() ");
//    }
//
//    /**
//     * Will attempt to reattach a previous fragment in the FragmentManager, or return null if not able to,
//     *
//     * @param ft current fragment transaction
//     * @return Fragment if we were able to find and reattach it
//     */
//    @Nullable private Fragment reattachPreviousFragment(FragmentTransaction ft) {
//        Fragment fragment = null;
//        if (!fragmentStack.isEmpty()) {
//            fragment = getChildFragmentManager().findFragmentByTag(fragmentStack.peek().getTag());
//            if (fragment != null) {
//                ft.attach(fragment);
//            }
//        }
//        return fragment;
//    }
//
//    public void setupBackButton(Boolean value) {
//        if (interactionListener != null) {
//            interactionListener.setupBackButton(value);
//        }
//    }
//
//    /**
//     * Push a fragment onto the current stack
//     *
//     * @param fragment The fragment that is to be pushed
//     */
//    public void push(BaseFragment fragment) {
//        Log.v(LOG_TAG, "---> push()");
//        if (fragment != null) {
//            final FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//            ft.setTransition(mTransitionMode);
//            detachCurrentFragment(ft);
//            ft.add(R.id.fragment_container, fragment, generateTag(fragment));
//            ft.commit();
//
//            getChildFragmentManager().executePendingTransactions();
//            fragmentStack.push(fragment);
//
//            mCurrentFrag = fragment;
//        }
//        Log.v(LOG_TAG, "<--- push() ");
//    }
//
//    public void replace(BaseFragment fragment) {
//        Log.v(LOG_TAG, "---> push()");
//        if (fragment != null) {
//            final FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//            ft.setTransition(mTransitionMode);
//            detachCurrentFragment(ft);
//            ft.replace(R.id.fragment_container, fragment, generateTag(fragment));
//            ft.commit();
//
//            getChildFragmentManager().executePendingTransactions();
//            fragmentStack.push(fragment);
//
//            mCurrentFrag = fragment;
//        }
//        Log.v(LOG_TAG, "<--- push() ");
//    }
//
//    /**
//     * Attemps to detach any current fragment if it exists, and if none is found, returns;
//     *
//     * @param ft the current transaction being performed
//     */
//    private void detachCurrentFragment(FragmentTransaction ft) {
//        Fragment oldFrag = getCurrentFrag();
//        if (oldFrag != null) {
//            ft.detach(oldFrag);
//        }
//    }
//
//    private String generateTag(Fragment fragment) {
//        String tag = fragment.getClass().getName() + ++mTagCount;
//        Log.v(LOG_TAG, "generateTag: " + tag);
//        return tag;
//    }
//
//    public void detachCurrentFragment() {
//        Fragment oldFrag = getCurrentFrag();
//        if (oldFrag != null) {
//            getChildFragmentManager().beginTransaction().detach(oldFrag);
//        }
//    }
//
//    /**
//     * Pop the current fragment from the stack/current tab
//     */
//    public boolean pop() {
//        Log.v(LOG_TAG, "---> pop() ");
//        boolean poped = false;
//        if (fragmentStack.size() > MIN_SIZE_OF_STACK) {
//            Fragment poppingFrag = getCurrentFrag();
//            if (poppingFrag != null) {
//                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//                ft.setTransition(mTransitionMode);
//                ft.remove(poppingFrag);
//
//                //overly cautious fragment pop
//                if (!fragmentStack.isEmpty()) {
//                    fragmentStack.pop();
//                }
//
//                //Attempt reattach, if we can't, try to pop from the stack and push that on
//                Fragment fragment = reattachPreviousFragment(ft);
//                if (fragment == null && !fragmentStack.isEmpty()) {
//                    fragment = fragmentStack.peek();
//                    ft.add(R.id.fragment_container, fragment, fragment.getTag());
//                }
//
//                //Commit our transactions
//                ft.commit();
//                getChildFragmentManager().executePendingTransactions();
//
//                mCurrentFrag = (BaseFragment) fragment;
//                //if (mNavListener != null) {
//                //  mNavListener.onFragmentTransaction(mCurrentFrag);
//                //}
//                poped = true;
//            }
//        }
//
//        if (fragmentStack.size() == MIN_SIZE_OF_STACK) {
//            setupBackButton(false);
//        }
//        Log.v(LOG_TAG, "<--- pop() " + poped);
//        return poped;
//    }
//
//    public abstract boolean onBackPress();
//
//    public interface FragmentContainerBaseListener {
//
//        void setupBackButton(Boolean value);
//    }
//}
//
//
//
//
