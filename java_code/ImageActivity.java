package com.amilutinovic.inat;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import at.lukle.clickableareasimage.OnClickableAreaClickedListener;

public class ImageActivity extends AppCompatActivity implements OnClickableAreaClickedListener {

	private static final String TAG = ImageActivity.class.getSimpleName();

	private Toolbar toolbar;
	private ImageView worldMap;
	private ImageView info;
	private FragmentManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);

		manager = getSupportFragmentManager();
		manager.beginTransaction().replace(R.id.container, new WorldMapFragment(),
				WorldMapFragment.class.getSimpleName()).commit();

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		worldMap = (ImageView) findViewById(R.id.world_map_btn);

		FintemPreferences prefs = new FintemPreferences(this);
		String map = prefs.getLastMapUsed();

		if (map != null && !map.isEmpty()){
			Bundle args = new Bundle();
			args.putString("city", map);
			FragmentManager manager = getSupportFragmentManager();
			CityMapFragment fragment = new CityMapFragment();
			fragment.setArguments(args);
			manager.beginTransaction().replace(R.id.container, fragment, CityMapFragment.class.getSimpleName())
					.addToBackStack(null).commit();
		}

		worldMap.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d(TAG, "onClick: world map");
				manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			}
		});

		info = (ImageView) findViewById(R.id.inat_info_btn);
		info.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				manager.beginTransaction().replace(R.id.container, new InfoFragment(),
						InfoFragment.class.getSimpleName()).addToBackStack(null).commit();
			}
		});

		setSupportActionBar(toolbar);
	}

	@Override
	public void onClickableAreaTouched(Object o) {
		if (o instanceof FintemCity){
			Bundle args = new Bundle();
			args.putString("city", ((FintemCity)o).getTitle());
			FragmentManager manager = getSupportFragmentManager();
			CityMapFragment fragment = new CityMapFragment();
			fragment.setArguments(args);
			manager.beginTransaction().replace(R.id.container, fragment, CityMapFragment.class.getSimpleName())
					.addToBackStack(null).commit();
		}
	}
}
