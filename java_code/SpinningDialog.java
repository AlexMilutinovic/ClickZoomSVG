package com.amilutinovic.inat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by milutinac on 17.12.16..
 */
public class SpinningDialog extends ProgressDialog {
	private CharSequence title;
	private CharSequence message;
	private String preventCloseMessage;
	private boolean cancelable;
	private Activity activity;
	private SpinningDialogListener listener;

	public SpinningDialog(Activity activity) {
		super(activity);
		this.activity = activity;
		this.setTitle("");
		this.setMessage("Please wait");
		this.setCancelable(false);
		this.preventCloseMessage = "Please wait while operation finishes";
	}

	public SpinningDialog(Context context, int theme) {
		super(context, theme);
	}

	public void setPreventCloseMessage(String preventCloseMessage){
		this.preventCloseMessage = preventCloseMessage;
	}

	public void setSpinningDialogListener(SpinningDialogListener listener){
		this.listener = listener;
	}

	@Override
	public void setMessage(CharSequence message) {
		super.setMessage(message);
		this.message = message;
	}

	@Override
	public void setTitle(CharSequence title) {
		super.setTitle(title);
		this.title = title;
	}

	@Override
	public void onBackPressed() {
		if (cancelable){
			if (listener != null){
				listener.onBackPressed();
			}
			super.onBackPressed();
		} else {
			Toast.makeText(activity, preventCloseMessage, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void setCancelable(boolean flag) {
		super.setCancelable(flag);
		this.cancelable = flag;
	}

	@Override
	public void show() {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				SpinningDialog.super.show();
			}
		});
	}

	@Override
	public void setOnCancelListener(OnCancelListener listener) {
		if (cancelable && listener != null){
			super.setOnCancelListener(listener);
		}
	}

	public interface SpinningDialogListener{
		void onBackPressed();
		void onCanceled();
	}
}
