package me.rankov.hth.country

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_country_details.*
import me.rankov.hth.EXTRA_COUNTRY
import me.rankov.hth.EXTRA_COUNTRY_TRANSITION_NAME
import me.rankov.hth.GlideApp
import me.rankov.hth.R
import me.rankov.hth.me.rankov.hth.country.CountryDetailsPresenter
import java.text.NumberFormat


class CountryDetailsActivity : AppCompatActivity(), CountryDetailsView {
    private lateinit var country: Country
    private lateinit var presenter: CountryDetailsPresenter

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
    }

    override fun heal(country: Country) {
        presenter.onHealClicked(country)
    }

    override fun attack(country: Country) {
        presenter.onAttackClicked(country)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_details)
        setSupportActionBar(toolbar)
        title = getString(R.string.country_title)
        country = intent.getParcelableExtra(EXTRA_COUNTRY)
        presenter = CountryDetailsPresenter(this, CountryDetailsInteractor(), country)
        presenter.onCreate()

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

}
