class Camera
{
  float angleX=-30;
float angleY=0;
float speedX=0;
float speedY=0;
float positionX=0;
float positionY=100;
float positionZ=-300;
float speed_positionZ=0;
  
  void mouseDragged(int distX, int distY)
  {
     //CHANGE THE ROTATION SPEED PROPORTIONALLY TO THE MAGNITUDE OF THE MOVEMENT
   if(keyPressed && key==' ')
   {
     positionX=positionX+distX*1;
     positionY=positionY+distY*1;
   }
   else
   {
     if (mouseButton == LEFT) 
     {
       speedX=speedX-distY*0.01;
       speedY=speedY+distX*0.01;
     }
     else if (mouseButton == RIGHT)
     {
       speed_positionZ=speed_positionZ+distY*0.08;
     }
   }
  }
  
  
  void view()
  {
    translate(positionX,positionY,positionZ);
    rotateX(radians(angleX));
    rotateY(radians(angleY));
    
        //UPDATE THE CAMERA ANGLE X,Y
  angleX=angleX+speedX;
  angleY=angleY+speedY;
  positionZ=positionZ+speed_positionZ;
  //REDUCE THE CAMERA SPEED BY 5%
  speedX=speedX*0.95;
  speedY=speedY*0.95;
  speed_positionZ=speed_positionZ*0.95;
  }
}
