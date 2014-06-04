package org.twooldguys.homestay

import android.content.{Intent, Context}
import android.support.v7.app.ActionBarActivity
import android.os.Bundle
import android.view._
import android.widget._

import scala.collection.immutable._
import scala.collection.JavaConversions._
import scala.concurrent._
import ExecutionContext.Implicits.global
import com.mongodb.{BasicDBObject, DBCollection, MongoClient, MongoClientURI}
import scala.util.{Failure, Success}
import android.app.ListActivity

class HostActivity extends ActionBarActivity {

  // This is temporary.
  val p = List(
  ("Upstairs bedroom", new HostPreference(NonSmoking, YesPets, NoChild, Omnivore, Agnostic, Area)),
  ("In-Law Suite", new HostPreference(EnjoysSmoking, YesPets, ThreePlusChild, Omnivore, Agnostic, Area))
  )

  protected override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_host)

    val listView = findViewById(R.id.preferences_list).asInstanceOf[ListView]
    val adapter = new ArrayAdapter[String](this, android.R.layout.simple_list_item_1)
    listView.setAdapter(adapter)

    val prefs = Future {
      allPrefs
    }
    prefs onComplete {
      case Success(ps) => {
        // Get the intent.
        //    val intent = getIntent
        //    val prefs = intent.getStringArrayExtra("prefs")
        // Grab the ListView
        adapter.addAll(ps.toList)
//        val adapter = new ArrayAdapter[String](this, android.R.layout.simple_list_item_1, ps)
////        listView.setAdapter(adapter)
      }
      case Failure(err) => Toast.makeText(this, err.getMessage, Toast.LENGTH_SHORT)
    }

    //    // Should be able to swap this out with a db-backed collection and the rest just works.
    ////    val f: Future[Array[String]] = Future {
    //      val prefs = allPrefs
    ////    }
    ////    f onComplete {
    ////      case Success(prefs) => {
    //        for (pref <- prefs) {
    //          Toast.makeText(this, pref, Toast.LENGTH_SHORT)
    //        }
////      }
////      case Failure(reason) => Toast.makeText(this, reason.getMessage, Toast.LENGTH_LONG)
////    }
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

  def allPrefs: Array[String] = {
    val uri = new MongoClientURI("mongodb://twooldguys:WowSoOld@ds041218.mongolab.com:41218/homestay")
    val client = new MongoClient(uri)
    val prefDB = client.getDB("homestay").getCollection("hostPreferences")
    val cursor = prefDB.find(new BasicDBObject, new BasicDBObject("name", 1))
    cursor.toArray.map(_.get("name").toString).toArray
  }
  //  val allPrefs: Array[String] = p.map(_._1).toArray

  def addPreference =
    startActivity(new Intent(this, classOf[AddPreferenceActivity]))
}
