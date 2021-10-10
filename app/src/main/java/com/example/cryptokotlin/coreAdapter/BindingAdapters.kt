package com.example.cryptokotlin.coreAdapter

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.cryptokotlin.R
import com.example.cryptokotlin.pojo.MData


@BindingAdapter("setPercentageFromData")
fun bindImageFromUrl(view: TextView, hValue: Double = 0.0) {
    view.apply {
        val str = String.format("%.2f", hValue) + "%"
        if (hValue > 0.0) {
            text = "+$str"
            setTextColor(ContextCompat.getColor(context, R.color.green))

        } else {
            text = "-$str"
            setTextColor(ContextCompat.getColor(context, R.color.red))
        }
    }
}

@BindingAdapter("setPriceData")
fun bindSetPriceData(view: TextView, data: MData) {
    view.apply {
        text = "$${String.format("%.2f", data.priceUsd)}"
        if (data.changePercent24Hr > 0.0) setTextColor(
            ContextCompat.getColor(
                context,
                R.color.green
            )
        )
        else setTextColor(ContextCompat.getColor(context, R.color.red))
    }
}

