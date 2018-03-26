package com.amilutinovic.inat;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {

	private FintemTextView websiteLink;
	private FintemTextView emailLink;
	private FintemTextView artshopLink;

	private View rootView;

	public InfoFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		rootView = inflater.inflate(R.layout.fragment_info, container, false);
		initComponents();
		setListeners();
		return rootView;
	}

	private void initComponents(){
		websiteLink = (FintemTextView) rootView.findViewById(R.id.website_subtitle);
		emailLink = (FintemTextView) rootView.findViewById(R.id.email_subtitle);
		artshopLink = (FintemTextView) rootView.findViewById(R.id.artshop_subtitle);
	}

	private void setListeners(){
		websiteLink.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("http://www.inat.fr"));
				getActivity().startActivity(browserIntent);
			}
		});

		emailLink.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				Uri data = Uri.parse("mailto:contact@inat.fr");
				intent.setData(data);
				getActivity().startActivity(intent);
			}
		});

		artshopLink.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("https://www.redbubble.com/people/jugcerovic/portfolio"));
				getActivity().startActivity(browserIntent);
			}
		});
	}

}