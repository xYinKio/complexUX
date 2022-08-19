package com.example.complexux

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.complexux.databinding.ActivityMainBinding
import com.example.complexux.navigation.NavigationFragmentFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = NavigationFragmentFactory()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


    }
}