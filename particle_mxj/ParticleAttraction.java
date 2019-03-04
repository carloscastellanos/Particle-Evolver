import com.cycling74.max.*;
import java.util.*;

public class ParticleAttraction extends MaxObject { 


// blur adapted from super fast blur by Mario Klingemann
// (http://incubator.quasimondo.com/processing/superfastblur.pde)

	private int numParticles  = 128;
	private int numAttractors = 1;
	private int blurRadius = 8;
	private int particleSize = 16;
	private int currentAttractor = -1;
	
	private int winWidth;
	private int winHeight;
	
	private float damp = 0.99;
    private float accel = 8;
    private float x = px = random(winWidth);
    private float y = py = random(winHeight);
    private float vx = random(-accel/2,accel/2);
    private float vy = random(-accel/2,accel/2);

	private Particle[] particle;
	private Attractor[] attractor;

	public class Particle {
  		float damp, accel;
  		float x,y,vx,vy,px,py;
  	
  		public Particle() {
  		
  		}
  	}


  void step() {
    forces();
    move();
  }
  void forces() {
    for (int i = 0; i < attractor.length; i++) {
      float d2 = sq(attractor[i].x-x) + sq(attractor[i].y-y);
      if (d2 > 0.1) {
        vx += accel * (attractor[i].x-x) / d2;
        vy += accel * (attractor[i].y-y) / d2;
      }
    }
  }
  void move() {
    px = x;
    py = y;
    x += vx;
    y += vy;
    vx *= damp;
    vy *= damp;
  }
}

public class Attractor {
  float x,y;
  int currentPoint = 0;
  public Attractor() {
    x = random(width);
    y = random(height);
  }
  void step() {
    x += (mouseX-x)/5.0;
    y += (mouseY-y)/5.0;
  }
}

void setup() {
  size(640,480);
  background(0);
  stroke(255);
  noFill();
  attractor = new Attractor[numAttractors];
  for (int i = 0; i < attractor.length; i++) {
    attractor[i] = new Attractor();
  }
  particle = new Particle[numParticles];
  for (int i = 0; i < particle.length; i++) {
    particle[i] = new Particle();
  }
  part = new BImage(particleSize,particleSize);
  float md = 0.5*sqrt(sq(part.width/2)+sq(part.height/2));
  for (int x = 0; x < part.width; x++) {
    for (int y = 0; y < part.height; y++) {
      float d = sqrt(sq(x-part.width/2)+sq(y-part.height/2));
      part.set(x,y,color(53-53*d/md,4-4*d/md,255-255*d/md));
    }  
  }
  part.alpha(part);
  imageMode(CENTER_DIAMETER);
  ellipseMode(CENTER_DIAMETER);
}

BImage part;

void loop() {
  if (mousePressed) {
    if (currentAttractor >= 0) {
      attractor[currentAttractor].step();
    }
    else {
      for (int i = 0; i < attractor.length; i++) {
        if ( sq(mouseX-attractor[i].x) + sq(mouseY-attractor[i].y) < 250) {
          currentAttractor = i;
          break;
        }
      }
    }
    background(0);
    stroke(255,128,0);
    for (int j = 0; j < particle.length; j++) {
      particle[j].step();
      ellipse(particle[j].x, particle[j].y, 5, 5);
    }
    noStroke();
    fill(255,128);
    for (int i = 0; i < attractor.length; i++) {
      ellipse(attractor[i].x, attractor[i].y, 25, 25);
    }
  }
  else {
    cursor(ARROW);
    for (int i = 0; i < attractor.length; i++) {
      if ( sq(mouseX-attractor[i].x) + sq(mouseY-attractor[i].y) < 250) {
        cursor(HAND);
        break;
      }
    }
    currentAttractor = -1;
    fastblur(g,blurRadius);
    for (int j = 0; j < particle.length; j++) {
      particle[j].step();
      blend(part,0,0,part.width,part.height,(int)particle[j].x,(int)particle[j].y,(int)particle[j].x+part.width,(int)particle[j].y+part.height,ADD);
    }
  }
}

void fastblur(BImage img,int radius){

  if (radius<1){
    return;
  }
  int w=img.width;
  int h=img.height;
  int wm=w-1;
  int hm=h-1;
  int wh=w*h;
  int div=radius+radius+1;
  int r[]=new int[wh];
  int g[]=new int[wh];
  int b[]=new int[wh];
  int rsum,gsum,bsum,x,y,i,p,p1,p2,yp,yi,yw;
  int vmin[] = new int[max(w,h)];
  int vmax[] = new int[max(w,h)];
  int[] pix=img.pixels;
  int dv[]=new int[256*div];
  for (i=0;i<256*div;i++){
     dv[i]=(i/div); 
  }
  
  yw=yi=0;
 
  for (y=0;y<h;y++){
    rsum=gsum=bsum=0;
    for(i=-radius;i<=radius;i++){
      p=pix[yi+min(wm,max(i,0))];
      rsum+=(p & 0xff0000)>>16;
      gsum+=(p & 0x00ff00)>>8;
      bsum+= p & 0x0000ff;
   }
    for (x=0;x<w;x++){
    
      r[yi]=dv[rsum];
      g[yi]=dv[gsum];
      b[yi]=dv[bsum];

      if(y==0){
        vmin[x]=min(x+radius+1,wm);
        vmax[x]=max(x-radius,0);
       } 
       p1=pix[yw+vmin[x]];
       p2=pix[yw+vmax[x]];

      rsum+=((p1 & 0xff0000)-(p2 & 0xff0000))>>16;
      gsum+=((p1 & 0x00ff00)-(p2 & 0x00ff00))>>8;
      bsum+= (p1 & 0x0000ff)-(p2 & 0x0000ff);
      yi++;
    }
    yw+=w;
  }
  
  for (x=0;x<w;x++){
    rsum=gsum=bsum=0;
    yp=-radius*w;
    for(i=-radius;i<=radius;i++){
      yi=max(0,yp)+x;
      rsum+=r[yi];
      gsum+=g[yi];
      bsum+=b[yi];
      yp+=w;
    }
    yi=x;
    for (y=0;y<h;y++){
      pix[yi]=0xff000000 | (dv[rsum]<<16) | (dv[gsum]<<8) | dv[bsum];
      if(x==0){
        vmin[y]=min(y+radius+1,hm)*w;
        vmax[y]=max(y-radius,0)*w;
      } 
      p1=x+vmin[y];
      p2=x+vmax[y];

      rsum+=r[p1]-r[p2];
      gsum+=g[p1]-g[p2];
      bsum+=b[p1]-b[p2];

      yi+=w;
    }
  }

}

