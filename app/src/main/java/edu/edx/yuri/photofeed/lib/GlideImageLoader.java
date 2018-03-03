package edu.edx.yuri.photofeed.lib;

import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import edu.edx.yuri.photofeed.lib.base.ImageLoader;

/**
 * Created by yuri_ on 28/12/2017.
 */

public class GlideImageLoader implements ImageLoader {

    private RequestManager glideRequestManager;

    public void setLoaderContext(Fragment fragment) {
        this.glideRequestManager = Glide.with(fragment);
    }

    @Override
    public void load(ImageView imageView, String URL) {
        glideRequestManager
                .load(URL)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .apply(RequestOptions.overrideOf(800,800))
                .apply(RequestOptions.centerCropTransform())
                .into(imageView);
    }
}
