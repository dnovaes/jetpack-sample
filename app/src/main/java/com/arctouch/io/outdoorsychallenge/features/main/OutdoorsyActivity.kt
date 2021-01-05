package com.arctouch.io.outdoorsychallenge.features.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.arctouch.io.outdoorsychallenge.R
import com.arctouch.io.outdoorsychallenge.databinding.ActivityOutdoorsyBinding
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import org.koin.androidx.viewmodel.ext.android.viewModel

class OutdoorsyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOutdoorsyBinding
    private val viewModel: OutdoorsyViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil
            .setContentView<ActivityOutdoorsyBinding>(this, R.layout.activity_outdoorsy)
            .apply { lifecycleOwner = this@OutdoorsyActivity }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val result: IntentResult =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result.contents == null)
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            else viewModel.onQrCodeRead(result.contents)
        }
    }
}
