import edu.ufl.digitalworlds.j4k.*;

PKinect myKinect;

void setup()
{
  size(640,480);
  
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
}

void draw()
{   
  background(255);
  
  //Draw all tracked skeletons
  PSkeleton[] s=myKinect.getPSkeletons();
  for(int i=0;i<6;i++) s[i].draw2D();
}


