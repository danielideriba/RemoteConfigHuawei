package com.ml.remoteconfighuawei


import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.huawei.agconnect.remoteconfig.AGConnectConfig
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val GREETING_KEY = "GREETING_KEY"
    private val SET_BOLD_KEY = "SET_BOLD_KEY"
    private lateinit var config: AGConnectConfig
    private var isBold: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        config = AGConnectConfig.getInstance()
        config.applyDefault(R.xml.remote_config)
        greeting.text = config.getValueAsString(GREETING_KEY)
        isBold = config.getValueAsBoolean(SET_BOLD_KEY)

        if (isBold){
            greeting.typeface = Typeface.defaultFromStyle(Typeface.BOLD);
        }

        fetch_button.setOnClickListener {
            fetchAndApply()
        }
    }

    private fun fetchAndApply() {
        config.fetch(0)
            .addOnSuccessListener { configValues ->
                config.apply(configValues)
                updateUI()
            }.addOnFailureListener { e -> greeting!!.text = "fetch setting failed: " + e.message }
    }

    private fun updateUI() {
        val text = config.getValueAsString(GREETING_KEY)
        val isBold = config.getValueAsBoolean(SET_BOLD_KEY)
        greeting!!.text = text
        if (isBold) {
            greeting.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        }
    }
}