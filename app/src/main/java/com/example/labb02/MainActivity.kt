package com.example.labb02

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.TextView
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
// import androidx.core.app.ComponentActivity.ExtraData
// import androidx.core.content.ContextCompat.getSystemService
// import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MainActivity : AppCompatActivity() {

    private lateinit var expandableListView: ExpandableListView
    private lateinit var expandableListAdapter: ExpandableListAdapter
    private lateinit var expandableListTitle: ArrayList<String>
    private lateinit var expandableListDetail: HashMap<String, ArrayList<String>>


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
                Toast.makeText(applicationContext,
                    expandableListTitle[groupPos] + " List Expanded.",
                    Toast.LENGTH_SHORT).show()
                path.text = "/${expandableListTitle[groupPos]}"
            }
        )

        expandableListView.setOnGroupCollapseListener(
            @Override
            fun (groupPos: Int) {
                Toast.makeText(applicationContext,
                    expandableListTitle[groupPos] + " List Collapsed.",
                    Toast.LENGTH_SHORT).show()
                path.text = "/${expandableListTitle[groupPos]}"
            }
        )

        expandableListView.setOnChildClickListener { expandableListView, view, groupPos, childPos, id ->
            Toast.makeText(
                applicationContext,
                expandableListTitle[groupPos]
                        + " -> "
                        + expandableListDetail[expandableListTitle[groupPos]]!![childPos],
                Toast.LENGTH_SHORT
            ).show()
            path.text = "/${expandableListTitle[groupPos]}/${expandableListDetail[expandableListTitle[groupPos]]!![childPos]}"
            false
        }




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
        dark.add("indigo")

        colorList["Light"] = light
        colorList["Medium"] = medium
        colorList["Dark"] = dark

        return colorList
    }
}
