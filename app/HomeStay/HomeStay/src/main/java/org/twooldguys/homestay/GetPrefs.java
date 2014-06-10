package org.twooldguys.homestay;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joneshf on 6/10/14.
 */
public class GetPrefs extends AsyncTask<DBObject, Void, List<DBObject>> {
    protected Activity act;

    public GetPrefs(Activity act) {
        this.act = act;
    }

    @Override
    protected List<DBObject> doInBackground(DBObject... objs) {
        List<DBObject> list = new ArrayList<DBObject>();
        MongoClientURI uri = new MongoClientURI(
                "mongodb://twooldguys:WowSoOld@ds041218.mongolab.com:41218/homestay"
        );
        MongoClient client = null;
        DBObject query = new BasicDBObject();
        for (DBObject obj : objs) {
            query.putAll(obj.toMap());
        }
        System.out.println(query.toString());
        try {
            client = new MongoClient(uri);
            DBCollection prefDB = client.getDB("homestay").getCollection("hostPreferences");
            DBCursor cursor = prefDB.find(query);
            list = cursor.toArray();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return list;
    }

    protected ArrayList<String> project(List<DBObject> objs) {
        ArrayList<String> list = new ArrayList<String>();
        for (DBObject obj : objs) {
            list.add(obj.get("name").toString());
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<DBObject> objs) {
        ArrayList<String> result = project(objs);
        ListView listView = (ListView) act.findViewById(R.id.preferences_list);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(act, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        adapter.addAll(result);
    }
}
