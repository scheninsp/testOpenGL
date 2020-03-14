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
import static android.opengl.GLES20.glUniform3fv;

public class TextureShaderProgram extends ShaderProgram{
    //Uniform Locations
    private final int uTextureUnitLocation;

    //Attribute Locations
    private final int aPositionLocation;
    private final int aNormalLocation;
    private final int aTextureCoordinatesLocation;

    //lighting added
    private final int uVectorToLightLocation;
    private final int uMVMatrixLocation;
    private final int uIT_MVMatrixLocation;
    private final int uMVPMatrixLocation;

    //constructor , read data from openGL
    public TextureShaderProgram(Context context){
        super(context, R.raw.texture_vertex_shader,
                R.raw.texture_fragment_shader);

        uVectorToLightLocation = glGetUniformLocation(program, U_VECTOR_TO_LIGHT);
        uMVMatrixLocation = glGetUniformLocation(program, U_MV_MATRIX);
        uIT_MVMatrixLocation = glGetUniformLocation(program, U_IT_MV_MATRIX);
        uMVPMatrixLocation = glGetUniformLocation(program, U_MVP_MATRIX);

        uTextureUnitLocation = glGetUniformLocation(program, U_TEXTURE_UNIT);

        aPositionLocation = glGetAttribLocation(program,A_POSITION);
        aNormalLocation = glGetAttribLocation(program, A_NORMAL);
        aTextureCoordinatesLocation = glGetAttribLocation(program, A_TEXTURE_COORDINATES);
    }

    //functions
    public void setUniforms(float[] mvMatrix,
                            float[] it_mvMatrix,
                            float[] mvpMatrix,
                            float[] vectorToDirectionalLight,
                            int textureId){

        //build vertices
        glUniformMatrix4fv(uMVMatrixLocation, 1, false, mvMatrix, 0);
        glUniformMatrix4fv(uIT_MVMatrixLocation, 1, false, it_mvMatrix, 0);
        glUniformMatrix4fv(uMVPMatrixLocation, 1, false, mvpMatrix, 0);
        glUniform3fv(uVectorToLightLocation, 1, vectorToDirectionalLight, 0);

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

    public int getNormalAttributeLocation() {
        return aNormalLocation;
    }

    public int getTextureCoordinatesAttributeLocation() {
        return aTextureCoordinatesLocation;
    }

}
