package com.viram.weather

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.viram.weather.ui.city.CityFragment
import com.viram.weather.ui.help.HelpFragment
import com.viram.weather.ui.home.HomeFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        navView.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                val id: Int = menuItem.getItemId()
                if (id == R.id.navigation_home) {
                    toolbar.title = resources.getString(R.string.title_home)
                    loadFragment(HomeFragment())
                    return true
                } else if (id == R.id.navigation_city) {
                    toolbar.title = resources.getString(R.string.title_city)
                    loadFragment(CityFragment())
                    return true
                } else if (id == R.id.navigation_help) {
                    toolbar.title = resources.getString(R.string.title_help)
                    loadFragment(HelpFragment())
                    return true
                }
                return true
            }
        })
        navView.selectedItemId = R.id.navigation_home
    }

    fun loadFragment(fragment: Fragment){
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container, fragment)
        transaction.addToBackStack("")
        transaction.commit()
    }
}