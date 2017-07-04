#version 330 core
layout (location = 0) in vec3 aPos;

uniform mat4 projmat;
uniform mat4 movmat;

void main()
{		
    gl_Position = projmat*(movmat*vec4(aPos,1));
}
