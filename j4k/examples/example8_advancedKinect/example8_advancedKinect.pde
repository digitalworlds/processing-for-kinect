import edu.ufl.digitalworlds.j4k.*;

CustomKinect myKinect;
PDepthMap map;
PImage video_frame;
PSkeleton skeletons[];

void setup()
{
  size(640,480,P3D);
  
  video_frame=new PImage(640,480,ARGB);
 
  //Initialize one PKinect object and start the sensor
  myKinect=new CustomKinect(this);
  if(myKinect.start(true,PKinect.NUI_IMAGE_RESOLUTION_320x240,PKinect.NUI_IMAGE_RESOLUTION_640x480)==0)
  {
    println("ERROR: The Kinect device could not be initialized.");
    println("1. Check if the Microsoft's Kinect SDK was succesfully installed on this computer.");
    println("2. Check if the Kinect is plugged into a power outlet.");
    println("3. Check if the Kinect is connected to a USB port of this computer.");   
    System.exit(0);
   }  
   myKinect.computeUV(true);
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
 
  //Draw the depth map
  if(map!=null)
  {
    noStroke();
    lights();
    map.draw();
    //map.draw(video_frame);
  }
  
  stroke(0);
  //Draw the tracked skeletons
  if(skeletons!=null)
  {
    for(int i=0;i<6;i++) skeletons[i].draw();
  }
}


