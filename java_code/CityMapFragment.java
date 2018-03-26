package com.amilutinovic.inat;


import android.content.ContentResolver;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;

import java.io.File;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class CityMapFragment extends Fragment {

	private static final String TAG = CityMapFragment.class.getSimpleName();
	private TouchImageView touchImageView;
	private SpinningDialog spinner;
	private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;

	private ImageView imageViewRes;
	private ImageView imageViewNet;

	private int city;

	public CityMapFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_city_map, container, false);
		touchImageView = (TouchImageView) view.findViewById(R.id.touchImageView);
		touchImageView.setMaxZoom(20);
		if (!getArguments().containsKey("city")){
			Toast.makeText(getActivity(), "Error parsing city map", Toast.LENGTH_SHORT).show();
			getActivity().getSupportFragmentManager().popBackStack();
		}

		int map = getResources().getIdentifier(getArguments().getString("city"),
				"raw", getActivity().getPackageName());
		if (map == 0) {
			Toast.makeText(getActivity(), "Error rendering city map", Toast.LENGTH_SHORT).show();
			getActivity().getSupportFragmentManager().popBackStack();
		} else {
			city = map;
			FintemPreferences prefs = new FintemPreferences(getActivity());
			prefs.setLastMapUsed(getArguments().getString("city"));
			spinner = new SpinningDialog(getActivity());
			spinner.setMessage("Loading city metro map");
			spinner.setCancelable(true);
			spinner.show();

			requestBuilder = Glide.with(this)
					.using(Glide.buildStreamModelLoader(Uri.class, getActivity()), InputStream.class)
					.from(Uri.class)
					.as(SVG.class)
					.transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
					.sourceEncoder(new StreamEncoder())
					.cacheDecoder(new FileToStreamDecoder<>(new SvgDecoder()))
					.decoder(new SvgDecoder())
					.placeholder(android.R.drawable.screen_background_light_transparent)
					.animate(android.R.anim.fade_in)
					.listener(new SvgSoftwareLayerSetter<Uri>(spinner));
		}
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		reload();
	}

	public void clearCache(View v) {
		Log.w(TAG, "clearing cache");
		Glide.get(getActivity()).clearMemory();
		File cacheDir = Glide.getPhotoCacheDir(getActivity());
		if (cacheDir.isDirectory()) {
			for (File child : cacheDir.listFiles()) {
				if (!child.delete()) {
					Log.w(TAG, "cannot delete: " + child);
				}
			}
		}
		reload();
	}

	public void cycleScaleType(View v) {
		ImageView.ScaleType curr = imageViewRes.getScaleType();
		Log.w(TAG, "cycle: current=" + curr);
		ImageView.ScaleType[] all = ImageView.ScaleType.values();
		int nextOrdinal = (curr.ordinal() + 1) % all.length;
		ImageView.ScaleType next = all[nextOrdinal];
		Log.w(TAG, "cycle: next=" + next);
		imageViewRes.setScaleType(next);
		imageViewNet.setScaleType(next);
		reload();
	}

	private void reload() {
		Log.w(TAG, "reloading");
		loadRes();
	}

	private void loadRes() {
		Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getActivity().getPackageName() + "/"
				+ this.city);
		requestBuilder
				.diskCacheStrategy(DiskCacheStrategy.NONE)
				// SVG cannot be serialized so it's not worth to cache it
				// and the getResources() should be fast enough when acquiring the InputStream
				.load(uri)
				.into(touchImageView);
	}

	private void loadNet() {
		Uri uri = Uri.parse("http://www.clker.com/cliparts/u/Z/2/b/a/6/android-toy-h.svg");
		requestBuilder
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				// SVG cannot be serialized so it's not worth to cache it
				.load(uri)
				.into(imageViewNet);
	}
}