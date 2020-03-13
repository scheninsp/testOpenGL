package com.example.testopengl.objects;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.example.testopengl.programs.TextureShaderProgram;
import com.example.testopengl.util.IndexBuffer;
import com.example.testopengl.util.VertexBuffer;

import static android.opengl.GLES20.GL_ELEMENT_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glBindBuffer;
import static android.opengl.GLES20.glDrawElements;

public class BoardHmap {

    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = 0;

    private final int width;
    private final int height;
    private final int numElements;
    private final VertexBuffer vertexBuffer;
    private final IndexBuffer indexBuffer;
    private final VertexBuffer mUVBuffer;

    public BoardHmap(Bitmap bitmap){
        width = bitmap.getWidth();
        height = bitmap.getHeight();

        if(width*height > 65536){
            throw new RuntimeException("bitmap resolution for heightmap is too large");
        }

        numElements = calculateNumElements();
        vertexBuffer = new VertexBuffer(loadBitmapData(bitmap));
        indexBuffer = new IndexBuffer(createIndexData());
        mUVBuffer = new VertexBuffer(createTextureUV());
    }

    //6 point, 2 triangle for one tile(pixel)
    private int calculateNumElements(){
        return (width-1) * (height-1) * 2 * 3;
    }

    //indices for heightmap
    private short[] createIndexData(){
        final short[] indexData = new short[numElements];
        int offset = 0;

        for(int row = 0; row< height-1; row++){
            for(int col=0; col<width-1; col++){

                short tln = (short)(row*width+col);
                short trn = (short)(row*width+col+1);
                short bln = (short)((row+1)*width+col);
                short brn = (short) ((row+1)*width+col+1);

                //triangle 1
                indexData[offset++] = tln;
                indexData[offset++] = bln;
                indexData[offset++] = trn;

                //triangle 2
                indexData[offset++] = trn;
                indexData[offset++] = bln;
                indexData[offset++] = brn;

            }
        }

        return indexData;
    }

    //values for heightmap
    private float[] loadBitmapData(Bitmap bitmap){

        final int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        bitmap.recycle();

        final float[] heightmapVertices =
                new float[width * height * POSITION_COMPONENT_COUNT];
        int offset = 0;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                final float xPosition = ((float)col / (float)(width - 1)) - 0.5f;
                final float yPosition =
                        (float) Color.red(pixels[(row * height) + col]) / (float)255 / 5f;  //use Red channel , scale smaller

                final float zPosition = ((float)row / (float)(height - 1)) - 0.5f;

                heightmapVertices[offset++] = xPosition;
                heightmapVertices[offset++] = yPosition;
                heightmapVertices[offset++] = zPosition;
            }
        }
        return heightmapVertices;
    }

    private float[] createTextureUV(){
        final float[] UVs =
                new float[width * height * TEXTURE_COORDINATES_COMPONENT_COUNT];
        int offset = 0;

        for(int row = 0; row< height-1; row++){
            for(int col=0; col<width-1; col++){

                UVs[offset++] = (float)(col)/(float)(width-1);  //u
                UVs[offset++] = (float)(row)/(float)(height-1);  //v

            }
        }

        return UVs;
    }


    public void bindData(TextureShaderProgram heightmapProgram) {
        vertexBuffer.setVertexAttribPointer(0,
                heightmapProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT, 0);

        mUVBuffer.setVertexAttribPointer(
                0,
                heightmapProgram.getTextureCoordinatesAttributeLocation(),
                TEXTURE_COORDINATES_COMPONENT_COUNT,
                STRIDE);
    }

    public void draw() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer.getBufferId());
        glDrawElements(GL_TRIANGLES, numElements, GL_UNSIGNED_SHORT, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

}
