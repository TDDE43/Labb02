package com.example.labb02

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    private lateinit var expandableListView: ExpandableListView
    private lateinit var expandableListAdapter: ExpandableListAdapter
    private lateinit var expandableListTitle: ArrayList<String>
    private lateinit var expandableListDetail: HashMap<String, ArrayList<String>>
    private var groupIndex = -1
    private var itemIndex = -1


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        // https://www.journaldev.com/9942/android-expandablelistview-example-tutorial

        val path = findViewById<TextView>(R.id.URI)
        expandableListView = findViewById(R.id.expandedListItem)
        expandableListDetail = createList()
        expandableListTitle = ArrayList(expandableListDetail.keys)
        expandableListAdapter = CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail)
        expandableListView.setAdapter(expandableListAdapter)

        expandableListView.setOnGroupExpandListener(
            @Override
            fun (groupPos: Int) {
                uncheckItems()
                if (groupPos == groupIndex) {
                    checkItem(groupIndex, itemIndex)
                } else if (expandableListView.isGroupExpanded(groupIndex)) {
                    checkItem(groupIndex, itemIndex)
                }
            }
        )

        expandableListView.setOnGroupCollapseListener(
            @Override
            fun (groupPos: Int) {
                uncheckItems()
                if (groupPos != groupIndex && expandableListView.isGroupExpanded(groupIndex)) {
                    checkItem(groupIndex, itemIndex)
                }
            }
        )

        expandableListView.setOnChildClickListener { expandableListView, view, groupPos, childPos, id ->
            path.text = "/${expandableListTitle[groupPos]}/${expandableListDetail[expandableListTitle[groupPos]]!![childPos]}"

            val index = expandableListView.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPos, childPos))
            expandableListView.setItemChecked(index, true)

            true
        }

        path.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0!!.isNotEmpty()){
                    val pathParts = p0.split("/")
                    println(pathParts)

                    if (pathParts[0] == "" && pathParts[1] in expandableListTitle) {
                        path.setBackgroundColor(Color.TRANSPARENT)
                        val groupToOpen = expandableListTitle.indexOf(pathParts[1])
                        expandableListView.expandGroup(groupToOpen)

                        if (groupToOpen == groupIndex) checkItem(groupIndex, itemIndex)

                        if (pathParts.size > 2 && expandableListDetail[pathParts[1]]!!.contains(pathParts[2])) {

                            groupIndex = expandableListTitle.indexOf(pathParts[1])
                            itemIndex = expandableListDetail[pathParts[1]]!!.indexOf(pathParts[2])

                            checkItem(groupIndex, itemIndex)
                        } else if (pathParts.size > 2 && !subStringInList(pathParts[2], expandableListDetail[pathParts[1]] as ArrayList<String>)) {
                            path.setBackgroundColor(Color.RED)
                        }
                    } else if (pathParts[0] == "") {
                        if (subStringInList(pathParts[1], expandableListTitle)) {
                            path.setBackgroundColor(Color.TRANSPARENT)
                        } else {
                            path.setBackgroundColor(Color.RED)
                        }
                        for (i in 0..expandableListTitle.size) {
                            expandableListView.collapseGroup(i)
                        }
                        uncheckItems()
                    } else if (pathParts[0] != "") {
                        path.setBackgroundColor(Color.RED)
                    } else {
                        for (i in 0..expandableListTitle.size) {
                            expandableListView.collapseGroup(i)
                        }
                        uncheckItems()
                    }
                } else {
                    path.text = "/"
                }

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createList(): HashMap<String, ArrayList<String>> {
        val colorList = HashMap<String, ArrayList<String>>()

        val light = ArrayList<String>()
        light.add("Yellow")
        light.add("Cyan")
        light.add("Pink")

        val medium = ArrayList<String>()
        medium.add("Red")
        medium.add("Green")
        medium.add("Blue")

        val dark = ArrayList<String>()
        dark.add("Marine")
        dark.add("Ebony")
        dark.add("Indigo")
        dark.add("Yellow")

        colorList["Light"] = light
        colorList["Medium"] = medium
        colorList["Dark"] = dark

        return colorList
    }

    private fun checkItem(groupPos: Int, itemPos: Int) : Int {
        if (groupPos < 0 || itemPos < 0) return -1
        val index = expandableListView.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPos, itemPos))
        expandableListView.setItemChecked(index, true)
        return index
    }

    private fun uncheckItems() {
        expandableListView.setItemChecked(-1, true)
    }

    private fun subStringInList(str: String, list: ArrayList<String>) : Boolean {
        for (item in list) {
            if (str.length <= item.length && str == item.substring(0, str.length)) {
                return true
            }
        }
        return false
    }
}
