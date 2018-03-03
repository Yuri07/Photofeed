package edu.edx.yuri.photofeed.photolist.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.io.ByteArrayOutputStream;

import com.bumptech.glide.load.resource.bitmap.BitmapDrawableResource;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edu.edx.yuri.photofeed.PhotoFeedApp;
import edu.edx.yuri.photofeed.R;
import edu.edx.yuri.photofeed.entities.Photo;
import edu.edx.yuri.photofeed.photolist.PhotoListPresenter;
import edu.edx.yuri.photofeed.photolist.ui.adapters.OnItemClickListener;
import edu.edx.yuri.photofeed.photolist.ui.adapters.PhotoListAdapter;

/**
 * Created by yuri_ on 03/01/2018.
 */
/*
* onCreate():

The onCreate() method in a Fragment is called after the Activity's onAttachFragment() but before that Fragment's onCreateView().
In this method, you can assign variables, get Intent extras, and anything else that doesn't involve the View hierarchy (i.e. non-graphical initialisations). This is because this method can be called when the Activity's onCreate() is not finished, and so trying to access the View hierarchy here may result in a crash.

onCreateView():

After the onCreate() is called (in the Fragment), the Fragment's onCreateView() is called. You can assign your View variables and do any graphical initialisations. You are expected to return a View from this method, and this is the main UI view, but if your Fragment does not use any layouts or graphics, you can return null (happens by default if you don't override).

onActivityCreated():

As the name states, this is called after the Activity's onCreate() has completed. It is called after onCreateView(), and is mainly used for final initialisations (for example, modifying UI elements).*/
public class PhotoListFragment extends Fragment implements PhotoListView, OnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.container)
    RelativeLayout container;

    Unbinder unbinder;

    @Inject
    PhotoListAdapter adapter;
    @Inject
    PhotoListPresenter presenter;

    public PhotoListFragment() {
        // Required empty public constructor?
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupInjection();
        presenter.onCreate();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        unbinder = ButterKnife.bind(this, view);

        setupRecyclerView();
        presenter.subscribe();

        return view;
    }

    private void setupRecyclerView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

    }

    private void setupInjection() {

        PhotoFeedApp app = (PhotoFeedApp) getActivity().getApplication();
        app.getPhotoListComponent(this, this, this).inject(this);

    }

    @Override
    public void showList() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideList() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void addPhoto(Photo photo) {
        adapter.addPhoto(photo);
    }

    @Override
    public void removePhoto(Photo photo) {
        adapter.removePhoto(photo);
    }

    @Override
    public void onPhotosError(String error) {
        Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onPlaceClick(Photo photo) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:" + photo.getLatitutde() +"," + photo.getLongitude()));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onShareClick(Photo photo, ImageView img) {
        Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();//casting era feito com (GlideBitmapDwawable) no codigo original do noah do curso edx
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, null, null);
        Uri imageUri =  Uri.parse(path);

        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(share, getString(R.string.photolist_message_share)));
    }

    @Override
    public void onDeleteClick(Photo photo) {
        presenter.removePhoto(photo);
    }

    @Override
    public void onDestroy() {
        presenter.unsubscribe();
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
