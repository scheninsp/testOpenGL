package com.example.testopengl.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_STATIC_DRAW;
import static android.opengl.GLES20.glBufferData;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGenBuffers;
import static android.opengl.GLES20.glBindBuffer;
import static android.opengl.GLES20.glVertexAttribPointer;
import static com.example.testopengl.util.Constants.BYTES_PER_FLOAT;

public class VertexBuffer {
    private final int bufferId;

    public VertexBuffer(float[] vertexData){
        final int buffers[] = new int[1];

        glGenBuffers(buffers.length, buffers, 0);
        if (buffers[0] == 0){
            throw new RuntimeException("create vertex buffer failed");
        }
        bufferId = buffers[0]; //use this buffer

        glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);

        FloatBuffer vertexArray = ByteBuffer.allocateDirect(vertexData.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertexData);

        vertexArray.position(0);

        glBufferData(GL_ARRAY_BUFFER, vertexArray.capacity()*BYTES_PER_FLOAT,
                vertexArray, GL_STATIC_DRAW);

        //unbind
        glBindBuffer(GL_ARRAY_BUFFER,0);
    }

    //set pointer location
    public void setVertexAttribPointer(int dataOffset, int attributeLocation,
                                       int componentCount, int stride){
        glBindBuffer(GL_ARRAY_BUFFER,bufferId);

        glVertexAttribPointer(attributeLocation, componentCount, GL_FLOAT, false,
                stride, dataOffset);

        glEnableVertexAttribArray(attributeLocation);

        glBindBuffer(GL_ARRAY_BUFFER,0);
    }


}


