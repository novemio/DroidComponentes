package com.novemio.android.droidcomponents

import android.view.Menu
import android.view.MenuItem
import com.novemio.android.components.mvp.BaseActivity
import com.novemio.android.components.mvp.MvpView

class MainActivity : BaseActivity<MvpView>() {


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String?) {
    }


    override fun injectMembers() {
        //do not override if use dagger
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else                 -> super.onOptionsItemSelected(item)
        }
    }
}
