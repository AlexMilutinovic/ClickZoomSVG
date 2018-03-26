package com.amilutinovic.inat;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by milutinac on 17.1.17..
 */
public class FintemTextView extends TextView {

	private static final String TAG = FintemTextView.class.getSimpleName();
	private static final String DEFAULT_TYPEFACE = "fonts/dinpro.otf";
	public static final String STYLE_BOLD = "bold";
	public static final String STYLE_REGULAR = "regular";
	private Context context;

	public FintemTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		parseAttributes(attrs);
	}

	public FintemTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		parseAttributes(attrs);
	}

	public FintemTextView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	private void init(){
		init(DEFAULT_TYPEFACE);
	}

	private void init(String typeface) {
		if (!isInEditMode()) {
			Typeface tf = Typeface.createFromAsset(context.getAssets(), typeface);
			setTypeface(tf);
		}
	}

	public void setBold(){
		init("fonts/dinpro.otf");
	}

	private void parseAttributes(AttributeSet attrs){
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FintemTextView);

		try {
			String style = a.getString(R.styleable.FintemTextView_style);
			if (style != null){
				switch (style){
					case STYLE_BOLD:
						setBold();
						break;
					case STYLE_REGULAR:
						break;
					default:
				}
			}
			if (a.getString(R.styleable.FintemTextView_font) != null){
				init(a.getString(R.styleable.FintemTextView_font));
			} else {
				init();
			}
		} finally {
			a.recycle();
		}
	}
}