/**
 * Processing J4K
 * An open source Processing library for the Kinect SDK
 * http://research.dwi.ufl.edu/ufdw/j4k
 *
 * Copyright 2011 Angelos Barmpoutis
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author      Angelos Barmpoutis
 * @modified    11/17/2013
 * @version     1.0
 */

package edu.ufl.digitalworlds.j4k;

import processing.core.PConstants;
import processing.core.PGraphics;

public class PSkeleton extends Skeleton{
	
	public PSkeleton()
	{
		super();
	}
	
	public PSkeleton(Skeleton s)
	{
		skeleton_tracked=s.skeleton_tracked;
		joint_position=s.joint_position;
		id=s.id;
		times_drawn=s.times_drawn;
	}
	
	public static PSkeleton getPSkeleton(int id, float[] data, boolean[] flags)
	{
		if(id<0 || id>=J4KSDK.NUI_SKELETON_COUNT || data==null) return null;
		PSkeleton sk=new PSkeleton();
		sk.setPlayerID(id);
		if(flags!=null)sk.setIsTracked(flags[id]);
		
		float skeleton_data[]=new float[J4KSDK.NUI_SKELETON_POSITION_COUNT*3];
		System.arraycopy( data, id*J4KSDK.NUI_SKELETON_POSITION_COUNT*3, skeleton_data, 0, J4KSDK.NUI_SKELETON_POSITION_COUNT*3 );
		sk.setJointPositions(skeleton_data);
		return sk;
	}
	
	public void draw()
	{
		if(PKinect.myParent==null) return;
		
		PGraphics g=PKinect.myParent.g;
		if(skeleton_tracked)
		{	
			//1 MAIN BODY: HIP_CENTER, SPINE, SHOULDER_CENTER, HEAD
			g.beginShape(PConstants.LINES);
		
			g.vertex(-get3DJointX(HIP_CENTER),get3DJointY(HIP_CENTER),-get3DJointZ(HIP_CENTER));
			g.vertex(-get3DJointX(SPINE),get3DJointY(SPINE),-get3DJointZ(SPINE));
			
			g.vertex(-get3DJointX(SPINE),get3DJointY(SPINE),-get3DJointZ(SPINE));
			g.vertex(-get3DJointX(SHOULDER_CENTER),get3DJointY(SHOULDER_CENTER),-get3DJointZ(SHOULDER_CENTER));
			
			g.vertex(-get3DJointX(SHOULDER_CENTER),get3DJointY(SHOULDER_CENTER),-get3DJointZ(SHOULDER_CENTER));
			g.vertex(-get3DJointX(HEAD),get3DJointY(HEAD),-get3DJointZ(HEAD));
			
			//2 LEFT ARM: SHOULDER_CENTER, SHOULDER_LEFT, ELBOW_LEFT, WRIST_LEFT, HAND_LEFT
			g.vertex(-get3DJointX(SHOULDER_CENTER),get3DJointY(SHOULDER_CENTER),-get3DJointZ(SHOULDER_CENTER));
			g.vertex(-get3DJointX(SHOULDER_LEFT),get3DJointY(SHOULDER_LEFT),-get3DJointZ(SHOULDER_LEFT));
			
			g.vertex(-get3DJointX(SHOULDER_LEFT),get3DJointY(SHOULDER_LEFT),-get3DJointZ(SHOULDER_LEFT));
			g.vertex(-get3DJointX(ELBOW_LEFT),get3DJointY(ELBOW_LEFT),-get3DJointZ(ELBOW_LEFT));
			
			g.vertex(-get3DJointX(ELBOW_LEFT),get3DJointY(ELBOW_LEFT),-get3DJointZ(ELBOW_LEFT));
			g.vertex(-get3DJointX(WRIST_LEFT),get3DJointY(WRIST_LEFT),-get3DJointZ(WRIST_LEFT));
			
			g.vertex(-get3DJointX(WRIST_LEFT),get3DJointY(WRIST_LEFT),-get3DJointZ(WRIST_LEFT));
			g.vertex(-get3DJointX(HAND_LEFT),get3DJointY(HAND_LEFT),-get3DJointZ(HAND_LEFT));
			

			//3 RIGHT ARM: SHOULDER_CENTER, SHOULDER_RIGHT, ELBOW_RIGHT, WRIST_RIGHT, HAND_RIGHT
			g.vertex(-get3DJointX(SHOULDER_CENTER),get3DJointY(SHOULDER_CENTER),-get3DJointZ(SHOULDER_CENTER));
			g.vertex(-get3DJointX(SHOULDER_RIGHT),get3DJointY(SHOULDER_RIGHT),-get3DJointZ(SHOULDER_RIGHT));
			
			g.vertex(-get3DJointX(SHOULDER_RIGHT),get3DJointY(SHOULDER_RIGHT),-get3DJointZ(SHOULDER_RIGHT));
			g.vertex(-get3DJointX(ELBOW_RIGHT),get3DJointY(ELBOW_RIGHT),-get3DJointZ(ELBOW_RIGHT));
			
			g.vertex(-get3DJointX(ELBOW_RIGHT),get3DJointY(ELBOW_RIGHT),-get3DJointZ(ELBOW_RIGHT));
			g.vertex(-get3DJointX(WRIST_RIGHT),get3DJointY(WRIST_RIGHT),-get3DJointZ(WRIST_RIGHT));
			
			g.vertex(-get3DJointX(WRIST_RIGHT),get3DJointY(WRIST_RIGHT),-get3DJointZ(WRIST_RIGHT));
			g.vertex(-get3DJointX(HAND_RIGHT),get3DJointY(HAND_RIGHT),-get3DJointZ(HAND_RIGHT));
			
			//4 LEFT LEG: HIP_CENTER, HIP_LEFT, KNEE_LEFT, ANKLE_LEFT, FOOT_LEFT
			g.vertex(-get3DJointX(HIP_CENTER),get3DJointY(HIP_CENTER),-get3DJointZ(HIP_CENTER));
			g.vertex(-get3DJointX(HIP_LEFT),get3DJointY(HIP_LEFT),-get3DJointZ(HIP_LEFT));
			
			g.vertex(-get3DJointX(HIP_LEFT),get3DJointY(HIP_LEFT),-get3DJointZ(HIP_LEFT));
			g.vertex(-get3DJointX(KNEE_LEFT),get3DJointY(KNEE_LEFT),-get3DJointZ(KNEE_LEFT));
			
			g.vertex(-get3DJointX(KNEE_LEFT),get3DJointY(KNEE_LEFT),-get3DJointZ(KNEE_LEFT));
			g.vertex(-get3DJointX(ANKLE_LEFT),get3DJointY(ANKLE_LEFT),-get3DJointZ(ANKLE_LEFT));

			g.vertex(-get3DJointX(ANKLE_LEFT),get3DJointY(ANKLE_LEFT),-get3DJointZ(ANKLE_LEFT));
			g.vertex(-get3DJointX(FOOT_LEFT),get3DJointY(FOOT_LEFT),-get3DJointZ(FOOT_LEFT));
			
			//5 RIGHT LEG: HIP_CENTER, HIP_RIGHT, KNEE_RIGHT, ANKLE_RIGHT, FOOT_RIGHT
			g.vertex(-get3DJointX(HIP_CENTER),get3DJointY(HIP_CENTER),-get3DJointZ(HIP_CENTER));
			g.vertex(-get3DJointX(HIP_RIGHT),get3DJointY(HIP_RIGHT),-get3DJointZ(HIP_RIGHT));

			g.vertex(-get3DJointX(HIP_RIGHT),get3DJointY(HIP_RIGHT),-get3DJointZ(HIP_RIGHT));
			g.vertex(-get3DJointX(KNEE_RIGHT),get3DJointY(KNEE_RIGHT),-get3DJointZ(KNEE_RIGHT));
			
			g.vertex(-get3DJointX(KNEE_RIGHT),get3DJointY(KNEE_RIGHT),-get3DJointZ(KNEE_RIGHT));
			g.vertex(-get3DJointX(ANKLE_RIGHT),get3DJointY(ANKLE_RIGHT),-get3DJointZ(ANKLE_RIGHT));
			
			g.vertex(-get3DJointX(ANKLE_RIGHT),get3DJointY(ANKLE_RIGHT),-get3DJointZ(ANKLE_RIGHT));
			g.vertex(-get3DJointX(FOOT_RIGHT),get3DJointY(FOOT_RIGHT),-get3DJointZ(FOOT_RIGHT));
			g.endShape();
			
		}

	}
	
	public int get2DJointX(int joint_id)
	{
	  float fx=0.0f;
	  float z=get3DJointZ(joint_id);
	  float x=get3DJointX(joint_id);
	  if(z > PKinect.FLT_EPSILON)
	    {
	        //
	        // Center of depth sensor is at (0,0,0) in skeleton space, and
	        // and (160,120) in depth image coordinates.  Note that positive Y
	        // is up in skeleton space and down in image coordinates.
	        //
	        fx=0.5f + x * ( PKinect.NUI_CAMERA_DEPTH_NOMINAL_FOCAL_LENGTH_IN_PIXELS / z ) / 320.0f;    
	    } 
	    return (int) ( fx * PKinect.myParent.g.width + 0.5f );
	}

	public int get2DJointY(int joint_id)
	{
	  float fy=0.0f;
	  float z=get3DJointZ(joint_id);
	  float y=get3DJointY(joint_id);
	  if(z > PKinect.FLT_EPSILON)
	    {
	        //
	        // Center of depth sensor is at (0,0,0) in skeleton space, and
	        // and (160,120) in depth image coordinates.  Note that positive Y
	        // is up in skeleton space and down in image coordinates.
	        //
	        fy = 0.5f - y * ( PKinect.NUI_CAMERA_DEPTH_NOMINAL_FOCAL_LENGTH_IN_PIXELS / z ) / 240.0f;
	    } 
	    return (int) ( fy * PKinect.myParent.g.height + 0.5f );
	}

	
	public void draw2D()
	{
		if(PKinect.myParent==null) return;
		PGraphics g=PKinect.myParent.g;
		if(skeleton_tracked)
		{
	    //1 MAIN BODY
	    g.line(get2DJointX(HIP_CENTER),get2DJointY(HIP_CENTER),get2DJointX(SPINE),get2DJointY(SPINE));
	    g.line(get2DJointX(SPINE),get2DJointY(SPINE),get2DJointX(SHOULDER_CENTER),get2DJointY(SHOULDER_CENTER));
	    g.line(get2DJointX(SHOULDER_CENTER),get2DJointY(SHOULDER_CENTER),get2DJointX(HEAD),get2DJointY(HEAD));
	    //2 LEFT ARM
	    g.line(get2DJointX(SHOULDER_CENTER),get2DJointY(SHOULDER_CENTER),get2DJointX(SHOULDER_LEFT),get2DJointY(SHOULDER_LEFT));
	    g.line(get2DJointX(SHOULDER_LEFT),get2DJointY(SHOULDER_LEFT),get2DJointX(ELBOW_LEFT),get2DJointY(ELBOW_LEFT));
	    g.line(get2DJointX(ELBOW_LEFT),get2DJointY(ELBOW_LEFT),get2DJointX(WRIST_LEFT),get2DJointY(WRIST_LEFT));
	    g.line(get2DJointX(WRIST_LEFT),get2DJointY(WRIST_LEFT),get2DJointX(HAND_LEFT),get2DJointY(HAND_LEFT));
	    //3 RIGHT ARM
	    g.line(get2DJointX(SHOULDER_CENTER),get2DJointY(SHOULDER_CENTER),get2DJointX(SHOULDER_RIGHT),get2DJointY(SHOULDER_RIGHT));
	    g.line(get2DJointX(SHOULDER_RIGHT),get2DJointY(SHOULDER_RIGHT),get2DJointX(ELBOW_RIGHT),get2DJointY(ELBOW_RIGHT));
	    g.line(get2DJointX(ELBOW_RIGHT),get2DJointY(ELBOW_RIGHT),get2DJointX(WRIST_RIGHT),get2DJointY(WRIST_RIGHT));
	    g.line(get2DJointX(WRIST_RIGHT),get2DJointY(WRIST_RIGHT),get2DJointX(HAND_RIGHT),get2DJointY(HAND_RIGHT));
	    //4 LEFT LEG
	    g.line(get2DJointX(HIP_CENTER),get2DJointY(HIP_CENTER),get2DJointX(HIP_LEFT),get2DJointY(HIP_LEFT));
	    g.line(get2DJointX(HIP_LEFT),get2DJointY(HIP_LEFT),get2DJointX(KNEE_LEFT),get2DJointY(KNEE_LEFT));
	    g.line(get2DJointX(KNEE_LEFT),get2DJointY(KNEE_LEFT),get2DJointX(ANKLE_LEFT),get2DJointY(ANKLE_LEFT));
	    g.line(get2DJointX(ANKLE_LEFT),get2DJointY(ANKLE_LEFT),get2DJointX(FOOT_LEFT),get2DJointY(FOOT_LEFT));
	    //5 RIGHT LEG
	    g.line(get2DJointX(HIP_CENTER),get2DJointY(HIP_CENTER),get2DJointX(HIP_RIGHT),get2DJointY(HIP_RIGHT));
	    g.line(get2DJointX(HIP_RIGHT),get2DJointY(HIP_RIGHT),get2DJointX(KNEE_RIGHT),get2DJointY(KNEE_RIGHT));
	    g.line(get2DJointX(KNEE_RIGHT),get2DJointY(KNEE_RIGHT),get2DJointX(ANKLE_RIGHT),get2DJointY(ANKLE_RIGHT));
	    g.line(get2DJointX(ANKLE_RIGHT),get2DJointY(ANKLE_RIGHT),get2DJointX(FOOT_RIGHT),get2DJointY(FOOT_RIGHT));
		}	
	}

}
