import edu.ufl.digitalworlds.j4k.*;

PKinect myKinect;
PImage video_frame;
PImage floor;
PImage bookcase;

void setup()
{
  size(640,480,P3D);
  video_frame=new PImage(640,480,ARGB);
  floor=loadImage("ceiling.png");
  bookcase=loadImage("bookcases.png");
  
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
   myKinect.computeUV(true);
}

void draw()
{   
  background(0);
  //Set the camera parameters
  myKinect.setFrustum();
  
  //Simple rotation of the 3D scene using the mouse
  translate(0,0,-2);
  rotateX(radians((mouseY*1f/height-0.5)*180));
  rotateY(radians((mouseX*1f/width-0.5)*180));
  translate(0,0,2);
 
  //Draw the depth map
  PDepthMap map=myKinect.getPDepthMap();
  map.maskPlayers();
  myKinect.updatePImage(video_frame);
  noStroke();
  map.draw(video_frame);
  
  //or draw lower resolution depthmap like that:
  //int skip=1;
  //map.draw(video_frame,skip);
  
  //Draw a 4meters x 4meters floor 1meter lower from the level of the camera
  pushMatrix();
  translate(0,-1,-2);
  rotateX(-PI/2);
  for(int i=0;i<4;i++)
    for(int j=0;j<4;j++)
      image(floor,-2+i,-2+j,1,1);
  popMatrix();
  
  //Draw the bookases on the back 
  pushMatrix();
  translate(0,0.1,-4);
  rotateZ(PI);
  image(bookcase,-2,-1.1,4,2.2);
  popMatrix();
  
  //Draw the bookases on the left 
  pushMatrix();
  translate(-2,0.1,-2);
  rotateY(PI/2);
  rotateZ(PI);
  image(bookcase,-2,-1.1,4,2.2);
  popMatrix();
  
  //Draw the bookases on the right
  pushMatrix();
  translate(2,0.1,-2);
  rotateY(-PI/2);
  rotateZ(PI);
  image(bookcase,-2,-1.1,4,2.2);
  popMatrix();
}


