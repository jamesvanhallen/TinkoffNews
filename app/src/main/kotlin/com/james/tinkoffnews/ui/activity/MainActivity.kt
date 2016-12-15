package com.james.tinkoffnews.ui.activity

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
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
        if(supportActionBar!=null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.show()
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }

        if (supportFragmentManager.findFragmentById(R.id.main_container)==null) {
            replaceFragment(NewsListFragment.newInstance())
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        val size = supportFragmentManager.backStackEntryCount
        Log.d("MainActivity", "size stack $size" )
    }
}
