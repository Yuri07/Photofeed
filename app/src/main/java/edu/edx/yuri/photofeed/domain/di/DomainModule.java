package edu.edx.yuri.photofeed.domain.di;

import android.content.Context;
import android.location.Geocoder;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.edx.yuri.photofeed.domain.FirebaseAPI;
import edu.edx.yuri.photofeed.domain.Util;

/**
 * Created by yuri_ on 14/12/2017.
 */

@Module
public class DomainModule {

    @Provides
    @Singleton
    FirebaseAPI providesFirebaseAPI(DatabaseReference databaseReference) {
        return new FirebaseAPI(databaseReference);
    }

    @Provides
    @Singleton
    DatabaseReference providesFirebase() {
        return FirebaseDatabase.getInstance().getReference();
    }

    @Provides
    @Singleton
    Util providesUtil(Geocoder geocoder) {
        return new Util(geocoder);
    }

    @Provides
    @Singleton
    Geocoder providesGeocoder(Context context) {
        return new Geocoder(context);
    }

}
