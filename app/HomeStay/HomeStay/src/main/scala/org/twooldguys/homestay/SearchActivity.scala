package org.twooldguys.homestay

import android.support.v7.app.ActionBarActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.widget.{RadioButton, RadioGroup}
import com.mongodb.BasicDBObject

class SearchActivity extends ActionBarActivity {
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search)
    val map: GoogleMap =
      (getFragmentManager.findFragmentById(R.id.map).asInstanceOf[MapFragment]).getMap
    val ucdavis: LatLng = new LatLng(38.54, -121.75)
    map.setMyLocationEnabled(true)
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(ucdavis, 13))
    map.addMarker(
      new MarkerOptions()
        .title("UC Davis")
        .snippet("Where the famous Karl Levitt works")
        .position(ucdavis)
    )
  }

  override def onCreateOptionsMenu(menu: Menu): Boolean = {
    getMenuInflater.inflate(R.menu.search, menu)
    true
  }

  override def onOptionsItemSelected(item: MenuItem): Boolean = item.getItemId match {
    case R.id.action_search => {
      val smokingPref = selectedPref(R.id.pref_smoking_group)
      val petsPref = selectedPref(R.id.pref_pets_group)
      val familyPref = selectedPref(R.id.pref_family_group)
      println("searching")
      new SearchPrefs(this).execute(
        new BasicDBObject("smoking", smokingPref),
        new BasicDBObject("pets", petsPref),
        new BasicDBObject("family", familyPref)
      )
      true
    }
    case _ => super.onOptionsItemSelected(item)
  }

  def selectedPref(id: Int): String = {
    val group = findViewById(id).asInstanceOf[RadioGroup]
    val buttonId = group.getCheckedRadioButtonId
    val button = findViewById(buttonId).asInstanceOf[RadioButton]
    button.getText.toString
  }
}

