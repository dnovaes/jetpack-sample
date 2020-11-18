package com.arctouch.io.outdoorsychallenge.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
//import androidx.navigation.findNavController
//import com.arctouch.io.outdoorsychallenge.OutdoorsyGraphDirections
import com.arctouch.io.outdoorsychallenge.R
import com.arctouch.io.outdoorsychallenge.databinding.ActivityOutdoorsyBinding

class OutdoorsyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOutdoorsyBinding

//    private val navController by lazy { findNavController(R.id.fcv_outdoorsy_nav_host) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil
            .setContentView<ActivityOutdoorsyBinding>(this, R.layout.activity_outdoorsy)
            .apply { lifecycleOwner = this@OutdoorsyActivity }
    }
}
