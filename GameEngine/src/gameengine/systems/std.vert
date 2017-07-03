#version 330 core
layout (location = 0) in vec3 aPos;

uniform mat4 projmat;

void main()
{		
    gl_Position = projmat*aPos;
}