package me.rankov.hth.country

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_country_action.*
import me.rankov.hth.EXTRA_COUNTRY
import me.rankov.hth.EXTRA_COUNTRY_TRANSITION_NAME
import me.rankov.hth.GlideApp
import me.rankov.hth.R
import me.rankov.hth.me.rankov.hth.country.CountryActionPresenter


class CountryActionActivity : AppCompatActivity(), CountryActionView {
    private lateinit var country: Country
    private lateinit var presenter: CountryActionPresenter

    override fun setCountry(country: Country) {
        supportPostponeEnterTransition()
        title = country.name
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
        setContentView(R.layout.activity_country_action)
        setSupportActionBar(toolbar)
        country = intent.getParcelableExtra(EXTRA_COUNTRY)
        presenter = CountryActionPresenter(this, CountryActionInteractor(), country)
        presenter.onCreate()

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

}
