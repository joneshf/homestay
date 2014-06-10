package org.twooldguys.homestay

import android.support.v7.app.ActionBarActivity
import android.os.{AsyncTask, Bundle}
import android.view.{View, Menu, MenuItem}
import android.widget._
import android.widget.AdapterView.OnItemSelectedListener

import com.mongodb._
import scala.concurrent.{ExecutionContext, Future}
import ExecutionContext.Implicits.global
 import android.view.View.OnClickListener
import scala.util.{Failure, Success}

class AddPreferenceActivity extends ActionBarActivity {
  lazy val spinnerAndVals = List(
    (R.id.preference_smoking, R.array.preference_smoking),
    (R.id.preference_pets, R.array.preference_pets),
    (R.id.preference_family, R.array.preference_family),
    (R.id.preference_diet, R.array.preference_diet),
    (R.id.preference_religion, R.array.preference_religion),
    (R.id.preference_eoa, R.array.preference_eoa)
  )

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_preference)

    val create = findViewById(R.id.create_new_preference).asInstanceOf[Button]
    create.setOnClickListener(new OnClickListener {
      println("Setting listener")
      override def onClick(v: View): Unit = createNewPreference onComplete {
        case Success(_) =>
          Toast.makeText(getApplicationContext, "Preference added", Toast.LENGTH_SHORT)
        case Failure(e) =>
          Toast.makeText(getApplicationContext, e.getMessage, Toast.LENGTH_SHORT)
      }
    })

    for ((id, arr) <- spinnerAndVals) {
      setupSpinners(id, arr)
    }
  }

  def smoking:  String = spinnerValue(R.id.preference_smoking)
  def pets:     String = spinnerValue(R.id.preference_pets)
  def children: String = spinnerValue(R.id.preference_family)
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
    val name = findViewById(R.id.preference_name).asInstanceOf[TextView]
    name.getText.toString
    val pref = new BasicDBObject("name", name)
      .append("smoking", smoking)
      .append("pets", pets)
      .append("children", children)
      .append("diet", diet)
      .append("religion", religion)
      .append("eoa", eoa)
    println("Got the pref all setup")
    Future {
      println("Creating uri")
      val uri =
        new MongoClientURI("mongodb://twooldguys:WowSoOld@ds041218.mongolab.com:41218/homestay")
      println("Creating client")
      val client = new MongoClient(uri)
      println("getting collection")
      val prefDB = client.getDB("homestay").getCollection("hostPreferences")
      println("inserting")
      prefDB.insert(pref)
      println("DONE!")
    }
  }
}
