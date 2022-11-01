package com.ukm.iotsamplesender.adapter

import android.content.Context
import android.view.LayoutInflater
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.ukm.iotsamplesender.R
import com.ukm.iotsamplesender.databinding.RowHeaderBinding
import com.ukm.iotsamplesender.databinding.RowHistoryBinding
import com.ukm.iotsamplesender.model.SensorValue

class SensorAdapter(title: String, context: Context) : BaseQuickAdapter<SensorValue, BaseDataBindingHolder<RowHistoryBinding>>(
    R.layout.row_history
) {
    init {
        val header = RowHeaderBinding.inflate(LayoutInflater.from(context)).apply {
            textRecord.text = title
        }
        addHeaderView(header.root)
    }
    override fun convert(holder: BaseDataBindingHolder<RowHistoryBinding>, item: SensorValue) {
        holder.dataBinding?.textRecord?.text = item.sensorValue.toString()
    }
}