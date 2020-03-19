//Standard GLRenderer

#ifndef TESTNATIVEOPENGL_GLRENDERER_H
#define TESTNATIVEOPENGL_GLRENDERER_H

#include <pthread.h>
//pthread_t, pthread_mutex_t, pthread_xxx etc.
#include <android/native_window.h>
//ANativeWindow
#include <android/native_window_jni.h>
//ANativeWindow_fromSurface
#include <EGL/egl.h>
//EGLDisplay, EGLSurface, EGLContext, EGLint
//EGL_WINDOW_BIT etc.
#include <GLES2/gl2.h>
//GLuint, GLenum, glXXX etc.
#include <jni.h>
//JNIEnv, jobject etc.
#include <memory>
//malloc, free
#include <unistd.h>
//usleep

class RenderPara{
public:

    enum RenderThreadMessage{
    MSG_NONE = 0,
    MSG_WINDOW_SET,
    MSG_RENDER_LOOP_EXIT
    };

    pthread_t _threadId;
    pthread_mutex_t _mutex;
    enum RenderThreadMessage _msg;

    ANativeWindow* _window;

    EGLDisplay _display;
    EGLSurface _surface;
    EGLContext _context;

    EGLint _width;
    EGLint _height;
};


class GLRenderer {
private:
    RenderPara renderPara;

    //states
    bool inited=0;
    bool running =0;
    bool window_seted =0;
public:
    //implemented
    GLRenderer();
    ~GLRenderer();
    virtual GLuint loadShader(GLenum shaderType, const char* pSource);
    virtual GLuint createProgram(const char* pVertexSource, const char* pFragmentSource);
    virtual bool initRender(); //initialize EGL
    virtual void destroyRender();  //clear EGL
    static void* renderThread(void *args);
    virtual void renderLoop();

    //jni functions
    virtual void setWindow(JNIEnv *env, jobject surface);
    virtual void startRenderThread();
    virtual void stopRenderThread();

    //pure virtual, must implemented by derived classes
    virtual void surfaceCreate()=0;
    virtual void surfaceChange(int width, int height)=0;
    virtual void drawFrame()=0;

};



#endif //TESTNATIVEOPENGL_GLRENDERER_H
