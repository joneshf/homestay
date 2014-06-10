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
import android.app.Activity

class HomeStayActivity extends ActionBarActivity {
  protected override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home_stay)
  }

  def gotoStudent(view: View) = gotoActivity(new SearchActivity)

  def gotoHost(view: View) = gotoActivity(new HostActivity)

  def gotoActivity(act: Activity) = {
    val intent: Intent = new Intent(this, act.getClass)
    startActivity(intent)
  }
}
