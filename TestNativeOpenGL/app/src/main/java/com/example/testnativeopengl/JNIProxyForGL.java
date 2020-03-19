package com.example.testnativeopengl;

import android.view.Surface;

public class JNIProxyForGL {

    static{
        System.loadLibrary("native-lib");
    }

    public static native void nat_prepareContents();
    public static native void nat_setSurface(Surface surface);
    public static native void nat_startRender();
    public static native void nat_stopRender();
}
