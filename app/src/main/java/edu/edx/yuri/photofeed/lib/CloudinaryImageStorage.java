package edu.edx.yuri.photofeed.lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.Utils;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.util.Map;

import edu.edx.yuri.photofeed.lib.base.EventBus;
import edu.edx.yuri.photofeed.lib.base.ImageStorage;
import edu.edx.yuri.photofeed.lib.base.ImageStorageFinishedListener;

/**
 * Created by yuri_ on 28/12/2017.
 */

public class CloudinaryImageStorage implements ImageStorage{

    private EventBus eventBus;
    private Cloudinary cloudinary;

    public CloudinaryImageStorage(Context context, EventBus eventBus) {
        this.eventBus = eventBus;
        this.cloudinary = new Cloudinary(Utils.cloudinaryUrlFromContext(context));
    }

    @Override
    public String getImageUrl(String id) {
        return cloudinary.url().generate(id);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void upload(final File file, final String id, final ImageStorageFinishedListener listener){
        new AsyncTask<Void, Void, Void>() {
            boolean success = false;
            @Override
            protected Void doInBackground(Void... voids) {
                Map params = ObjectUtils.asMap("public_id", id);

                try {
                    cloudinary.uploader().upload(file, params);
                    success = true;
                } catch (Exception e) {
                    listener.onError(e.getMessage());
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (success) {
                    listener.onSuccess();
                }
            }
        }.execute();
    }
}
