package com.example.testopengl.programs;

import android.content.Context;

import com.example.testopengl.util.ShaderHelper;
import com.example.testopengl.util.TextResourceReader;

import static android.opengl.GLES20.glUseProgram;



public class ShaderProgram {
    //Uniform constants
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

    //Attribute constants
    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";


    //Shader program
    protected final int program;
    protected ShaderProgram(Context context, int vertexShaderResourceId,
                            int fragmentShaderResourceId){
        //Compile the shaders and link the program
        program = ShaderHelper.buildProgram(
                TextResourceReader.readTextFileFromResource(
                        context, vertexShaderResourceId),
                TextResourceReader.readTextFileFromResource(
                        context, fragmentShaderResourceId)
        );
    }

    public void useProgram(){
        //set current opengl shader to this
        glUseProgram(program);
    }

}
