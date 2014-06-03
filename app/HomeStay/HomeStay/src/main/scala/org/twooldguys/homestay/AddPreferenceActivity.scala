package org.twooldguys.homestay

import android.support.v7.app.ActionBarActivity
import android.os.{AsyncTask, Bundle}
import android.view.{View, Menu, MenuItem}
import android.widget._
import android.widget.AdapterView.OnItemSelectedListener

import com.mongodb._
import scalaz._
import Scalaz._
import android.view.View.OnClickListener
import android.content.Context


//This is broken because scala doesn't like to play with varags and inheritance apparently.
class CreatePreference(context: Context) extends AsyncTask[AnyRef, Void, String] {
  override protected def doInBackground(ps: AnyRef*): String = {
    val prefs = ps.asInstanceOf[Seq[BasicDBObject]]
    val uri = new MongoClientURI("mongodb://twooldguys:WowSoOld@ds041218.mongolab.com:41218/homestay")
    val client = new MongoClient(uri)
    val prefDB = client.getDB("homestay").getCollection("hostPreferences")
    for (pref <- prefs) {
      prefDB.insert(pref)
    }
    "Good to go!"
  }

  override def onPostExecute(result: String) =
    Toast.makeText(context.getApplicationContext, result, Toast.LENGTH_SHORT).show
}

class AddPreferenceActivity extends ActionBarActivity {
  lazy val spinnerAndVals = List(
    (R.id.preference_smoking, R.array.preference_smoking),
    (R.id.preference_pets, R.array.preference_pets),
    (R.id.preference_children, R.array.preference_children),
    (R.id.preference_diet, R.array.preference_diet),
    (R.id.preference_religion, R.array.preference_religion),
    (R.id.preference_eoa, R.array.preference_eoa)
  )

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_preference)
//    createNewPreference

    val create = findViewById(R.id.create_new_preference).asInstanceOf[Button]
    create.setOnClickListener(new OnClickListener {
      override def onClick(v: View): Unit = createNewPreference
    })

    for ((id, arr) <- spinnerAndVals) {
      setupSpinners(id, arr)
      val spinner = findViewById(id).asInstanceOf[Spinner]
    }
  }

  def smoking:  String = spinnerValue(R.id.preference_smoking)
  def pets:     String = spinnerValue(R.id.preference_pets)
  def children: String = spinnerValue(R.id.preference_children)
  def diet:     String = spinnerValue(R.id.preference_diet)
  def religion: String = spinnerValue(R.id.preference_religion)
  def eoa:      String = spinnerValue(R.id.preference_eoa)

  def spinnerValue(id: Int): String = {
    val spinner = findViewById(id).asInstanceOf[Spinner]
    spinner.getSelectedItem.toString
  }

  def setupSpinners(id: Int, array: Int) = {
    val spinner = findViewById(id).asInstanceOf[Spinner]
    val adapter = ArrayAdapter.createFromResource(this, array, android.R.layout.simple_spinner_item)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinner.setAdapter(adapter)
  }

  def createNewPreference = {
    val pref = new BasicDBObject("smoking", smoking)
      .append("pets", pets)
      .append("children", children)
      .append("diet", diet)
      .append("religion", religion)
      .append("eoa", eoa)
    new CreatePreference(this).execute(pref)
  }
}
