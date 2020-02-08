package me.rankov.kaboom.stats

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_stats.*
import me.rankov.kaboom.R

class StatsActivity : AppCompatActivity(), StatsContract.View {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, StatsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        stats_fragment.arguments = intent.extras
    }
}