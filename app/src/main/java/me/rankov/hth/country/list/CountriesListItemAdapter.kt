package me.rankov.hth.country.list

import android.content.Context
import android.support.v4.view.ViewCompat.setTransitionName
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.view_country_item.view.*
import me.rankov.hth.GlideApp
import me.rankov.hth.R
import me.rankov.hth.country.Country


class CountriesListItemAdapter(private val context: Context,
                               private val countries: List<Country>,
                               private val listener: (Country, ImageView) -> Unit) :
        RecyclerView.Adapter<CountriesListItemAdapter.CountryViewHolder>() {

    class CountryViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.name
        val flag: ImageView = view.flag
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.view_country_item, parent, false))
    }

    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        holder.name.text = country.name
        GlideApp.with(context).load(country.flag).into(holder.flag)
        setTransitionName(holder.flag, country.name)
        holder.view.setOnClickListener { listener(country, holder.flag) }
    }

}