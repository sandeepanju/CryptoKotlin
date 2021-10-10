package com.example.cryptokotlin.adapter

import android.view.View
import com.example.cryptokotlin.R
import com.example.cryptokotlin.coreAdapter.BaseAdapter
import com.example.cryptokotlin.coreAdapter.BaseViewHolder
import com.example.cryptokotlin.coreAdapter.bindings
import com.example.cryptokotlin.databinding.ItemCryptoBinding
import com.example.cryptokotlin.pojo.MData


class CryptoAdapter : BaseAdapter() {

    override fun layout(type: String) = R.layout.item_crypto

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return CryptoViewHolder(view)
    }

    inner class CryptoViewHolder(view: View) : BaseViewHolder(view){

        private val binding: ItemCryptoBinding by bindings(view)

        override fun bindData(item: Any) {
            if(item is MData){

                binding.apply {
                    data = item
                }
            }
        }
    }
}