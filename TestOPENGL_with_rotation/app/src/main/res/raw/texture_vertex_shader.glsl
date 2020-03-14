uniform mat4 u_MVMatrix;
uniform mat4 u_IT_MVMatrix;
uniform mat4 u_MVPMatrix;
uniform vec3 u_VectorToLight;  //directional light

attribute vec4 a_Position;
attribute vec3 a_Normal;
attribute vec2 a_TextureCoordinates;

varying vec2 v_TextureCoordinates;
varying vec4 diffuse;
varying vec4 ambient;

void main()
{
    vec4 eyeSpacePosition;
    vec3 eyeSpaceNormal;

    //caculate Normals in eyespace
    eyeSpacePosition = u_MVMatrix*a_Position;
    eyeSpaceNormal = normalize(vec3(u_IT_MVMatrix * vec4(a_Normal, 0.0)));

    float NdotL = max(dot(eyeSpaceNormal, u_VectorToLight),0.0);

    ambient = vec4(0.1, 0.1, 0.1, 0.1);
    vec4 LightColor = vec4(1, 1, 1, 1);
    vec4 MaterialDiffuse = vec4(0.8, 0.8, 0.8, 0.8);

    diffuse = NdotL * LightColor * MaterialDiffuse;

    gl_Position = u_MVPMatrix * a_Position;

    v_TextureCoordinates = a_TextureCoordinates;

}