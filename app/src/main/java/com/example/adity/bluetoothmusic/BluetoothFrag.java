package com.example.adity.bluetoothmusic;


import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class BluetoothFrag extends Fragment{

    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    public int REQUEST_ENABLE_BT=1;
    public int REQUEST_SETTINGS=2;
    ToggleButton toggleButton;
    ConstraintLayout constraintLayout;
    View.OnClickListener mSnackBarClickListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent setIntent=new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
            startActivityForResult(setIntent, REQUEST_SETTINGS);
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_bluetoothfrag,container,false);
        constraintLayout= (ConstraintLayout) view.findViewById(R.id.constraint);
        toggleButton= (ToggleButton) view.findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if (!mBluetoothAdapter.isEnabled()) {
                        Snackbar.make(constraintLayout, "Are you sure you want to switch on?", BaseTransientBottomBar.LENGTH_LONG).setAction("Yes",mSnackBarClickListener).show();
                    }
                }
                else{
                    if (mBluetoothAdapter.isEnabled()) {
                        mBluetoothAdapter.disable();
                    }
                }
            }
        });
        return view;
    }
}
