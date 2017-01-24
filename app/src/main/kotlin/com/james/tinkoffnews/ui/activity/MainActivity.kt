package com.james.tinkoffnews.ui.activity

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.james.tinkoffnews.R
import com.james.tinkoffnews.replaceFragment
import com.james.tinkoffnews.ui.fragment.NewsListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }

        if (supportFragmentManager.findFragmentById(R.id.main_container) == null) {
            replaceFragment(NewsListFragment.newInstance())
        }
    }
}
