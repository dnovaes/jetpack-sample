package com.arctouch.io.outdoorsychallenge.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.arctouch.io.outdoorsychallenge.R
import com.arctouch.io.outdoorsychallenge.databinding.ActivityOutdoorsyBinding

class OutdoorsyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOutdoorsyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil
            .setContentView<ActivityOutdoorsyBinding>(this, R.layout.activity_outdoorsy)
            .apply { lifecycleOwner = this@OutdoorsyActivity }
    }
}
