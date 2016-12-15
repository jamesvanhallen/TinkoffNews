package com.james.tinkoffnews.view.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import com.arellomobile.mvp.MvpAppCompatActivity
import com.james.tinkoffnews.R
import com.james.tinkoffnews.isOnline
import com.james.tinkoffnews.replaceFragment
import com.james.tinkoffnews.setWhiteText
import com.james.tinkoffnews.view.fragment.NewsListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(NewsListFragment.newInstance())
    }

    private fun checkIsOnline() {
        if (isOnline()) {
            replaceFragment(NewsListFragment.newInstance())
        } else {
            val snack = Snackbar.make(main_container, getString(R.string.not_online), Snackbar.LENGTH_INDEFINITE)
            snack.setWhiteText()
            snack.setAction(getString(R.string.snack_bar_action), {
                checkIsOnline()
            })
            snack.show()
        }
    }
}
