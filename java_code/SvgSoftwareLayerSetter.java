package com.amilutinovic.inat;

/**
 * Created by milutinac on 17.1.17..
 */

import android.annotation.TargetApi;
import android.graphics.drawable.PictureDrawable;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.Target;

/**
 * Listener which updates the {@link ImageView} to be software rendered,
 * because {@link com.caverock.androidsvg.SVG SVG}/{@link android.graphics.Picture Picture}
 * can't render on a hardware backed {@link android.graphics.Canvas Canvas}.
 *
 * @param <T> not used, here to prevent unchecked warnings at usage
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SvgSoftwareLayerSetter<T> implements RequestListener<T, PictureDrawable> {

	private SpinningDialog spinner;

	public SvgSoftwareLayerSetter(SpinningDialog spinner) {
		this.spinner = spinner;
	}

	@Override
	public boolean onException(Exception e, T model, Target<PictureDrawable> target, boolean isFirstResource) {
		ImageView view = ((ImageViewTarget<?>) target).getView();
		if (Build.VERSION_CODES.HONEYCOMB <= Build.VERSION.SDK_INT) {
			view.setLayerType(ImageView.LAYER_TYPE_NONE, null);
		}
		if (spinner != null){
			spinner.hide();
		}
		return false;
	}

	@Override
	public boolean onResourceReady(PictureDrawable resource, T model, Target<PictureDrawable> target,
								   boolean isFromMemoryCache, boolean isFirstResource) {
		ImageView view = ((ImageViewTarget<?>) target).getView();
		if (Build.VERSION_CODES.HONEYCOMB <= Build.VERSION.SDK_INT) {
			view.setLayerType(ImageView.LAYER_TYPE_SOFTWARE, null);
		}
		if (spinner != null){
			spinner.hide();
		}
		return false;
	}
}