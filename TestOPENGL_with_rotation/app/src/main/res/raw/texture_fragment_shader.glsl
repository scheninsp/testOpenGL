precision mediump float;

uniform sampler2D u_TextureUnit;

varying vec2 v_TextureCoordinates;
varying vec4 diffuse;
varying vec4 ambient;

void main()
{
    gl_FragColor = (diffuse + ambient) * texture2D(u_TextureUnit, v_TextureCoordinates);

    //gl_FragColor = texture2D(u_TextureUnit, v_TextureCoordinates);
}