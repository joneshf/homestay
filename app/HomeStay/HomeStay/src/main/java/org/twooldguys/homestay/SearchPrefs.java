package org.twooldguys.homestay;

import android.app.Activity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joneshf on 6/10/14.
 */
public class SearchPrefs extends GetPrefs {

    public SearchPrefs(Activity act) {
        super(act);
    }

    @Override
    protected void onPostExecute(List<DBObject> objs) {
        System.out.println("Done searching");
        System.out.println(objs);
        LatLng ucdavis = new LatLng(38.54, -121.75);
        GoogleMap map =
                ((MapFragment) act.getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.clear();
        map.addMarker(
                new MarkerOptions()
                        .title("UC Davis")
                        .snippet("Where the famous Karl Levitt works")
                        .position(ucdavis)
        );
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ucdavis, 13));
        for (DBObject obj: objs) {
            LatLng pos = new LatLng(
                    Double.parseDouble(obj.get("lat").toString()),
                    Double.parseDouble(obj.get("long").toString()));
            map.addMarker(
                    new MarkerOptions()
                    .title(obj.get("name").toString())
                    .position(pos)
            );
        }
    }
}
