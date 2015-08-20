package com.ziglinski.flashright;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private static boolean isOn= false;
    private Button button;
    private static Camera
            cam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeFields();

        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isOn){
                        turnOffLight();
                    }
                    else {
                        turnOnLight();
                    }
                }
            });
        }
        else{
            button.setText("Flash Unavailable");
        }
    }

    private void initializeFields(){
        button = (Button) findViewById(R.id.button);

        if(isOn){
            button.setText("Turn Off");
        }
        else{
            button.setText("Turn On");
        }
    }

    private void initializeCamera(){
        cam = Camera.open();
        Camera.Parameters p = cam.getParameters();
        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        cam.setParameters(p);
    }

    private void turnOnLight(){
        Log.i("Flashlight", "Turn on Light");
        isOn = true;
        initializeCamera();
        cam.startPreview();
        button.setText("Turn Off");
    }

    private void turnOffLight(){
        Log.i("Flashlight", "Turn off Light");
        isOn = false;
//        if(cam == null) {
//            Log.d("Flashlight", "initialize before turn off");
//            initializeCamera();
//        }
        cam.stopPreview();
        cam.release();
        button.setText("Turn On");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
