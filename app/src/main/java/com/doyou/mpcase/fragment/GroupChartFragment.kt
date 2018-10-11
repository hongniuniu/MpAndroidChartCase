package com.doyou.mpcase.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.doyou.mpcase.R

/**
 * 组合图
 * @autor hongbing
 * @date 2018/10/11
 */
class GroupChartFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.groupchart_fragment, null)
    }
}
