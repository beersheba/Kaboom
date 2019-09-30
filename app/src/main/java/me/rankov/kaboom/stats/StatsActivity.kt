package me.rankov.kaboom.stats

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_stats.*
import me.rankov.kaboom.R

class StatsActivity: AppCompatActivity(), StatsContract.View {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        stats_fragment.arguments = intent.extras
    }
}