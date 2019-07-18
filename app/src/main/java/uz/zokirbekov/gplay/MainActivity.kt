package uz.zokirbekov.gplay

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.FrameLayout
import uz.zokirbekov.gplay.fragments.BaseFragment
import uz.zokirbekov.gplay.fragments.MainMenuFragment

class MainActivity : AppCompatActivity() {

    private lateinit var content:FrameLayout
    private var currentFragment:Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        content = findViewById(R.id.content)
        switchFragment(MainMenuFragment())
    }

    public fun switchFragment(fragment: Fragment)
    {
        supportFragmentManager.beginTransaction()
                .replace(R.id.content,fragment)
                .commit()
        currentFragment = fragment
    }

    override fun onBackPressed() {
        val parent = ( currentFragment as BaseFragment ).parent
        if (parent != null)
            switchFragment(parent)
    }

}
