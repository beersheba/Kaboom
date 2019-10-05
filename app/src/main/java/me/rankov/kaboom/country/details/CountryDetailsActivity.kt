package me.rankov.kaboom.country

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_country_details.*
import me.rankov.kaboom.EXTRA_COUNTRY
import me.rankov.kaboom.EXTRA_COUNTRY_TRANSITION_NAME
import me.rankov.kaboom.GlideApp
import me.rankov.kaboom.R
import me.rankov.kaboom.country.details.CountryDetailsContract.View
import me.rankov.kaboom.country.details.CountryDetailsPresenterImpl
import me.rankov.kaboom.stats.StatsActivity
import java.text.NumberFormat


class CountryDetailsActivity : AppCompatActivity(), View {
    private lateinit var country: Country
    private lateinit var presenter: CountryDetailsPresenterImpl

    override fun setCountry(country: Country) {
        supportPostponeEnterTransition()
        name.text = country.name
        val populationString = NumberFormat.getNumberInstance().format(country.population)
        population.text = String.format(getString(R.string.population), populationString)
        flag.transitionName = intent.getStringExtra(EXTRA_COUNTRY_TRANSITION_NAME)
        GlideApp.with(this).load(country.flag)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                })
                .into(flag)
        heal_button.setOnClickListener { presenter.onHealClicked(country) }
        attack_button.setOnClickListener { presenter.onAttackClicked(country) }
    }

    override fun heal(country: Country) {

    }

    override fun attack(country: Country) {
        var intent = Intent(this, StatsActivity::class.java)
        intent.putExtra(EXTRA_COUNTRY, country)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_details)
        setSupportActionBar(toolbar)
        title = getString(R.string.country_title)
        country = intent.getParcelableExtra(EXTRA_COUNTRY)
        presenter = CountryDetailsPresenterImpl(this, CountryDetailsInteractor(), country)
        presenter.onCreate()

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

}
