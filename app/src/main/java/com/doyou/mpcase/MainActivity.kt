package com.doyou.mpcase

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.doyou.mpcase.bean.TabBean
import com.doyou.mpcase.fragment.BarChartFragment
import com.doyou.mpcase.fragment.GroupChartFragment
import com.doyou.mpcase.fragment.PieChartFragment
import com.doyou.mpcase.fragment.linechart.LineChartFragment
import com.flyco.tablayout.listener.CustomTabEntity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val fragmentList = ArrayList<Fragment>(4)
    private val entityList = ArrayList<CustomTabEntity>(4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        fragmentList.add(LineChartFragment())
        fragmentList.add(BarChartFragment())
        fragmentList.add(PieChartFragment())
        fragmentList.add(GroupChartFragment())

        entityList.add(TabBean("折线", R.drawable.tab_icon_line_selected, R.drawable.tab_icon_line_default))
        entityList.add(TabBean("柱状", R.drawable.tab_icon_bar_selected, R.drawable.tab_icon_bar_default))
        entityList.add(TabBean("饼状", R.drawable.tab_icon_pie_selected, R.drawable.tab_icon_pie_default))
        entityList.add(TabBean("组合", R.drawable.tab_icon_group_selected, R.drawable.tab_icon_group_default))

        mainTab.setTabData(entityList,this,R.id.mainContainer,fragmentList)
    }
}
