#ifdef GL_ES
precision highp float;
precision highp double;
#endif



uniform int depth;
uniform vec2 resolution;


vec4 f = vec4(1.,1.,1.,1.); // f will be used to store the color of the current fragment



void main(void)

{

 vec2 c = vec2 (-1.0 + 2.0 * gl_FragCoord.x / resolution.x, 1.0 - 2.0 * gl_FragCoord.y / resolution.y) ;
 double constX = c.x;
 double constY = c.y;
 double x = constX;
 double y = constY;
 int iter = 0;
 for(int i = 0; i < depth;i++){
  if(sqrt(x*x+y*y)>2){iter = i;break;}
  x = x*x - y*y + constX;
  y = 2*x*y + constY;
 }

 f = vec4(c.x, c.y, 0.0, iter/depth);  // This is the code you see in the tutorial

 gl_FragColor = f;

}