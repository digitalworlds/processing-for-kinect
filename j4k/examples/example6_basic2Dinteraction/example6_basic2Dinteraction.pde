import edu.ufl.digitalworlds.j4k.*;

PKinect myKinect;
int x[];
int y[];
int buffer_x[];
int buffer_y[];
int buffer_pos;
PImage head;
PImage apple;
boolean holding_apple;
boolean inside_rect;

int counter=0;

void setup()
{
  size(500,500);
  head=loadImage("head.png");
  apple=loadImage("apple.png");
  myKinect=new PKinect(this);
  if(myKinect.start(true,PKinect.NUI_IMAGE_RESOLUTION_320x240,PKinect.NUI_IMAGE_RESOLUTION_640x480)==0)
    System.exit(0);
  
  x=new int[20];
  y=new int[20];
  buffer_x=new int[100];
  buffer_y=new int[100];
  buffer_pos=0;
  holding_apple=false;
  inside_rect=false;
}

void draw()
{
  counter=counter+1;
  
  background(255);
  strokeWeight(5);
     
   PSkeleton[] skeleton=myKinect.getPSkeletons();
  for(int skeleton_id=0;skeleton_id<6;skeleton_id++)
  {
    if(skeleton[skeleton_id].isTracked()==true)
    {
      for(int i=0;i<20;i++)
      {
        x[i]=skeleton[skeleton_id].get2DJointX(i);
        y[i]=skeleton[skeleton_id].get2DJointY(i);
      }
      
      buffer_x[buffer_pos]=x[PSkeleton.HAND_RIGHT];
      buffer_y[buffer_pos]=y[PSkeleton.HAND_RIGHT];
      buffer_pos=buffer_pos+1;
      if(buffer_pos==100) buffer_pos=0;
      
      //drawing commands...
      
      if((x[PSkeleton.HAND_LEFT]>0)&&(x[PSkeleton.HAND_LEFT]<100)&&
         (y[PSkeleton.HAND_LEFT]>100)&&(y[PSkeleton.HAND_LEFT]<140))
      {
         stroke(255,0,0);
         fill(255,128,128);
         rect(0,100,100,40);  
         if(inside_rect==false)
         {
           counter=0;
           inside_rect=true;
           if(holding_apple==true) holding_apple=false;
           else holding_apple=true;
           
           
           
         }
      }
      else
      {
        if(inside_rect==true)
        {
          stroke(255,0,0);
         fill(255,128,128);
         rect(0,100,100,40); 
        }
        else
        {
          stroke(0,255,0);
          fill(128,255,128);
          rect(0,100,100,40);
        }
        if(counter>200) inside_rect=false;
      }
      
      if(holding_apple==false)
         image(apple, 25 , 90 ,50,50);
      
      stroke(0,0,0);
      skeleton[skeleton_id].draw2D();
      
      
      
      int neck_size= (int)sqrt((x[PSkeleton.HEAD]-x[PSkeleton.SHOULDER_CENTER])*
                     (x[PSkeleton.HEAD]-x[PSkeleton.SHOULDER_CENTER])+
                     (y[PSkeleton.HEAD]-y[PSkeleton.SHOULDER_CENTER])*
                     (y[PSkeleton.HEAD]-y[PSkeleton.SHOULDER_CENTER]));
      
      
      image(head,x[PSkeleton.HEAD]-neck_size*0.5,y[PSkeleton.HEAD]-neck_size*0.8,
                 neck_size,neck_size*1.6);
      
      if(holding_apple==true)
      image(apple,x[PSkeleton.HAND_LEFT]-25,y[PSkeleton.HAND_LEFT]-50,50,50);
      
      
      stroke(255,0,0);
      for(int i=0;i<99;i++)
        if(i!=buffer_pos-1)
        line(buffer_x[i],buffer_y[i],buffer_x[i+1],buffer_y[i+1]);
      
      if(buffer_pos!=0)
      line(buffer_x[99],buffer_y[99],buffer_x[0],buffer_y[0]);
      
      //up to here
    }
  }
  
}

