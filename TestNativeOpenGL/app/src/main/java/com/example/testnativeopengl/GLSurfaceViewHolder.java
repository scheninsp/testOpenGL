package com.example.testnativeopengl;

import android.view.SurfaceHolder;

public class GLSurfaceViewHolder implements SurfaceHolder.Callback{

    public void surfaceCreated(SurfaceHolder holder){
        JNIProxyForGL.nat_setSurface(holder.getSurface());
        JNIProxyForGL.nat_startRender();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h){
        JNIProxyForGL.nat_setSurface(holder.getSurface());
        JNIProxyForGL.nat_startRender();
    }

    public void surfaceDestroyed(SurfaceHolder holder){
        JNIProxyForGL.nat_setSurface(null);
    }

}
