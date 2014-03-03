package com.example.wifistrength;

import java.util.Date;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends Activity {
	private EditText editText;

	public BroadcastReceiver rssiReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			int rssi = intent.getIntExtra(WifiManager.EXTRA_NEW_RSSI, 0);
			int level = WifiManager.calculateSignalLevel(rssi, 5);
			addLine("Wifi changed " + new Date() + "  :  " + level);

		}
	};

	private void addLine(final String line){
		MainActivity.this.runOnUiThread(new Runnable(){
			@Override
			public void run() {
				editText.setText(editText.getText() + "\n" + line);
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		editText = (EditText) findViewById(R.id.editText1);
	}

	@Override
	public void onResume() {
		super.onResume();
		registerReceiver(rssiReceiver, new IntentFilter(WifiManager.RSSI_CHANGED_ACTION));
		addLine("Registered");
	}

	@Override
	public void onPause() {
		super.onPause();
		unregisterReceiver(rssiReceiver);
		addLine("Unregistered");
	}

}
