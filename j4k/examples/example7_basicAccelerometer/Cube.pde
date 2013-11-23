class Cube
{
  int center_x;
  int center_y;
  int center_z;
  int cube_size;
  PImage cube_image;
  
  Cube(PImage img, int x,int y, int z, int s)
  {
    cube_image=img;
    center_x=x;
    center_y=y;
    center_z=z;
    cube_size=s;
  }
  
  void imageR(PImage img, int image_centerX,int image_centerY,int image_centerZ, int image_width, int image_height, int angle_aroundX,int angle_aroundY,int angle_aroundZ)
{
   pushMatrix();
    //MOVE THE COORDINATE SYSTEM TO THE CENTER OF THE IMAGE
    translate(image_centerX,image_centerY,image_centerZ);
    //ROTATE THE COORDINATE SYSTEM AROUND THE NEW (0,0)
    rotateX(radians(angle_aroundX));
    rotateY(radians(angle_aroundY));
    rotateZ(radians(angle_aroundZ));
    //DRAW THE IMAGE SO THAT IT IS CENTERED AT (0,0)
    //AND ITS SIZE IS image_width x image_height
    image(img,-image_width/2,-image_height/2,image_width,image_height);
  popMatrix();
}
  
  void setPosition(int x, int y, int z)
  {
    center_x=x;
    center_y=y;
    center_z=z;
  }
  
  void draw()
  {
    pushMatrix();
    translate(center_x,center_y,center_z);
    //Back face
  imageR(cube_image,0,0,-cube_size/2,cube_size,cube_size,0,0,0);
  //Bottom face
  imageR(cube_image,0,cube_size/2,0,cube_size,cube_size,90,0,0);
  //Top face
  imageR(cube_image,0,-cube_size/2,0,cube_size,cube_size,90,0,0);
  //Left face
  imageR(cube_image,-cube_size/2,0,0,cube_size,cube_size,0,90,0);
  //Right face
  imageR(cube_image,cube_size/2,0,0,cube_size,cube_size,0,90,0);
  //Front face
  imageR(cube_image,0,0,cube_size/2,cube_size,cube_size,0,0,0);
  popMatrix();
  }
}
