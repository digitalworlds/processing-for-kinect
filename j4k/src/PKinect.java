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

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

public class PKinect extends J4KSDK{

	public static PApplet myParent;
	
	public final static String VERSION = "1.0";

	public PKinect(PApplet theParent)
	{
		super();
		PKinect.myParent=theParent;
	}
	
	@Override
	public void onDepthFrameEvent(short[] arg0, int[] arg1, int[] arg2) {}

	@Override
	public void onSkeletonFrameEvent(float[] arg0, boolean[] arg1) {}

	@Override
	public void onVideoFrameEvent(byte[] arg0) {}

	public PSkeleton[] getPSkeletons()
	{
		Skeleton[] s=getSkeletons();
		if(s==null) {PSkeleton[] ps=new PSkeleton[J4KSDK.NUI_SKELETON_COUNT];for(int i=0;i<J4KSDK.NUI_SKELETON_COUNT;i++)ps[i]=new PSkeleton();return ps;}
		PSkeleton[] ps=new PSkeleton[s.length];
		for(int i=0;i<s.length;i++)if(s[i]!=null)ps[i]=new PSkeleton(s[i]);else ps[i]=new PSkeleton();
		return ps;
	}
	
	public PDepthMap getPDepthMap()
	{
		short[] d=getDepthPacked();
		if(d==null) return new PDepthMap(depthWidth(),depthHeight());
		int[] u=getU();
		int[] v=getV();
		PDepthMap map=new PDepthMap(depthWidth(), depthHeight(),d);
		if(u!=null && v!=null) map.setUV(u, v, videoWidth(), videoHeight());
		return map;
	}
	
	private BufferedImage video_frame;
	private byte[] video_frame_data;
	
	public void updatePImage(PImage pimg,byte[] pixels)
	{
		if(pixels==null) return;
		
		if(video_frame==null || video_frame.getWidth()!=videoWidth())
		{
			video_frame=new BufferedImage(videoWidth(),videoHeight(),BufferedImage.TYPE_4BYTE_ABGR);
			video_frame_data=((DataBufferByte) video_frame.getRaster().getDataBuffer()).getData();
		}
		final byte tmp=(byte)255;
		final int l=pixels.length;
		for(int i=0;i<l;i+=4) pixels[i+3]=tmp;
		
		System.arraycopy(pixels, 0, video_frame_data, 1, pixels.length-1);
	    video_frame.getRGB(0, 0, pimg.width, pimg.height, pimg.pixels, 0, pimg.width);
	    pimg.updatePixels();
		
	}
	
	public void updatePImage(PImage pimg)
	{
		byte[] pixels=this.getVideoData();
		updatePImage(pimg,pixels);
	}
	
	public void setFrustum()
	{
		if(PKinect.myParent==null) return;
		PGraphics g=PKinect.myParent.g;
		float w = (float)g.width / (float)g.height;
		g.frustum(-0.1f*w, 0.1f*w, -0.1f, 0.1f, 0.2215f, 100.0f);//Vertical FOV 48.6 degrees
		g.resetMatrix();
		g.scale(1, -1, 1);
	}
	
	public static void drawFOV()
	{
		drawFOV(0.8f,4.0f);
	}
	
	public static void drawFOV(float dnear, float dfar)
	{
		if(PKinect.myParent==null) return;
		PGraphics g=PKinect.myParent.g;
		
		//Near Rectangle:(90cm x 67cm)  Far Rectangle:(4.6m x 3.36m)	
		float w=(320f/2.0f)/285.63f;
		int ymin=0;//max(0-allignY,0);
		int xmin=0;//max(0-allignX,0);
		int ymax=240;//min(240-allignY,240);
		int xmax=320;//min(320-allignX,320);

		g.beginShape(PConstants.LINES);
		g.vertex((xmin-160.0f)/160.0f*dnear*w,-(ymin-120.0f)/160.0f*dnear*w,-dnear);
		g.vertex((xmax-160.0f)/160.0f*dnear*w,-(ymin-120.0f)/160.0f*dnear*w,-dnear);

		g.vertex((xmax-160.0f)/160*dnear*w,-(ymin-120.0f)/160.0f*dnear*w,-dnear);
		g.vertex((xmax-160.0f)/160*dnear*w,-(ymax-120.0f)/160.0f*dnear*w,-dnear);
			

		g.vertex((xmax-160.0f)/160*dnear*w,-(ymax-120.0f)/160.0f*dnear*w,-dnear);
		g.vertex((xmin-160.0f)/160*dnear*w,-(ymax-120.0f)/160.0f*dnear*w,-dnear);

		g.vertex((xmin-160.0f)/160*dnear*w,-(ymax-120.0f)/160.0f*dnear*w,-dnear);
		g.vertex((xmin-160.0f)/160*dnear*w,-(ymin-120.0f)/160.0f*dnear*w,-dnear);
		
		g.vertex((xmin-160.0f)/160*dfar*w,-(ymin-120.0f)/160.0f*dfar*w,-dfar);
		g.vertex((xmax-160.0f)/160*dfar*w,-(ymin-120.0f)/160.0f*dfar*w,-dfar);

		g.vertex((xmax-160.0f)/160*dfar*w,-(ymin-120.0f)/160.0f*dfar*w,-dfar);
		g.vertex((xmax-160.0f)/160*dfar*w,-(ymax-120.0f)/160.0f*dfar*w,-dfar);
			
		g.vertex((xmax-160.0f)/160*dfar*w,-(ymax-120.0f)/160.0f*dfar*w,-dfar);
		g.vertex((xmin-160.0f)/160*dfar*w,-(ymax-120.0f)/160.0f*dfar*w,-dfar);

		g.vertex((xmin-160.0f)/160*dfar*w,-(ymax-120.0f)/160.0f*dfar*w,-dfar);
		g.vertex((xmin-160.0f)/160*dfar*w,-(ymin-120.0f)/160.0f*dfar*w,-dfar);

			int i;
			for(i=xmin;i<xmax;i+=30)
			{
				g.vertex((i-160.0f)/160*dfar*w,-(ymin-120.0f)/160.0f*dfar*w,-dfar);
				g.vertex((i-160.0f)/160*dfar*w,-(ymax-120.0f)/160.0f*dfar*w,-dfar);
			}
			for(i=ymin;i<ymax;i+=30)
			{
				g.vertex((xmax-160.0f)/160*dfar*w,-(i-120.0f)/160.0f*dfar*w,-dfar);
				g.vertex((xmin-160.0f)/160*dfar*w,-(i-120.0f)/160.0f*dfar*w,-dfar);
			}
			
			g.vertex((xmin-160.0f)/160*dnear*w,-(ymin-120.0f)/160.0f*dnear*w,-dnear);
			g.vertex((xmin-160.0f)/160*dfar*w,-(ymin-120.0f)/160.0f*dfar*w,-dfar);
			
			for(i=ymin;i<ymax;i+=30)
			{
				g.vertex((xmin-160.0f)/160*dnear*w,-(i-120.0f)/160.0f*dnear*w,-dnear);
				g.vertex((xmin-160.0f)/160*dfar*w,-(i-120.0f)/160.0f*dfar*w,-dfar);
			}

			g.vertex((xmax-160.0f)/160*dnear*w,-(ymin-120.0f)/160.0f*dnear*w,-dnear);
			g.vertex((xmax-160.0f)/160*dfar*w,-(ymin-120.0f)/160.0f*dfar*w,-dfar);
			
			for(i=ymin;i<ymax;i+=30)
			{
				g.vertex((xmax-160.0f)/160*dnear*w,-(i-120.0f)/160.0f*dnear*w,-dnear);
				g.vertex((xmax-160.0f)/160*dfar*w,-(i-120.0f)/160.0f*dfar*w,-dfar);
			}

			g.vertex((xmax-160.0f)/160*dnear*w,-(ymax-120.0f)/160.0f*dnear*w,-dnear);
			g.vertex((xmax-160.0f)/160*dfar*w,-(ymax-120.0f)/160.0f*dfar*w,-dfar);
			
			for(i=xmin;i<xmax;i+=30)
			{
				g.vertex((i-160.0f)/160*dnear*w,-(ymin-120.0f)/160.0f*dnear*w,-dnear);
				g.vertex((i-160.0f)/160*dfar*w,-(ymin-120.0f)/160.0f*dfar*w,-dfar);
		
			}
			
			g.vertex((xmin-160.0f)/160*dnear*w,-(ymax-120.0f)/160.0f*dnear*w,-dnear);
			g.vertex((xmin-160.0f)/160*dfar*w,-(ymax-120.0f)/160.0f*dfar*w,-dfar);

			for(i=xmin;i<xmax;i+=30)
			{
				g.vertex((i-160.0f)/160*dnear*w,-(ymax-120.0f)/160.0f*dnear*w,-dnear);
				g.vertex((i-160.0f)/160*dfar*w,-(ymax-120.0f)/160.0f*dfar*w,-dfar);
			}
			
		g.endShape();
	}

	
}
