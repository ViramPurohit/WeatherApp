package com.viram.weather.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.viram.weather.R
import com.viram.weather.model.WeatherResult


class UserLocationListAdapter(
    var casePartyList: List<WeatherResult>,
    var itemClickListener: onItemClickListener
) : RecyclerView.Adapter<UserLocationListAdapter.InvolveUserClaimViewHolder>() {

    interface onItemClickListener {
        fun onItemClick(
            claimDetails: WeatherResult
        )
    }

    fun refreshSelection(checked: Boolean) {
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InvolveUserClaimViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_user_location, parent, false)
    )
    override fun getItemCount() = casePartyList.size

    override fun onBindViewHolder(holder: InvolveUserClaimViewHolder, position: Int) {
        holder.bind(casePartyList[position])
    }

    inner class InvolveUserClaimViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {


        fun bind(daItem: WeatherResult?) {
//            if (daItem != null) {
//                itemView.txt_ecr_unregister_case_number.text = daItem.najmCaseNo
//                itemView.txt_ecr_unregister_plate_number.text = daItem.plateNo
//
//
//                itemView.txt_ecr_unregister_case_number
//                    .setTypeface(itemView.txt_ecr_unregister_case_number.typeface, Typeface.BOLD)
//
//                itemView.txt_ecr_unregister_plate_number
//                    .setTypeface(itemView.txt_ecr_unregister_plate_number.typeface, Typeface.BOLD)
//
//                itemView.ecr_btnClaimDetail.text = itemView.context.getString(R.string.ecr_unfinished_claim_proceed)
//
//                itemView.ecr_btnClaimDetail.visibility = View.VISIBLE
//                itemView.img_ecr_old_case.visibility = View.GONE
//
//                itemView.ecr_btnClaimDetail.setOnClickListener {
//                    itemClickListener.onItemClick(daItem)
//                }
//
//            }


        }


    }
    fun setAssessmentValue(total: String): String {
        val valueWithoutEpsilon = total.toBigDecimal()
        /* Set the converted value to your android view using setText() function */
        return valueWithoutEpsilon.toPlainString()
    }
}