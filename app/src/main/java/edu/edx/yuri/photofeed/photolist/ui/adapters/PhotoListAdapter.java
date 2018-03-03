package edu.edx.yuri.photofeed.photolist.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.edx.yuri.photofeed.R;
import edu.edx.yuri.photofeed.domain.Util;
import edu.edx.yuri.photofeed.entities.Photo;
import edu.edx.yuri.photofeed.lib.base.ImageLoader;

/**
 * Created by yuri_ on 03/01/2018.
 */

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {

    private Util utils;
    private List<Photo> photoList;
    private ImageLoader imageLoader;
    private OnItemClickListener onItemClickListener;

    public PhotoListAdapter(Util utils, List<Photo> photoList, ImageLoader imageLoader, OnItemClickListener onItemClickListener) {
        this.utils = utils;
        this.photoList = photoList;
        this.imageLoader = imageLoader;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_photos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Photo currentPhoto = photoList.get(position);
        holder.setOnItemClickListener(currentPhoto, onItemClickListener);

        imageLoader.load(holder.imgMain, currentPhoto.getUrl());
        imageLoader.load(holder.imgAvatar, TextUtils.isEmpty(currentPhoto.getEmail()) ? "" :utils.getAvatarUrl(currentPhoto.getEmail()));
        holder.txtUser.setText(currentPhoto.getEmail());
        double lat = currentPhoto.getLatitutde();
        double lng = currentPhoto.getLongitude();

        if (lat != 0.0 && lng != 0.0) {
            holder.txtPlace.setText(utils.getFromLocation(lat, lng));
            holder.txtPlace.setVisibility(View.VISIBLE);
        } else {
            holder.txtPlace.setVisibility(View.GONE);
        }

        if (currentPhoto.isPublishedByMe()){
            holder.btnDelete.setVisibility(View.VISIBLE);
        } else {
            holder.btnDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public void addPhoto(Photo photo) {
        photoList.add(0, photo);
        notifyDataSetChanged();
    }

    public void removePhoto(Photo photo) {
        photoList.remove(photo);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgAvatar)
        CircleImageView imgAvatar;
        @BindView(R.id.txtUser)
        TextView txtUser;
        @BindView(R.id.imgMain)
        ImageView imgMain;
        @BindView(R.id.txtPlace)
        TextView txtPlace;
        @BindView(R.id.imgShare)
        ImageButton btnShare;
        @BindView(R.id.imgDelete)
        ImageButton btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setOnItemClickListener(final Photo photo,
                                           final OnItemClickListener listener) {
            txtPlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onPlaceClick(photo);
                }
            });
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onShareClick(photo, imgMain);
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onDeleteClick(photo);
                }
            });
        }

    }

}
