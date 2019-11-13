package com.example.labb02

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView

class CustomExpandableListAdapter : BaseExpandableListAdapter {

    private val context:Context
    private val expandableListTitle:ArrayList<String>
    private val expandableListDetail:HashMap<String, ArrayList<String>>

    constructor(context: Context,
                expandableListTitle:ArrayList<String>,
                expandableListDetail:HashMap<String, ArrayList<String>>) : super() {
        this.context = context
        this.expandableListTitle = expandableListTitle
        this.expandableListDetail = expandableListDetail
    }


    override fun getGroup(listPos: Int): Any {
        return expandableListTitle[listPos]
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    @Suppress("NAME_SHADOWING")
    override fun getGroupView(listPos: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var listTitle = getGroup(listPos) as String
        var convertView = convertView
        if (convertView == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_menu, null)
        }
        val listTitleTextView: TextView = convertView!!.findViewById(R.id.listTitle)
        listTitleTextView.setTypeface(null, Typeface.BOLD)
        listTitleTextView.text = (listTitle)
        return convertView
    }

    override fun getChildrenCount(listPos: Int): Int {
        return expandableListDetail[expandableListTitle[listPos]]!!.size
    }

    override fun getChild(listPos: Int, expListPos: Int): Any {
        return expandableListDetail[expandableListTitle[listPos]]!![expListPos]
    }

    override fun getGroupId(listPos: Int): Long {
        return listPos.toLong()
    }

    @Suppress("NAME_SHADOWING")
    override fun getChildView(listPos: Int, expListPos: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val expandedListText = getChild(listPos, expListPos)
        var convertView = convertView
        if (convertView == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_item, null)
        }
        val expandedListTextView : TextView = convertView!!.findViewById<TextView>(R.id.expandedListItem)
        expandedListTextView.text = expandedListText as CharSequence
        return convertView
    }

    override fun getChildId(listPos: Int, expListPos: Int): Long {
        return expListPos.toLong()
    }

    override fun getGroupCount(): Int {
        return expandableListTitle.size
    }

}