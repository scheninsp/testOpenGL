package com.example.testnativeopengl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;

public class MainActivity extends AppCompatActivity {

    private SurfaceView mGLSurfaceView = null;
    private GLSurfaceViewHolder mHolder = null;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JNIProxyForGL.nat_prepareContents();

        mGLSurfaceView = (SurfaceView)findViewById(R.id.glSurfaceView1);
        mHolder = new GLSurfaceViewHolder();
        mGLSurfaceView.getHolder().addCallback(mHolder);

    }

    protected void onResume(){
        super.onResume();
    }

    protected void onStop(){
        super.onStop();
        JNIProxyForGL.nat_stopRender();
    }
}
