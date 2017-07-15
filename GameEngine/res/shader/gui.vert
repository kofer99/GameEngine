#version 330 core

layout (location = 0) in vec4 aPos;
layout (location = 1) in vec2 aTexCoord;

out vec2 TexCoord;

uniform mat4 projmat;
uniform mat4 movmat;
uniform mat4 rotmat;

void main()
{		
    gl_Position = projmat * (movmat * (rotmat * aPos));
    TexCoord = aTexCoord;
}