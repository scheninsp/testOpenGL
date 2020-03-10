package com.example.testopengl.programs;

import android.content.Context;
import com.example.testopengl.R;

import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glBindTexture;

public class TextureShaderProgram extends ShaderProgram{
    //Uniform Locations
    private final int uMatrixLocation;
    private final int uTextureUnitLocation;

    //Attribute Locations
    private final int aPositionLocation;
    private final int aTextureCoordinatesLocation;

    //constructor , read data from openGL
    public TextureShaderProgram(Context context){
        super(context, R.raw.texture_vertex_shader,
                R.raw.texture_fragment_shader);

        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
        uTextureUnitLocation = glGetUniformLocation(program, U_TEXTURE_UNIT);

        aPositionLocation = glGetAttribLocation(program,A_POSITION);
        aTextureCoordinatesLocation = glGetAttribLocation(program, A_TEXTURE_COORDINATES);
    }

    //functions
    public void setUniforms(float[] matrix, int textureId){

        //build vertices
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);

        //set texture unit number
        glActiveTexture(GL_TEXTURE0);

        //bind texture
        glBindTexture(GL_TEXTURE_2D, textureId);

        //texture uniform sampler to use texture in shader from unit 0
        glUniform1i(uTextureUnitLocation,0);

    }

    public int getPositionAttributeLocation() {
        return aPositionLocation;
    }

    public int getTextureCoordinatesAttributeLocation() {
        return aTextureCoordinatesLocation;
    }

}
