package edu.gatech.seclass.sdpcryptogram;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by chaiyixiao on 07/07/2017.
 */

public class FirebaseGetInstanceClass {

    private static FirebaseDatabase firebaseDatabase;


    public static  FirebaseDatabase GetFirebaseDatabaseInstance(){

        if (firebaseDatabase == null){
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.setPersistenceEnabled(true);
//            firebaseDatabase.goOffline();
//            firebaseDatabase.getReference().removeValue();
//            firebaseDatabase.getReference().keepSynced(true);
        }
        return firebaseDatabase;
    }
}
