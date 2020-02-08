package me.rankov.kaboom.country.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_countries_list.*
import me.rankov.kaboom.GlideApp
import me.rankov.kaboom.R
import me.rankov.kaboom.ScreenNavigator
import me.rankov.kaboom.di.ActivityModule
import me.rankov.kaboom.di.DaggerActivityComponent
import me.rankov.kaboom.model.Country
import org.jetbrains.anko.toast
import javax.inject.Inject

class CountriesListActivity : AppCompatActivity(), CountriesListContract.View {

    @Inject lateinit var screenNavigator: ScreenNavigator

    init {
        DaggerActivityComponent.builder().activityModule(ActivityModule(this)).build().inject(this)
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, CountriesListActivity::class.java)
            context.startActivity(intent)
        }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sign_out -> {
                signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun onBackPressed() {
    }

    override fun setBackground() {
        GlideApp.with(this).load(R.drawable.earth).centerCrop().into(list_background)
    }

    private val presenter = CountriesListPresenterImpl(this, CountriesListLoadInteractor())

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

    private fun signOut() {
        presenter.onSignOut()
    }

    override fun navigateToCountry(country: Country, imageView: ImageView) {
        screenNavigator.toCountryDetailsScreen(country, imageView)
    }

    override fun navigateToHome() {
        screenNavigator.toLoginScreen()
    }
}
