#version 330 core

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 aTexCoord;

out vec2 TexCoord;

uniform mat4 projmat;
uniform mat4 movmat;
uniform mat4 rotmat;
uniform mat4 scalemat;
uniform vec3 camera;

void main()
{		
<<<<<<< Upstream, based on master
    gl_Position = projmat * (movmat *( rotmat * (scalemat * (vec4(aPos,1)))) + vec4(camera, 0));
=======
    gl_Position = projmat*(movmat*(rotmat*(vec4(aPos,1)+ vec4(camera, 0))));
>>>>>>> d4e6c3a Camera Movement and collision adjustment
    TexCoord = aTexCoord;
}
