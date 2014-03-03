package com.example.wifistrength2;

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

	private BroadcastReceiver myRssiChangeReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			WifiManager wifiMan=(WifiManager)getSystemService(Context.WIFI_SERVICE);
			wifiMan.startScan();
			int newRssi = wifiMan.getConnectionInfo().getRssi();
			int level = WifiManager.calculateSignalLevel(newRssi, 5);
			addLine("Wifi changed " + new Date() + "  :  " + level);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		editText = (EditText) findViewById(R.id.editText1);
	}

	private void addLine(final String line){
		MainActivity.this.runOnUiThread(new Runnable(){
			@Override
			public void run() {
				editText.setText(editText.getText() + "\n" + line);
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();

		IntentFilter rssiFilter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
		this.registerReceiver(myRssiChangeReceiver, rssiFilter);

		WifiManager wifiMan = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		wifiMan.startScan();
		
		addLine("Registered");
	}

	@Override
	public void onPause() {
		super.onPause();
		this.unregisterReceiver(myRssiChangeReceiver);
		addLine("Unregistered");

	}

}
