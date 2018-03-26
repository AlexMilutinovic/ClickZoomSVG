package com.amilutinovic.inat;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import at.lukle.clickableareasimage.ClickableArea;
import at.lukle.clickableareasimage.ClickableAreasImage;
import at.lukle.clickableareasimage.OnClickableAreaClickedListener;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorldMapFragment extends Fragment {

	private static final String TAG = WorldMapFragment.class.getSimpleName();

	private ImageView touchImageView;
	private DisplayMetrics metrics;
	private OnClickableAreaClickedListener listener;

	public WorldMapFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_world_map, container, false);

		SVG svg = SVGParser.getSVGFromResource(getResources(),
				R.raw.world_map);
		touchImageView = (ImageView) view.findViewById(R.id.touchImageView);
		//touchImageView.setMaxZoom(16);
		PictureDrawable image = svg.createPictureDrawable();

		Bitmap b = drawableToBitmap(image);
		touchImageView.setImageBitmap(b);
		ClickableAreasImage clickableAreasImage = new ClickableAreasImage(new PhotoViewAttacher(touchImageView), listener);
		// Initialize your clickable area list
		List<ClickableArea> clickableAreas = new ArrayList<>();

		// Define your clickable areas
		// parameter values (pixels): (x coordinate, y coordinate, width, h	eight) and assign an object to it
		Map<String, FintemCity> cities = svg.getCities();
		for (String key : cities.keySet()){
			clickableAreas.add(cities.get(key).toClickableArea(getMetrics()));
		}
		// Set your clickable areas to the image
		clickableAreasImage.setClickableAreas(clickableAreas);

		return view;
	}

	private DisplayMetrics getMetrics(){
		if (metrics == null){
			metrics = getResources().getDisplayMetrics();
		}
		return metrics;
	}

	public static Bitmap drawableToBitmap (Drawable drawable) {
		Bitmap bitmap = null;

		if (drawable instanceof BitmapDrawable) {
			BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
			if(bitmapDrawable.getBitmap() != null) {
				return bitmapDrawable.getBitmap();
			}
		}

		if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
			bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
		} else {
			bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
		}

		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	@Override
	public void onResume() {
		super.onResume();
		touchImageView.requestFocus();
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		try{
			listener = (OnClickableAreaClickedListener) context;
		} catch (ClassCastException e) {
			throw new ClassCastException("Parent activity must implement interface "
					+ OnClickableAreaClickedListener.class.getSimpleName());
		}
	}
}
