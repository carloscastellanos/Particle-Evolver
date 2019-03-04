import oscP5.*;
import oscP5.osc.*;

  // create the required instances for the osc communication
OscP5 oscP5;

  // reveiveAtPort is the number of the network-port listing for incoming osc messages. 
int receiveAtPort;
  
  // sendToPort is the number of the network-port sending osc messages.
int sendToPort;
  
  // the network-address, messages will be sent to.
String host;



void initOsc() {
    receiveAtPort = 50000;
    sendToPort = 12000;
    host = "127.0.0.1"; 
    oscP5 = new OscP5(this,host,sendToPort,receiveAtPort,"oscEvent");
    // connect to temp
    OscMessage oscMsg = oscP5.newMsg("/temp/connect/"+receiveAtPort+"/oscP5");
    oscMsg.add("/livedata");
    oscP5.sendMsg(oscMsg);
}

void oscEvent(OscIn oscIn) {
  println("received ...");
  analyseMessage(oscIn);
}

void analyseMessage(OscIn oscIn) { 
   if(oscIn.checkAddrPattern("/livedata")) {
       if(oscIn.checkTypetag("i")) {
         int p = oscIn.getInt(0);
         fillRect(0,p,0);
         System.out.println("OscMessage >>> "+oscIn.getAddrPattern()+"   "+oscIn.getTypetag());
      }
    } else if (oscIn.checkAddrPattern("/temp/ping")) {
      OscMessage oscMsg = oscP5.newMsg("/temp/pingreturn/"+receiveAtPort);
      oscP5.sendMsg(oscMsg);
    } else {
      System.out.println("OscMessage >>> "+oscIn.getAddrPattern()+"   "+oscIn.getTypetag());
      Object[] o = oscIn.getData();
      for(int i=0;i<o.length;i++) {
      if(o[i] instanceof byte[]) {
        //println("BYTE "+((byte[])o[i]).length);
      }
        println(i+"  "+o[i]);
      }
    }
}



void simpleOscMessage() {
  OscMessage oscMsg = oscP5.newMsg("/test");  // create and prepare the new osc message
  oscMsg.add("abcde");  // add a string to the osc message
  oscMsg.add(12.44);  // add a float to the osc message
  oscMsg.add(12);  // add an int to the osc message
  
  oscP5.sendMsg(oscMsg);  // send the osc message
}


void simpleOscBundle() {
  OscBundle b = new OscBundle();
  OscMessage m = new OscMessage("/test");
  m.add(100);
  b.add(m);
  m = new OscMessage("/foo");
  m.add("a foo");
  b.add(m);
  oscP5.sendBundle(b);
}





void setup(){
  background(0);
  size(400, 400);   
  initOsc();
}

void loop(){}


void mousePressed() {
  simpleOscMessage();
}

void fillRect(int r, int g, int b) {
  fill(r, g, b);
  rect(0, 0,width,height);
  println(r+"  "+g+"  "+b);
}


