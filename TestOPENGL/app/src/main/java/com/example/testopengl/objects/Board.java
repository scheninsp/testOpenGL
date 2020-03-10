package com.example.testopengl.objects;

import com.example.testopengl.programs.TextureShaderProgram;
import com.example.testopengl.util.VertexArray;

import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawArrays;

public class Board {
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = 0;

    private static int triangleNum = 2*2*2;
    private static VertexArray mVertexBuffer;
    private static VertexArray mUVBuffer;

    public Board() {
        genVertexUVBufferData();
    }

    //send data into buffers
    public void bindData(TextureShaderProgram textureProgram) {
        mVertexBuffer.setVertexAttribPointer(
                0,
                textureProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);

        mUVBuffer.setVertexAttribPointer(
                0,
                textureProgram.getTextureCoordinatesAttributeLocation(),
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE);
    }

    public void draw() {
        glDrawArrays(GL_TRIANGLES, 0, 3*triangleNum);
    }

    //generate data to send into openGL shaders
    private void genVertexUVBufferData() {

        int num = 0;
        float[] mesh = {-20, -20, 500, 10, 1000, 0, 0, 500, 470, 500, 980, 500, 20, 980, 420, 950, 950, 910};
        int mesh_rows = 3;
        int mesh_cols = 3;
        int mesh_height = 1000;
        int mesh_width = 1000;

        //normalize
        for (int row = 0; row < mesh_rows; row++) {
            for (int col = 0; col < mesh_cols; col++) {
                mesh[row * mesh_cols * 2 + col * 2] = (mesh[row * mesh_cols * 2 + col * 2] - mesh_width / 2) / mesh_width;
                mesh[row * mesh_cols * 2 + col * 2 + 1] = (mesh[row * mesh_cols * 2 + col * 2 + 1] - mesh_height / 2) / mesh_height;
            }
        }

        //assign
        float[] vertexBuffer = new float[18 * (mesh_rows - 1) * (mesh_cols - 1)];
        float[] uvBuffer = new float[12 * (mesh_rows - 1) * (mesh_cols - 1)];

        for (int row = 0; row < mesh_rows - 1; row++) {
            for (int col = 0; col < mesh_cols - 1; col++) {

                float tl_x = mesh[row * mesh_cols * 2 + col * 2];
                float tl_y = mesh[row * mesh_cols * 2 + col * 2 + 1];
                float tr_x = mesh[row * mesh_cols * 2 + (col+1) * 2];
                float tr_y = mesh[row * mesh_cols * 2 + (col+1) * 2 + 1];
                float bl_x = mesh[(row+1) * mesh_cols * 2 + col * 2];
                float bl_y = mesh[(row+1) * mesh_cols * 2 + col * 2 + 1];
                float br_x = mesh[(row+1) * mesh_cols * 2 + (col+1) * 2];
                float br_y = mesh[(row+1) * mesh_cols * 2 + (col+1) * 2 + 1];

                // assign data to buffer
                vertexBuffer[18 * num + 0] = tl_x;
                vertexBuffer[18 * num + 1] = tl_y;
                vertexBuffer[18 * num + 2] = 0;
                vertexBuffer[18 * num + 3] = tr_x;
                vertexBuffer[18 * num + 4] = tr_y;
                vertexBuffer[18 * num + 5] = 0;
                vertexBuffer[18 * num + 6] = br_x;
                vertexBuffer[18 * num + 7] = br_y;
                vertexBuffer[18 * num + 8] = 0;
                vertexBuffer[18 * num + 9] = br_x;
                vertexBuffer[18 * num + 10] = br_y;
                vertexBuffer[18 * num + 11] = 0;
                vertexBuffer[18 * num + 12] = bl_x;
                vertexBuffer[18 * num + 13] = bl_y;
                vertexBuffer[18 * num + 14] = 0;
                vertexBuffer[18 * num + 15] = tl_x;
                vertexBuffer[18 * num + 16] = tl_y;
                vertexBuffer[18 * num + 17] = 0;

                uvBuffer[12 * num + 0] =(float)(col) /(float) (mesh_cols - 1);
                uvBuffer[12 * num + 1] =(float)(row) /(float) (mesh_rows - 1);
                uvBuffer[12 * num + 2] =(float)(col + 1) /(float) (mesh_cols - 1);
                uvBuffer[12 * num + 3] =(float)(row) /(float) (mesh_rows - 1);
                uvBuffer[12 * num + 4] =(float)(col + 1) /(float) (mesh_cols - 1);
                uvBuffer[12 * num + 5] =(float)(row + 1) /(float) (mesh_rows - 1);
                uvBuffer[12 * num + 6] =(float)(col + 1) /(float) (mesh_cols - 1);
                uvBuffer[12 * num + 7] =(float)(row + 1) /(float) (mesh_rows - 1);
                uvBuffer[12 * num + 8] =(float)(col) /(float) (mesh_cols - 1);
                uvBuffer[12 * num + 9] =(float)(row + 1) /(float) (mesh_rows - 1);
                uvBuffer[12 * num + 10] =(float)(col) /(float) (mesh_cols - 1);
                uvBuffer[12 * num + 11] =(float)(row) /(float) (mesh_rows - 1);

                num++;
            }
        }

        mVertexBuffer = new VertexArray(vertexBuffer);
        mUVBuffer = new VertexArray(uvBuffer);
    }
}
