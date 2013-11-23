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

import java.nio.ShortBuffer;

import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;


public class PDepthMap extends DepthMap{

	public PDepthMap(int w, int h) {
		super(w, h);
	}
	
	public PDepthMap(int w, int h, ShortBuffer sb) {
		super(w, h, sb);
	}

	public PDepthMap(int w, int h, short[] sb) {
		super(w, h, sb);
	}
	
	public void drawMesh()
	{
		drawMesh(3);
	}
	
	public void drawMesh(int skip)
	{
		if(PKinect.myParent==null) return;
		PGraphics g=PKinect.myParent.g;
		skip+=1;
		int skipX;
		int skipY;
		int idx;
		boolean draw_flag=true;
		boolean is_region=true;
		if(realX==null || realY==null) computeXY();
		
		g.pushMatrix();
		g.applyMatrix((float)transformation[0], (float)transformation[4], (float)transformation[8], (float)transformation[12], (float)transformation[1],(float) transformation[5], (float)transformation[9], (float)transformation[13], (float)transformation[2], (float)transformation[6], (float)transformation[10], (float)transformation[14], (float)transformation[3], (float)transformation[7], (float)transformation[11], (float)transformation[15]);
		int idx2;
		g.beginShape(PConstants.LINES);
		//HORIZONTAL LINES
		skipY=skip;
		skipX=1;
		for(int j=0;j<Dheight-skipY;j+=skipY)
	    {
	    	for(int i=0;i<Dwidth-skipX;i+=skipX)
	    	{
	    		idx=j*Dwidth+i;
	    		//CHECK IF A VALID DEPTH WAS ESTIMATED IN THIS PIXEL
	    		if(realZ[idx]<FLT_EPSILON || realZ[idx+skipX]<FLT_EPSILON )
	    			draw_flag=false;
	    		else draw_flag=true;
	    		
	    		
	    		//CHECK IF THE REGION IS DEPICTED IN THIS PIXEL
	    		if(draw_flag && mask!=null)
	    		{
	    			if (mask[idx]==false || mask[idx+skipX]==false ) 
	    				is_region=false;
	    			else is_region=true;
	    		}
	    		
	    		//DO NOT DRAW QUADS ON THE BORDERS BETWEEN OBJECTS
	    		if(draw_flag)
	    		{
	    			if(mask!=null)
	    				draw_flag=is_region;
	    			
	    			if(Math.abs(realZ[idx]-realZ[idx+skipX])>largest_z_diff) draw_flag=false;
	    		}
	    		
	    		
	    		if(draw_flag)
	    		{
	    			
	    				g.normal((realZ[idx+Dwidth*skipX]-realZ[idx])/1.0f,(realZ[idx+skipX]-realZ[idx])/1.0f,0.005f);
	        			idx2=idx;
	    				g.vertex(realX[idx2], realY[idx2], -realZ[idx2]);
	    				idx2=idx+skipX;
		    			g.vertex(realX[idx2], realY[idx2], -realZ[idx2]);
	    		}
	    	}
	    }
		//VERTICAL LINES
		skipX=skip;
		skipY=1;
		for(int i=0;i<Dwidth-skipX;i+=skipX)
		{
			for(int j=0;j<Dheight-skipY;j+=skipY)
			{
			    idx=j*Dwidth+i;
	    		//CHECK IF A VALID DEPTH WAS ESTIMATED IN THIS PIXEL
	    		if(realZ[idx]<FLT_EPSILON || realZ[idx+Dwidth*skipY]<FLT_EPSILON )
	    			draw_flag=false;
	    		else draw_flag=true;
	    		
	    		
	    		//CHECK IF THE REGION IS DEPICTED IN THIS PIXEL
	    		if(draw_flag && mask!=null)
	    		{
	    			if (mask[idx]==false || mask[idx+Dwidth*skipY]==false ) 
	    				is_region=false;
	    			else is_region=true;
	    		}
	    		
	    		//DO NOT DRAW QUADS ON THE BORDERS BETWEEN OBJECTS
	    		if(draw_flag)
	    		{
	    			if(mask!=null)
	    				draw_flag=is_region;
	    			
	    			if(Math.abs(realZ[idx]-realZ[idx+Dwidth*skipY])>largest_z_diff ) draw_flag=false;
	    		}
	    		
	    		
	    		if(draw_flag)
	    		{
	
	    			
	    				g.normal((realZ[idx+Dwidth*skipY]-realZ[idx])/1.0f,(realZ[idx+skipY]-realZ[idx])/1.0f,0.005f);
	        			idx2=idx;
	    				g.vertex(realX[idx2], realY[idx2], -realZ[idx2]);
	    				idx2=idx+Dwidth*skipY;
		    			g.vertex(realX[idx2], realY[idx2], -realZ[idx2]);
	    			
	    		}
	    	}
	    }
					
	    g.endShape();
	    g.popMatrix();
	}
	
	public void drawNormals()
	{
		drawNormals(0);
	}
	
	public void drawNormals(int skip)
	{
		if(PKinect.myParent==null) return;
		PGraphics g=PKinect.myParent.g;
		skip+=1;
		int idx;
		boolean draw_flag=true;
		boolean is_region=true;
		if(realX==null || realY==null) computeXY();
		
		
		g.pushMatrix();
		g.applyMatrix((float)transformation[0], (float)transformation[4], (float)transformation[8], (float)transformation[12], (float)transformation[1],(float) transformation[5], (float)transformation[9], (float)transformation[13], (float)transformation[2], (float)transformation[6], (float)transformation[10], (float)transformation[14], (float)transformation[3], (float)transformation[7], (float)transformation[11], (float)transformation[15]);
		int idx2;
		g.beginShape(PConstants.QUADS);
	    for(int i=0;i<Dwidth-skip;i+=skip)
	    {
	    	for(int j=0;j<Dheight-skip;j+=skip)
	    	{
	    		idx=j*Dwidth+i;
	    		//CHECK IF A VALID DEPTH WAS ESTIMATED IN THIS PIXEL
	    		if(realZ[idx]<FLT_EPSILON || realZ[idx+skip]<FLT_EPSILON || realZ[idx+Dwidth*skip]<FLT_EPSILON || realZ[idx+Dwidth*skip+skip]<FLT_EPSILON)
	    			draw_flag=false;
	    		else draw_flag=true;
	    		
	    		
	    		//CHECK IF THE REGION IS DEPICTED IN THIS PIXEL
	    		if(draw_flag && mask!=null)
	    		{
	    			if (mask[idx]==false || mask[idx+skip]==false || mask[idx+Dwidth*skip]==false || mask[idx+Dwidth*skip+skip]==false) 
	    				is_region=false;
	    			else is_region=true;
	    		}
	    		
	    		//DO NOT DRAW QUADS ON THE BORDERS BETWEEN OBJECTS
	    		if(draw_flag)
	    		{
	    			if(mask!=null)
	    				draw_flag=is_region;
	    			
	    			if(Math.abs(realZ[idx]-realZ[idx+skip])>largest_z_diff || Math.abs(realZ[idx+Dwidth*skip]-realZ[idx+Dwidth*skip+skip])>largest_z_diff || Math.abs(realZ[idx]-realZ[idx+Dwidth*skip])>largest_z_diff || Math.abs(realZ[idx+skip]-realZ[idx+Dwidth*skip+skip])>largest_z_diff) draw_flag=false;
	    		}
	    		
	    		
	    		if(draw_flag)
	    		{
	
	    	
	    				g.normal((realZ[idx+Dwidth*skip]-realZ[idx])/1.0f,(realZ[idx+skip]-realZ[idx])/1.0f,0.005f);
	        			idx2=idx;
	    				g.vertex(realX[idx2], realY[idx2], -realZ[idx2]);
	    				idx2=idx+skip;
		    			g.vertex(realX[idx2], realY[idx2], -realZ[idx2]);
		    			idx2=idx+Dwidth*skip+skip;
		    			g.vertex(realX[idx2], realY[idx2], -realZ[idx2]);
		    			idx2=idx+Dwidth*skip;
		    			g.vertex(realX[idx2], realY[idx2], -realZ[idx2]);
	    			
	    		}
	    	}
	    }
	    g.endShape();
	    g.popMatrix();	
	  }
	
	
	public void drawTexture(PImage tex)
	{
		drawTexture(tex,0);
	}
	
	public void drawTexture(PImage tex,int skip)
	{
		if(PKinect.myParent==null) return;
		PGraphics g=PKinect.myParent.g;
		skip+=1;
		int idx;
		boolean draw_flag=true;
		boolean is_region=true;
		if(realX==null || realY==null) computeXY();
		
		boolean ignoreUV=false;
		if(U==null ||V==null) ignoreUV=true;
		g.textureMode(PConstants.NORMAL);
		g.pushMatrix();
		g.applyMatrix((float)transformation[0], (float)transformation[4], (float)transformation[8], (float)transformation[12], (float)transformation[1],(float) transformation[5], (float)transformation[9], (float)transformation[13], (float)transformation[2], (float)transformation[6], (float)transformation[10], (float)transformation[14], (float)transformation[3], (float)transformation[7], (float)transformation[11], (float)transformation[15]);
		
		int idx2;
		g.beginShape(PConstants.QUADS);
		g.texture(tex);
	    for(int i=0;i<Dwidth-skip;i+=skip)
	    {
	    	for(int j=0;j<Dheight-skip;j+=skip)
	    	{
	    		idx=j*Dwidth+i;
	    		//CHECK IF A VALID DEPTH WAS ESTIMATED IN THIS PIXEL
	    		if(realZ[idx]<FLT_EPSILON || realZ[idx+skip]<FLT_EPSILON || realZ[idx+Dwidth*skip]<FLT_EPSILON || realZ[idx+Dwidth*skip+skip]<FLT_EPSILON)
	    			draw_flag=false;
	    		else draw_flag=true;
	    		
	    		
	    		//CHECK IF THE REGION IS DEPICTED IN THIS PIXEL
	    		if(draw_flag && mask!=null)
	    		{
	    			if (mask[idx]==false || mask[idx+skip]==false || mask[idx+Dwidth*skip]==false || mask[idx+Dwidth*skip+skip]==false) 
	    				is_region=false;
	    			else is_region=true;
	    		}
	    		
	    		//DO NOT DRAW QUADS ON THE BORDERS BETWEEN OBJECTS
	    		if(draw_flag)
	    		{
	    			if(mask!=null)
	    				draw_flag=is_region;
	    			
	    			if(Math.abs(realZ[idx]-realZ[idx+skip])>largest_z_diff || Math.abs(realZ[idx+Dwidth*skip]-realZ[idx+Dwidth*skip+skip])>largest_z_diff || Math.abs(realZ[idx]-realZ[idx+Dwidth*skip])>largest_z_diff || Math.abs(realZ[idx+skip]-realZ[idx+Dwidth*skip+skip])>largest_z_diff) draw_flag=false;
	    		}
	    		
	    		
	    		if(draw_flag)
	    		{
	
	    			
	    				//gl.glNormal3f((realZ[idx+Dwidth*skip]-realZ[idx])/1.0f,(realZ[idx+skip]-realZ[idx])/1.0f,0.005f);
	    				idx2=idx;
	    				if(!ignoreUV) g.vertex(realX[idx2], realY[idx2], -realZ[idx2],U[idx2], V[idx2]);
	    				else g.vertex(realX[idx2], realY[idx2], -realZ[idx2]);
	    				idx2=idx+skip;
	    				if(!ignoreUV) g.vertex(realX[idx2], realY[idx2], -realZ[idx2],U[idx2], V[idx2]);
	    				else g.vertex(realX[idx2], realY[idx2], -realZ[idx2]);
	    				idx2=idx+Dwidth*skip+skip;
	    				if(!ignoreUV) g.vertex(realX[idx2], realY[idx2], -realZ[idx2],U[idx2], V[idx2]);
	    				else g.vertex(realX[idx2], realY[idx2], -realZ[idx2]);    		
	    				idx2=idx+Dwidth*skip;
	    				if(!ignoreUV) g.vertex(realX[idx2], realY[idx2], -realZ[idx2],U[idx2], V[idx2]);
	    				else g.vertex(realX[idx2], realY[idx2], -realZ[idx2]);
	    			
	    		}
	    	}
	    }
	    g.endShape();
	    g.popMatrix();
	 }
	
	public void draw()
	{
		drawNormals(0);
	}
	
	public void draw(int skip)
	{
		drawNormals(skip);
	}
	
	public void draw(PImage tex)
	{
		drawTexture(tex,0);
	}
	
	public void draw(PImage tex,int skip)
	{
		drawTexture(tex,skip);
	}
}
