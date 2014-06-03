package org.twooldguys.homestay

import android.content.{Intent, Context}
import android.support.v7.app.ActionBarActivity
import android.os.Bundle
import android.view._
import android.widget._

import scala.collection.immutable._

class HostActivity extends ActionBarActivity {
  // This is temporary.
  val p = List(
    ("Upstairs bedroom", new HostPreference(NonSmoking, YesPets, NoChild, Omnivore, Agnostic, Area)),
    ("In-Law Suite", new HostPreference(EnjoysSmoking, YesPets, ThreePlusChild, Omnivore, Agnostic, Area))
  )

  protected override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_host)

    // Grab the ListView
    val listView = findViewById(R.id.preferences_list).asInstanceOf[ListView]
    // Should be able to swap this out with a db-backed collection and the rest just works.
    val prefs = allPrefs
    val adapter = new ArrayAdapter[String](this, android.R.layout.simple_list_item_1, prefs)
    listView.setAdapter(adapter)
  }

  override def onCreateOptionsMenu(menu: Menu): Boolean = {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater.inflate(R.menu.host, menu)
    true
  }

  override def onOptionsItemSelected(item: MenuItem): Boolean = item.getItemId() match {
    case R.id.action_new_preference => {
      addPreference
      true
    }
    case _ => super.onOptionsItemSelected(item)
  }

  val allPrefs: Array[String] = p.map(_._1).toArray

  def addPreference =
    startActivity(new Intent(this, classOf[AddPreferenceActivity]))
}
