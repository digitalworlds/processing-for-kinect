class CustomKinect extends PKinect
{
  CustomKinect(PApplet p){super(p);}
  
 void onDepthFrameEvent(short[] packed_depth, int[] U, int[] V) {
    PDepthMap new_map=new PDepthMap(depthWidth(),depthHeight(),packed_depth);
    if(U!=null && V!=null)
      new_map.setUV(U,V,videoWidth(),videoHeight());
    map=new_map;
  }
  
  void onSkeletonFrameEvent(float[] joint_data, boolean[] tracked) {
   PSkeleton[] new_skeletons=new PSkeleton[tracked.length];
   for(int i=0;i<tracked.length;i++)
      new_skeletons[i]=PSkeleton.getPSkeleton(i, joint_data, tracked);
   skeletons=new_skeletons;
  }
  
  void onVideoFrameEvent(byte[] video_data) {
    //updatePImage(video_frame,video_data);
  }

  
}
