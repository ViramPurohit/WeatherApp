package com.viram.weather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.viram.weather.R
import com.viram.weather.vo.UserCity
import kotlinx.android.synthetic.main.list_item_user_location.view.*


class UserLocationListAdapter(
    var itemClickListener: onItemClickListener
) : RecyclerView.Adapter<UserLocationListAdapter.InvolveUserClaimViewHolder>() {

    private var userCitys = emptyList<UserCity>() // Cached copy of words

    interface onItemClickListener {
        fun onItemClick(
            userCity: UserCity
        )
        fun onItemDeleteClick(
            city: String
        )
    }

    internal fun setCity(city: List<UserCity>) {
        this.userCitys = city
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InvolveUserClaimViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_user_location, parent, false)
    )
    override fun getItemCount() = userCitys.size

    override fun onBindViewHolder(holder: InvolveUserClaimViewHolder, position: Int) {
        holder.bind(userCitys[position])
    }

    inner class InvolveUserClaimViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {


        fun bind(userCity: UserCity) {
            itemView.city_name.text = userCity.city
            itemView.user_address.text = userCity.address


            itemView.main_view.setOnClickListener {
                itemClickListener.onItemClick(userCity)
            }

            itemView.img_delete.setOnClickListener {
                itemClickListener.onItemDeleteClick(userCity.city)
            }


        }


    }
    fun setAssessmentValue(total: String): String {
        val valueWithoutEpsilon = total.toBigDecimal()
        /* Set the converted value to your android view using setText() function */
        return valueWithoutEpsilon.toPlainString()
    }
}