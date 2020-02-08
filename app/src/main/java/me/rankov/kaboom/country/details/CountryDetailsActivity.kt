package me.rankov.kaboom.country

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_country_details.*
import me.rankov.kaboom.EXTRA_COUNTRY
import me.rankov.kaboom.EXTRA_COUNTRY_TRANSITION_NAME
import me.rankov.kaboom.GlideApp
import me.rankov.kaboom.R
import me.rankov.kaboom.country.details.CountryDetailsContract.View
import me.rankov.kaboom.country.details.CountryDetailsInteractor
import me.rankov.kaboom.country.details.CountryDetailsPresenterImpl
import me.rankov.kaboom.model.Country
import me.rankov.kaboom.stats.StatsActivity
import org.jetbrains.anko.selector
import java.text.NumberFormat


class CountryDetailsActivity : AppCompatActivity(), View {

    private lateinit var country: Country
    private lateinit var presenter: CountryDetailsPresenterImpl

    companion object {
        fun start(context: Activity, country: Country, imageView: ImageView) {
            val intent = Intent(context, CountryDetailsActivity::class.java)
            intent.apply {
                putExtra(EXTRA_COUNTRY, country)
                putExtra(EXTRA_COUNTRY_TRANSITION_NAME, ViewCompat.getTransitionName(imageView))
            }

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    context, imageView, ViewCompat.getTransitionName(imageView).toString())
            context.startActivity(intent, options.toBundle())
        }
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

    override fun onResume() {
        super.onResume()
        action_view.visibility = android.view.View.INVISIBLE
        enableProgress(false)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun setBackground() {
        GlideApp.with(this).load(R.drawable.earth).centerCrop().into(details_background)
    }

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
        heal_button.setOnClickListener { presenter.onActionClicked(false) }
        attack_button.setOnClickListener { presenter.onActionClicked(true) }
    }

    override fun goToStats(country: Country) {
        val intent = Intent(this, StatsActivity::class.java)
        intent.putExtra(EXTRA_COUNTRY, country)
        startActivity(intent)
    }

    override fun showAction(url: String) {
        GlideApp.with(this).asGif().load(url).listener(object : RequestListener<GifDrawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<GifDrawable>?, isFirstResource: Boolean): Boolean {
                enableProgress(false)
                println(e)
                return false
            }

            override fun onResourceReady(resource: GifDrawable?, model: Any?, target: Target<GifDrawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                enableProgress(false)
                action_view.visibility = VISIBLE
                var loopCount = 1
                resource?.let {
                    val minFramesCount = 20
                    if (it.frameCount in 1 until minFramesCount) {
                        loopCount = minFramesCount / it.frameCount
                    }
                }
                resource?.setLoopCount(loopCount)
                resource?.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
                    override fun onAnimationEnd(drawable: Drawable?) {
                        presenter.onActionImageShown(country)
                        resource.unregisterAnimationCallback(this)
                    }
                })
                return false
            }
        }).into(action_view)
    }

    fun enableProgress(enabled: Boolean) {
        val visibility = if (enabled) VISIBLE else GONE
        progress.visibility = visibility
        heal_button.isEnabled = !enabled
        attack_button.isEnabled = !enabled
    }

    override fun showActionSelector(items: List<String>, attack: Boolean) {
        val title = if (attack) "Weapon of choice" else "Cure of choice"
        selector(title, items) { dialogInterface, i ->
            presenter.onItemSelected(i, country, attack)
            enableProgress(true)
        }
    }

}
