package com.quiz.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.quiz.ecommerce.R

class MainActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView;
    lateinit var fab:FloatingActionButton;
    lateinit var bottomAppBar:BottomAppBar;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.fragment)

        fab=findViewById(R.id.fab)
        bottomAppBar=findViewById(R.id.bottomAppBar)

        fab.setOnClickListener {

            navController.navigate(R.id.cartFragment)

        }


        bottomNavigationView =findViewById(R.id.bottomNavigationView)
        val navHostFragment:NavHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment;
        NavigationUI.setupWithNavController(bottomNavigationView,
                navHostFragment.navController);


        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = true

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment ->
                {showBottomNav()

                    hideTitleBar()}
                R.id.SettingFragment ->
                {showBottomNav()
                    hideTitleBar()}
                R.id.productDetail -> {hideTitleBar()
                    hideBottomNav()}
                R.id.startPage ->
                {hideTitleBar()
                    hideBottomNav()}
                R.id.login ->{hideTitleBar()
                    hideBottomNav()}
                R.id.register ->{hideTitleBar()
                    hideBottomNav()}
                R.id.cartFragment ->{
                    hideTitleBar()
                    hideBottomNav()
                }
                else -> hideBottomNav()

            }
        }
    }

    private fun hideTitleBar() {
        this.supportActionBar?.hide()
    }

    private fun ShowTitleBar() {
        this.supportActionBar?.show()
    }

    private fun showBottomNav() {
        bottomNavigationView.visibility = View.VISIBLE
        bottomAppBar.visibility = View.VISIBLE
        fab.visibility = View.VISIBLE

    }

    private fun hideBottomNav() {
        bottomNavigationView.visibility = View.GONE
        bottomAppBar.visibility = View.GONE
        fab.visibility = View.GONE

    }


}