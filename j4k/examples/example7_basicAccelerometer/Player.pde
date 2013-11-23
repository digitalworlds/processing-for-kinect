class Player
{
  Cube avatar;
  boolean is_falling=false;
  int initial_pos_x;
  int initial_pos_z;
  
  Player(PImage img, int pos_x, int pos_z)
  {
    initial_pos_x=pos_x;
    initial_pos_z=pos_z;
    avatar=new Cube(img,pos_x,0,pos_z,150);
  }
  
  void draw(Level level)
  {
     avatar.draw();
     
     //UPDATE THE POSITION OF THE PLAYER
  if(is_falling==false)
  {
    avatar.center_x+= mylevel.floor_angleZ*0.5;
    avatar.center_z-= mylevel.floor_angleX*0.5;
  }
  
  level.check_if_it_falls(2, 4,this);
  level.check_if_it_falls(7, 2,this);
  level.check_if_it_falls(4, 1,this);

  if(is_falling) 
  {
    avatar.center_y+=7;
    if(avatar.center_y>2000)
    {
      is_falling=false;
      avatar.center_y=0;
      avatar.center_x=initial_pos_x;
      avatar.center_z=initial_pos_z;
    }
  }
  if(avatar.center_z<-1200) avatar.center_z=-1200;
  if(avatar.center_z>150) avatar.center_z=150;
  if(avatar.center_x<-600) avatar.center_x=-600;
  if(avatar.center_x>600) avatar.center_x=600;
  
  }
}
