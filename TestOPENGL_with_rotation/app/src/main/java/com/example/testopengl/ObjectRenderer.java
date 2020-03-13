package com.example.testopengl;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_CULL_FACE;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;

import com.example.testopengl.objects.Board;
import com.example.testopengl.objects.BoardHmap;
import com.example.testopengl.programs.TextureShaderProgram;
import com.example.testopengl.util.MatrixHelper;
import com.example.testopengl.util.TextureHelper;

public class ObjectRenderer implements Renderer {
    private final Context context;

    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private final float[] mpMatrix = new float[16];

    //for rotation
    public volatile float deltaX;
    public volatile float deltaY;
    private final float[] accumulatedRotation = new float[16];
    private final float[] currentRotation = new float[16];
    private final float[] temporaryMatrix = new float[16];

    private Board board;

    private TextureShaderProgram textureProgram;

    private int texture;

    public ObjectRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);

        //board = new BoardHmap(((BitmapDrawable)context.getResources()
        //        .getDrawable(R.drawable.board_surface_heightmap)).getBitmap());

        board = new Board();

        textureProgram = new TextureShaderProgram(context);

        texture = TextureHelper.loadTexture(context, R.drawable.board_surface);

        //initialize rotation matrix
        Matrix.setIdentityM(accumulatedRotation, 0);
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        // Set the OpenGL viewport to fill the entire surface.
        glViewport(0, 0, width, height);

        MatrixHelper.perspectiveM(projectionMatrix, 60, (float) width
                / (float) height, 1f, 10f);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        // Clear the rendering surface.
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        //glClear(GL_COLOR_BUFFER_BIT);

        textureProgram.useProgram();

        setIdentityM(modelMatrix, 0);
        translateM(modelMatrix, 0, 0f, 0f, -2.5f);

        //make texture view rightly top-down
        //rotateM(modelMatrix, 0, -45f, 1f, 0f, 0f);
        //rotateM(modelMatrix, 0, -90f, 0f, 0f, 1f);

        // Set currentRotation.
        Matrix.setIdentityM(currentRotation, 0);
        Matrix.rotateM(currentRotation, 0, deltaX, 0.0f, 1.0f, 0.0f);
        Matrix.rotateM(currentRotation, 0, deltaY, 1.0f, 0.0f, 0.0f);
        deltaX = 0.0f;
        deltaY = 0.0f;

        // set accumulatedRotation
        Matrix.multiplyMM(temporaryMatrix, 0, currentRotation, 0, accumulatedRotation, 0);
        System.arraycopy(temporaryMatrix, 0, accumulatedRotation, 0, 16);

        // apply rotation to modelMatrix
        Matrix.multiplyMM(temporaryMatrix, 0, modelMatrix, 0, accumulatedRotation, 0);
        System.arraycopy(temporaryMatrix, 0, modelMatrix, 0, 16);

        //apply projectionMatrix
        multiplyMM(temporaryMatrix, 0, projectionMatrix, 0, modelMatrix, 0);
        System.arraycopy(temporaryMatrix, 0, mpMatrix, 0, temporaryMatrix.length);

        // Draw the table.
        textureProgram.setUniforms(mpMatrix, texture);
        board.bindData(textureProgram);
        board.draw();

    }

}