package edu.edx.yuri.photofeed.photolist.ui.adapters;

import android.widget.ImageView;

import edu.edx.yuri.photofeed.entities.Photo;

/**
 * Created by yuri_ on 03/01/2018.
 */

public interface OnItemClickListener {

    void onPlaceClick(Photo photo);
    void onShareClick(Photo photo, ImageView img);
    void onDeleteClick(Photo photo);

}
