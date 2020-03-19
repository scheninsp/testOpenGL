//Interface of GLrenderer to Surface

#ifndef TESTNATIVEOPENGL_GLRENDERSURF_H
#define TESTNATIVEOPENGL_GLRENDERSURF_H


#include "GLrenderer.h"

const static int POINTS = 4;

class GLrenderSurf: public GLRenderer {
private:
    GLuint tProgram;

    GLuint uTextureUnitLocation;
    GLuint aPositionLocation ;
    GLuint aTextureCoordinatesLocation;

    GLuint textureId;

    GLuint texture_buffer_id[2];

    GLfloat gTextureVertices[4*POINTS];
    GLfloat gScreenVertices[4*POINTS];

public:
    GLrenderSurf();
    ~GLrenderSurf();
    void loadTexture();
    void prepareContents();

    virtual void surfaceCreate();
    virtual void surfaceChange(int width, int height);
    virtual void drawFrame();
};



#endif //TESTNATIVEOPENGL_GLRENDERSURF_H
