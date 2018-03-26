package com.amilutinovic.inat;

import android.util.DisplayMetrics;

import at.lukle.clickableareasimage.ClickableArea;

/**
 * Created by milutinac on 18.12.16..
 */
public class FintemCity {
	private int x;
	private int y;
	private int w;
	private int h;
	private String title;

	public FintemCity(){
		this.x = this.y = this.w = this.h = Integer.MIN_VALUE;
	}

	public FintemCity(String title, int x, int y, int w, int h){
		this.title = title;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public boolean isValid(){
		return title != null && x != Integer.MIN_VALUE && y != Integer.MIN_VALUE &&
				w != Integer.MIN_VALUE && h != Integer.MIN_VALUE;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ClickableArea toClickableArea(DisplayMetrics metrics){
		if (metrics != null){
			return new ClickableArea((int) (this.x / (metrics.densityDpi / 160f)),
					(int) (this.y / (metrics.densityDpi / 160f)),
					(int) (this.w / (metrics.densityDpi / 160f)),
					(int) (this.h / (metrics.densityDpi / 160f)),this);
		}
		return new ClickableArea(this.x, this.y, this.w, this.h, this);
	}
}
