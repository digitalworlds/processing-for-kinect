import edu.ufl.digitalworlds.j4k.*;

PKinect mykinect;
ImageAssets image_library;
Level mylevel;
Player[] myplayers;
Camera mycamera;

boolean right_pressed=false;
boolean left_pressed=false;
boolean up_pressed=false;
boolean down_pressed=false;
int previous_mouseX;
int previous_mouseY;


void setup()
{
  size(600,400,P3D);
  image_library=new ImageAssets();
  mycamera=new Camera();
  myplayers=new Player[3];
  myplayers[0]=new Player(image_library.wood,0,-450);
  myplayers[1]=new Player(image_library.cabinet,150,-150);
  myplayers[2]=new Player(image_library.wood,-300,-150);
  mylevel=new Level(image_library.ceiling,image_library.cabinet);
  mykinect=new PKinect(this);
}


void draw()
{
  background(0);
  translate(width/2, height/2);
  mycamera.view();
  float v[]=mykinect.getAccelerometerTiltAndRoll();
  mylevel.floor_angleX=degrees(-v[0]);
  mylevel.floor_angleZ=degrees(-v[1]);
  mylevel.draw();  
  myplayers[0].draw(mylevel);
  myplayers[1].draw(mylevel);
  myplayers[2].draw(mylevel);
 }
 
 void mousePressed()
 {
   previous_mouseX=mouseX;
   previous_mouseY=mouseY;
 }
 
 void mouseDragged()
 {
   //COMPUTE HOW MUCH I MOVED THE MOUSE ALONG X AND Y AXIS
   int distX=mouseX-previous_mouseX;
   int distY=mouseY-previous_mouseY;
   
   mycamera.mouseDragged(distX,distY);
   
   //RECORD THE CURRENT MOUSE X,Y 
   previous_mouseX=mouseX;
   previous_mouseY=mouseY;
 }
 
 void keyPressed()
 {
   if(key == CODED)
   {
     if(keyCode == UP)up_pressed=true;
     if(keyCode == DOWN)down_pressed=true;
     if(keyCode == LEFT)left_pressed=true;
     if(keyCode == RIGHT)right_pressed=true;
   }
 }
 
 void keyReleased()
 {
   if(key == CODED)
   {
     if(keyCode == UP)up_pressed=false;
     if(keyCode == DOWN)down_pressed=false;
     if(keyCode == LEFT)left_pressed=false;
     if(keyCode == RIGHT)right_pressed=false;
   }
 }
