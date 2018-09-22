package me.rankov.hth.country

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat.getTransitionName
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_countries_list.*
import me.rankov.hth.EXTRA_COUNTRY
import me.rankov.hth.EXTRA_COUNTRY_TRANSITION_NAME
import me.rankov.hth.R
import org.jetbrains.anko.toast

class CountriesListActivity : AppCompatActivity(), CountriesListView {
    private val presenter = CountriesListPresenter(this, CountriesListLoadInteractor())

    override fun navigateToCountry(country: Country, imageView: ImageView) {
        val intent = Intent(this, CountryDetailsActivity::class.java)
        intent.apply {
            putExtra(EXTRA_COUNTRY, country)
            putExtra(EXTRA_COUNTRY_TRANSITION_NAME, getTransitionName(imageView))
        }

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, imageView, getTransitionName(imageView))
        startActivity(intent, options.toBundle())
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
        list.visibility = View.VISIBLE
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
        list.visibility = View.GONE
    }

    override fun setCountries(countries: List<Country>) {
        title = getString(R.string.countries_title)
        list.adapter = CountriesListItemAdapter(this, countries, presenter::onCountryClicked)
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries_list)
        setSupportActionBar(toolbar)
        setTitle(R.string.loading)
        presenter.onCreate()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
