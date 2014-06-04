package org.twooldguys.homestay

import android.content.Intent
import android.support.v7.app.ActionBarActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.{ArrayAdapter, Toast, ListView}
import scala.concurrent.{ExecutionContext, Future}
import ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scala.collection.JavaConversions._
import com.mongodb.{BasicDBObject, MongoClient, MongoClientURI}

class HomeStayActivity extends ActionBarActivity {
  protected override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home_stay)
  }

  def gotoStudent(view: View) {
    val intent: Intent = new Intent(this, classOf[SearchActivity])
    startActivity(intent)
  }

  def gotoHost(view: View) {
//    // Should be able to swap this out with a db-backed collection and the rest just works.
//    val f: Future[Array[String]] = Future {
//      //    val prefs = allPrefs
//      allPrefs
//    }
//    f onComplete {
//      case Success(prefs) => {
//        for (pref <- prefs) {
//          Toast.makeText(this, pref, Toast.LENGTH_SHORT)
//        }
//      }
//      case Failure(reason) => Toast.makeText(this, reason.getMessage, Toast.LENGTH_LONG)
//    }
    val intent: Intent = new Intent(this, classOf[HostActivity])
//    intent.putExtra("prefs", prefs)
    startActivity(intent)
  }
  def allPrefs: Array[String] = {
    val uri = new MongoClientURI("mongodb://twooldguys:WowSoOld@ds041218.mongolab.com:41218/homestay")
    val client = new MongoClient(uri)
    val prefDB = client.getDB("homestay").getCollection("hostPreferences")
    val cursor = prefDB.find(new BasicDBObject, new BasicDBObject("name", 1))
    cursor.toArray.map(_.get("name").toString).toArray
  }
}
