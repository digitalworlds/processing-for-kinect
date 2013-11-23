import edu.ufl.digitalworlds.j4k.*;

PKinect myKinect;
SkeletonServer server;

void setup()
{
  size(640,480,P3D);
  
  //Initialize one PKinect object and start the sensor
  myKinect=new PKinect(this);
  if(myKinect.start(true,PKinect.NUI_IMAGE_RESOLUTION_320x240,PKinect.NUI_IMAGE_RESOLUTION_640x480)==0)
  {
    println("ERROR: The Kinect device could not be initialized.");
    println("1. Check if the Microsoft's Kinect SDK was succesfully installed on this computer.");
    println("2. Check if the Kinect is plugged into a power outlet.");
    println("3. Check if the Kinect is connected to a USB port of this computer.");   
    System.exit(0);
   } 
   server=new SkeletonServer(myKinect,60001); 
}

void draw()
{   
  background(255);
  //Set the camera parameters
  myKinect.setFrustum();
  
  //Simple rotation of the 3D scene using the mouse
  translate(0,0,-2);
  rotateX(radians((mouseY*1f/height-0.5)*180));
  rotateY(radians((mouseX*1f/width-0.5)*180));
  translate(0,0,2);
 
  //Draw all tracked skeletons
  PSkeleton[] s=myKinect.getPSkeletons();
  for(int i=0;i<6;i++) s[i].draw();
}


