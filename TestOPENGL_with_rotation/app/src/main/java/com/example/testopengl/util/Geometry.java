package com.example.testopengl.util;
//currently useless class

public class Geometry {

    public static class Point{
        public final float x,y,z;

        public Point(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public static class Vector{
        public final float x,y,z;

        public Vector(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public float length(){
            return (float)Math.sqrt(x*x+y*y+z*z);
        }
    }

    public static class Ray{
        public final Point point;
        public final Vector vector;

        public Ray(Point point, Vector vector){
            this.point = point;
            this.vector = vector;
        }
    }

    //functions
    public static float gauss_fun(float x, float y, float sigma){

        float dec1 = 2.0f*sigma*sigma;
        float dec2 = (float)Math.PI * dec1;

        float out = (float)Math.exp(-(x*x + y*y)/dec1)/dec2;

        return out;
    }

}
