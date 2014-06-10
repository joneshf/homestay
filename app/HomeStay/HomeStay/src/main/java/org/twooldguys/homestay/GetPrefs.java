package org.twooldguys.homestay;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by joneshf on 6/10/14.
 */
public class GetPrefs extends AsyncTask<Void, Void, String[]> {
    private HostActivity act;

    public GetPrefs(HostActivity act) {
        this.act = act;
    }

    @Override
    protected String[] doInBackground(Void... foo) {
        ArrayList<String> list = new ArrayList<String>();
        MongoClientURI uri = new MongoClientURI(
                "mongodb://twooldguys:WowSoOld@ds041218.mongolab.com:41218/homestay"
        );
        MongoClient client = null;
        try {
            client = new MongoClient(uri);
            DBCollection prefDB = client.getDB("homestay").getCollection("hostPreferences");
            DBCursor cursor = prefDB.find();
            for (DBObject obj : cursor.toArray()) {
                list.add(obj.get("name").toString());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return list.toArray(new String[list.size()]);
    }

    @Override
    protected void onPostExecute(String[] result) {
        ListView listView = (ListView) act.findViewById(R.id.preferences_list);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(act, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        adapter.addAll(result);
    }
}
