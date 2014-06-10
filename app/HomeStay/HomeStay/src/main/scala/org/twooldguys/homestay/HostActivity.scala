package org.twooldguys.homestay

import android.content.{Intent, Context}
import android.support.v7.app.ActionBarActivity
import android.os.{AsyncTask, Bundle}
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
  implicit def funToRunnable(fun: () => Unit) = new Runnable() { def run() = fun() }

  protected override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_host)

    new GetPrefs(this).execute()
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

  def addPreference =
    startActivity(new Intent(this, classOf[AddPreferenceActivity]))
}
