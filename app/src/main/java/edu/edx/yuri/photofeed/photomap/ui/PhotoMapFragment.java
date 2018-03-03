package edu.edx.yuri.photofeed.photomap.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.edx.yuri.photofeed.PhotoFeedApp;
import edu.edx.yuri.photofeed.R;
import edu.edx.yuri.photofeed.domain.Util;
import edu.edx.yuri.photofeed.entities.Photo;
import edu.edx.yuri.photofeed.lib.base.ImageLoader;
import edu.edx.yuri.photofeed.photomap.PhotoMapPresenter;

/**
 * Created by yuri_ on 03/01/2018.
 */

public class PhotoMapFragment extends Fragment implements PhotoMapView, OnMapReadyCallback, GoogleMap.InfoWindowAdapter {

    @BindView(R.id.container)
    FrameLayout container;
    Unbinder unbinder;

    @Inject
    Util utils;

    @Inject
    ImageLoader imageLoader;

    @Inject
    PhotoMapPresenter presenter;

    private GoogleMap map;
    private HashMap<Marker, Photo> markers;
    private List<Photo> photoList;
    private static final int PERMISSIONS_REQUEST_LOCATION = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupInjection();

        markers = new HashMap<Marker, Photo>();
        photoList = new ArrayList<>();

        presenter.onCreate();
        presenter.subscribe();
    }

    @Override
    public void onDestroy() {
        presenter.unsubscribe();
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void setupInjection() {

        PhotoFeedApp app = (PhotoFeedApp) getActivity().getApplication();
        app.getPhotoMapComponent(this, this).inject(this);

    }


    @Override
    public void addPhoto(Photo photo) {
        photoList.add(photo);
        for (Photo photot : photoList) {
            Log.d("d", "phototList(i).getemail(): " + photot.getEmail());
        }
    }

    @Override
    public void removePhoto(Photo photo) {
        for (Map.Entry<Marker, Photo> entry : markers.entrySet()) {
            Photo currentPhoto = entry.getValue();
            Marker currentMarker = entry.getKey();
            if (currentPhoto.getId().equals(photo.getId())) {
                currentMarker.remove();
                markers.remove(currentMarker);
                break;
            }
        }
    }

    @Override
    public void onPhotosError(String error) {
        Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View window = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.info_window, null);
        Photo photo = markers.get(marker);
        render(window, photo);

        return window;
    }

    private void render(View view, final Photo currentPhoto) {
        CircleImageView imgAvatar = (CircleImageView) view.findViewById(R.id.imgAvatar);
        TextView txtUser = (TextView) view.findViewById(R.id.txtUser);
        final ImageView imgMain = (ImageView) view.findViewById(R.id.imgMain);
        String userEmail = !TextUtils.isEmpty(currentPhoto.getEmail()) ? currentPhoto.getEmail() : "";

        imageLoader.load(imgMain, currentPhoto.getUrl());
        imageLoader.load(imgAvatar, utils.getAvatarUrl(userEmail));
        txtUser.setText(currentPhoto.getEmail());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setInfoWindowAdapter(this);
        Log.d("d", "Entrou no onMapReady");
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_LOCATION);
            Log.d("d", "Nao tinha as permissoes.");
        } else {
            Log.d("d", "Tem as permissoes");
            map.setMyLocationEnabled(true);
            LatLng location = null;
            for (Photo photo : photoList) {
                location = new LatLng(photo.getLatitutde(), photo.getLongitude());
                Marker marker = map.addMarker(new MarkerOptions().position(location));
                markers.put(marker, photo);
            }
            if (location != null) map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (map != null) {
                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        map.setMyLocationEnabled(true);
                    }
                }
                return;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
