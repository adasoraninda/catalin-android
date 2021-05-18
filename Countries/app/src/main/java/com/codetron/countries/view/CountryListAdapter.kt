package com.codetron.countries.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codetron.countries.R
import com.codetron.countries.data.model.Country
import com.codetron.countries.databinding.ItemCountryBinding

class CountryListAdapter : RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    private val counties = arrayListOf<Country>()

    fun updateCountries(countries: List<Country>) {
        this.counties.clear()
        this.counties.addAll(countries)
        notifyDataSetChanged()
    }

    class CountryViewHolder(
        private val binding: ItemCountryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(country: Country) {
            with(binding) {
                txtName.text = country.name
                txtCapital.text = country.capital

                Glide.with(imgCountry.context)
                    .setDefaultRequestOptions(
                        RequestOptions()
                            .placeholder(getProgressDrawable(imgCountry.context))
                            .error(R.color.black)
                    )
                    .load(country.image)
                    .into(imgCountry)

            }
        }

        private fun getProgressDrawable(context: Context): CircularProgressDrawable {
            return CircularProgressDrawable(context).apply {
                setColorSchemeColors(R.color.teal_200, R.color.purple_500)
                strokeWidth = 10f
                centerRadius = 50f
                start()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(
            ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.onBind(counties[position])
    }

    override fun getItemCount(): Int {
        return counties.size
    }
}