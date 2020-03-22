
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

