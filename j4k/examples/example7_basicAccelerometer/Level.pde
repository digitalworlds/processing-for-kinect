class Level
{
  Cube[][] myfloor;
  Cube[] backwall;
  Cube[] leftwall;
  Cube[] rightwall;
  
  float floor_angleZ=0;
  float floor_angleX=0;
  
  Level(PImage floor_image, PImage wall_image)
  {
    myfloor=new Cube[11][11];
    for(int column=0;column<11;column++)//For every column
      for(int row=0;row<11;row++)//For every row
      myfloor[column][row]=new Cube(floor_image,column*150-5*150,150,-row*150+150,150);

  backwall=new Cube[11];
  for(int column=0;column<11;column++)
    backwall[column]=new Cube(wall_image,column*150-5*150,0,-10*150+150,150);
  
  rightwall=new Cube[11];
  leftwall=new Cube[11];
  for(int row=0;row<11;row++)
  {
    leftwall[row]=new Cube(wall_image,0*150-5*150,0,-row*150+150,150);
    rightwall[row]=new Cube(wall_image,10*150-5*150,0,-row*150+150,150);
  }
  }
  
  void draw()
  {  
    rotateZ(radians(floor_angleZ));
    
    rotateX(radians(floor_angleX));    

    rotateX(radians(floor_angleX));for(int i=0;i<11;i++)//for every column
    for(int j=0;j<11;j++)//for every row
       if(i==4 && j==1){}
       else if(i==7 && j==2){}
       else if(i==2 && j==4){}
       else myfloor[i][j].draw();
    
  for(int i=0;i<11;i++)
  {
    backwall[i].draw();
    leftwall[i].draw();
    rightwall[i].draw();
  }
   //UPDATE THE ORIENTATION OF THE FLOOR
  if(up_pressed) floor_angleX+=1;
  if(down_pressed) floor_angleX-=1;
  if(left_pressed) floor_angleZ-=1;
  if(right_pressed) floor_angleZ+=1;
  }
  
  void check_if_it_falls(int i, int j, Player player)
  {
    int hole_x=i*150-5*150;
    int hole_z=-j*150+150;
  if(player.avatar.center_x>hole_x-35 && player.avatar.center_x<hole_x+35
  && player.avatar.center_z>hole_z-35 && player.avatar.center_z<hole_z+35)
  {
    player.is_falling=true;
    player.avatar.center_x=hole_x;
    player.avatar.center_z=hole_z;
  }
}
}
