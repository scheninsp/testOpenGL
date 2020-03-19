
#include "GLrenderSurf.h"

auto tVertexShader =
        "attribute vec2 a_Position;\n"
        "attribute vec2 a_TextureCoordinates;\n"
        "varying vec2 v_TextureCoordinates;\n"
        "void main() {\n"
        "   v_TextureCoordinates = a_TextureCoordinates;\n"
        "   gl_Position = vec4(a_Position,0,1.0);\n"
        "}\n";

auto tFragmentShader =
        "precision mediump float;\n"
        "uniform sampler2D u_TextureUnit;\n"
        "varying vec2 v_TextureCoordinates;\n"
        "vec4 color_temp;\n"
        "void main() {\n"
        "   gl_FragColor = texture2D(u_TextureUnit, v_TextureCoordinates);\n"
        "}\n";

GLrenderSurf::GLrenderSurf(){

}
GLrenderSurf::~GLrenderSurf(){

}

void GLrenderSurf::surfaceCreate() {
    tProgram = createProgram(tVertexShader, tFragmentShader);

    uTextureUnitLocation = glGetUniformLocation(tProgram, "u_TextureUnit");
    aPositionLocation  = glGetAttribLocation(tProgram, "a_Position");
    aTextureCoordinatesLocation = glGetAttribLocation(tProgram, "a_TextureCoordinates");

    loadTexture();
    glClearColor(0.0,0.0,0.0,1.0f);

    glGenBuffers(2, texture_buffer_id);
    glBindBuffer(GL_ARRAY_BUFFER, texture_buffer_id[0]);
    glBufferData(GL_ARRAY_BUFFER, 4*POINTS * sizeof(GLfloat), gScreenVertices, GL_STATIC_DRAW); //扇形坐标
    glEnableVertexAttribArray(aPositionLocation);
    glBindBuffer(GL_ARRAY_BUFFER, texture_buffer_id[1]);
    glBufferData(GL_ARRAY_BUFFER, 4*POINTS * sizeof(GLfloat), gTextureVertices, GL_STATIC_DRAW);//矩形坐标
    glEnableVertexAttribArray(aTextureCoordinatesLocation);
}

void GLrenderSurf::surfaceChange(int width, int height) {
    glViewport(0, 0, width, height);
}


void GLrenderSurf::drawFrame(){
    glClear( GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
    //画Texture
    glUseProgram(tProgram); //使用texture的program
    glTexSubImage2D(GL_TEXTURE_2D,0,0,0,1000,49,GL_LUMINANCE, GL_UNSIGNED_BYTE,sb_data);  //更新显示图像
    glBindBuffer(GL_ARRAY_BUFFER, texture_buffer_id[0]);    //使用VBO中存储的点
    glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, GL_FALSE, 0, 0);    //读取纹理坐标
    glEnableVertexAttribArray(aPositionLocation);
    glBindBuffer(GL_ARRAY_BUFFER, texture_buffer_id[1]);
    glVertexAttribPointer(aTextureCoordinatesLocation, 2, GL_FLOAT, GL_FALSE, 0, 0);
    glEnableVertexAttribArray(aTextureCoordinatesLocation);
    glDrawArrays(GL_TRIANGLE_STRIP, 0, 2*POINTS);    //显示纹理
    glBindBuffer(GL_ARRAY_BUFFER, 0);   //用完buffer以后解绑定

}

