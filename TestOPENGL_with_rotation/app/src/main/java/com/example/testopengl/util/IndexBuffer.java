package com.example.testopengl.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import static android.opengl.GLES20.GL_ELEMENT_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_STATIC_DRAW;
import static android.opengl.GLES20.glBindBuffer;
import static android.opengl.GLES20.glBufferData;
import static android.opengl.GLES20.glGenBuffers;
import static com.example.testopengl.util.Constants.BYTES_PER_SHORT;

public class IndexBuffer {
    private final int bufferId;

    public IndexBuffer(short[] indexData){
        final int buffers[] = new int[1];

        glGenBuffers(buffers.length, buffers, 0);
        if (buffers[0] == 0){
            throw new RuntimeException("create vertex buffer failed");
        }
        bufferId = buffers[0]; //use this buffer

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, buffers[0]);

        ShortBuffer indexArray = ByteBuffer.allocateDirect(indexData.length * BYTES_PER_SHORT)
                .order(ByteOrder.nativeOrder()).asShortBuffer().put(indexData);

        indexArray.position(0);

        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexArray.capacity()*BYTES_PER_SHORT,
                indexArray, GL_STATIC_DRAW);

        //unbind
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0);
    }

    public int getBufferId() {return bufferId;}
}
