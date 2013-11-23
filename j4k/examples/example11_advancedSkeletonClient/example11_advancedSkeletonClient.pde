import edu.ufl.digitalworlds.j4k.*;

PKinect myKinect;
SkeletonClient client;

void setup()
{
  size(640,480,P3D);
  
  //Initialize one PKinect object and start the sensor
  myKinect=new PKinect(this);
  client=new SkeletonClient(myKinect,"http://localhost:60001"); 
}

void draw()
{   
  background(255,255,255);
  //Set the camera parameters
  myKinect.setFrustum();
  
  //Simple rotation of the 3D scene using the mouse
  translate(0,0,-2);
  rotateX(radians((mouseY*1f/height-0.5)*180));
  rotateY(radians((mouseX*1f/width-0.5)*180));
  translate(0,0,2);
 
  //Draw all received skeletons
  PSkeleton[] s=myKinect.getPSkeletons();
  for(int i=0;i<6;i++) s[i].draw();
}


