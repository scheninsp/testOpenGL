//useless
package com.example.testopengl.objects;

import com.example.testopengl.programs.TextureShaderProgram;
import com.example.testopengl.util.IndexBuffer;
import com.example.testopengl.util.VertexArray;
import com.example.testopengl.util.VertexBuffer;
import static com.example.testopengl.util.Constants.BYTES_PER_FLOAT;
import static com.example.testopengl.util.Geometry.gauss_fun;

import static android.opengl.GLES20.GL_ELEMENT_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glBindBuffer;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glDrawElements;


public class Board {
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = 0;

    private static float[] mMesh;
    private static final int mesh_rows = 64;
    private static final int mesh_cols = 64;

    /*private static final float[] mesh = {-0.5f,-0.5f,0.5f,-0.5f,-0.5f,0.5f,0.5f,0.5f};
    private static final int mesh_rows = 2;
    private static final int mesh_cols = 2;
    */

    private static final int numElements = (mesh_cols-1) * (mesh_rows-1) * 6;

    private static VertexBuffer mVertexBuffer;
    private static VertexBuffer mUVBuffer;
    private static IndexBuffer indexBuffer;

    public Board() {
        mMesh = genMesh(mesh_rows,mesh_cols);
        genVertexUVBufferData();
        indexBuffer = new IndexBuffer(createIndexData());
        int xx=0;
    }

    //send data into buffers
    public void bindData(TextureShaderProgram textureProgram) {
        mVertexBuffer.setVertexAttribPointer(
                0,
                textureProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                0);

        mUVBuffer.setVertexAttribPointer(
                0,
                textureProgram.getTextureCoordinatesAttributeLocation(),
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE);
    }

    public void draw() {
        // glDrawArrays(GL_TRIANGLES, 0, 3*triangleNum);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer.getBufferId());
        glDrawElements(GL_TRIANGLES, numElements, GL_UNSIGNED_SHORT, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    private float[] genMesh(int mesh_rows, int mesh_cols){
        float start = -0.5f;
        float end = 0.5f;
        float[] mesh = new float[mesh_rows* mesh_cols* 2];

        for(int row = 0; row< mesh_rows; row++){
            for (int col = 0; col < mesh_cols; col++) {
                mesh[row*2*mesh_cols+2*col] = start+ (end-start)*((float)row/(float)(mesh_rows-1));
                mesh[row*2*mesh_cols+2*col+1] = start+ (end-start)*((float)col/(float)(mesh_cols-1));
            }
        }

        return mesh;
    }


    private short[] createIndexData(){
        final short[] indexData = new short[numElements];
        int offset = 0;

        int height = mesh_rows;
        int width = mesh_cols;

        for(int row = 0; row< height-1; row++){
            for(int col=0; col<width-1; col++){

                short tln = (short)(row*width+col);
                short trn = (short)(row*width+col+1);
                short bln = (short)((row+1)*width+col);
                short brn = (short) ((row+1)*width+col+1);

                //triangle 1 and 2, make sure these are counter-clock ordered
                indexData[offset++] = tln;
                indexData[offset++] = bln;
                indexData[offset++] = trn;

                indexData[offset++] = bln;
                indexData[offset++] = brn;
                indexData[offset++] = trn;

            }
        }

        return indexData;
    }

    //generate data to send into openGL shaders
    private void genVertexUVBufferData() {

        //assign
        float[] vertexBuffer = new float[POSITION_COMPONENT_COUNT * mesh_rows * mesh_cols];
        float[] uvBuffer = new float[TEXTURE_COORDINATES_COMPONENT_COUNT * mesh_rows * mesh_cols];

        int num = 0;
        for (int row = 0; row < mesh_rows; row++) {
            for (int col = 0; col < mesh_cols; col++) {
                // assign data to buffer
                vertexBuffer[num++] = mMesh[row*2*mesh_cols+2*col];
                vertexBuffer[num++] = mMesh[row*2*mesh_cols+2*col+1];

                float tmpy = ((float)row-(float)mesh_rows/2)/(float)mesh_rows;
                float tmpx = ((float)col-(float)mesh_cols/2)/(float)mesh_cols;
                vertexBuffer[num++] = 0.1f * gauss_fun(tmpx,tmpy,0.18f);

            }
        }

        num=0;
        for (int row = 0; row < mesh_rows; row++) {
            for (int col = 0; col < mesh_cols; col++) {

                // assign data to buffer
                uvBuffer[num++] =(float)(col) /(float)(mesh_cols-1);
                uvBuffer[num++] =(float)(row) /(float)(mesh_rows-1);
            }
        }

        mVertexBuffer = new VertexBuffer(vertexBuffer);
        mUVBuffer = new VertexBuffer(uvBuffer);
    }
}
