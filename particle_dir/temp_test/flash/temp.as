/**
* code based on flosc by
* Ben Chun, 2002
*
*/



/*

	Initialize the Client.
	The server's receiving port is set to 13000 by default.
	if the tcp port in your server is different than 13000,
	make a search for 13000 and replace the number with
	the appropriate port number.
*/

tempCommClient = function(theMsgHandler,theIP,thePort,theIDname,theMsgArray) {
	this.msgHandler = theMsgHandler;
	this.IPaddress = theIP;
	this.port = 13000;
	this.incomingUpdated = false;
	this.xmlArray = new Array();
	this.xmlReturn = new Array();
	this.serverArray = new Array();
	this.initFlag = true;
	this.msgArray = theMsgArray;
	this.IDname = theIDname;
}


/*
	initializing the XML socket
*/
	tempCommClient.prototype.connect = function() {
		trace("connecting ... ");
		this.mSocket = new XMLSocket();
		this.mSocket.onConnect = this.handleConnect;
		this.mSocket.onClose =this.handleClose;
		this.mSocket.onXML = this.handleIncoming;
		this.mSocket.displayPacketHeaders = this.displayPacketHeaders;
		this.mSocket.parseMessages = this.parseMessages;
		this.mSocket.parseNode = this.parseNode;
		this.mSocket.sendInit = this.sendInit;
		this.mSocket.xmlArray = new Array();
		this.mSocket.xmlReturn = new Array();
		this.mSocket.xmlStructure = "";
		this.mSocket.serverArray = new Array();
		this.mSocket.mID = "null";
		this.mSocket.initFlag = true;
		this.mSocket.msgArray = this.msgArray;
		this.mSocket.msgHandler = this.msgHandler;
		this.mSocket.IDname = this.IDname;

		if (!this.mSocket.connect(this.IPaddress, this.port)) {
			this.connectionStatus;
			trace("failed");
		} else {
			trace("successful");
		}
	}


	tempCommClient.prototype.disconnect = function() {
		this.mSocket.close();
		this.mSocket.connected = false;
		return true;
	}
	
	
	
	tempCommClient.prototype.getStatus = function() {
		return this.mSocket.connected;
	}
	

	tempCommClient.prototype.handleConnect = function(succeed) {
		if (succeed) 
		{
			this.connected = true;
			this.initFlag = true;
			//trace("initFlag "+this.initFlag);
		}
		else
		{
			this.connected = false;
			trace(" connecting was not successful.");
		}
	}
	
	tempCommClient.prototype.sendInit = function() {
		trace("send init, trying ....")
		if (this.initFlag) {
			tmpMsgs = "";
			if (this.msgArray.length>0) tmpMsgs +=this.msgArray[0];
			for(i=1;i<this.msgArray.length;i++) {
				tmpMsgs +=","+this.msgArray[i];
			}
			xmlOut = new XML();
			osc = xmlOut.createElement("OSCPACKET");
			osc.attributes.MODE = 1;
			osc.attributes.TIME = 0;
			osc.attributes.IDNAME = this.IDname;
			osc.attributes.CONNECT = escape(tmpMsgs);
			xmlOut.appendChild(osc);
			//trace(xmlOut+"  "+this.mSocket+"    "+this.mSocket.connected);
			if (this.mSocket && this.mSocket.connected) {
				trace("...init sent.")
				this.mSocket.send(xmlOut);
			}
			this.incomingUpdated = true;
			this.initFlag=false;
		} else {
			trace("init sending failed");
		}
	}
	
	
	tempCommClient.prototype.sendOSC = function(name, content, types) {
		xmlOut = new XML();
		osc = xmlOut.createElement("OSCPACKET");
		osc.attributes.TIME = 0;
		osc.attributes.ID = this.mSocket.mID;
		message = xmlOut.createElement("MSG");
		message.attributes.RECEIVER = "all";
		message.attributes.NAME = name;
			i=0;
			while(i<types.length) {
				
				argument = xmlOut.createElement("ARG");
				// NOTE : the server expects all strings to be encoded
				// with the escape function.
				if (types[i]=="s") {
					argument.attributes.VALUE = escape(content[i]);
					argument.attributes.TYPE = types.substring(i,i+1);
				} else {
					argument.attributes.VALUE = content[i];
					argument.attributes.TYPE = types.substring(i,i+1);
				}
				// NOTE : to send more than one argument, just create
				// more elements and appendChild them to the message.
				// the same goes for multiple messages in a packet.
				message.appendChild(argument);
				i++;
			}
		osc.appendChild(message);
		xmlOut.appendChild(osc);
		if (this.mSocket && this.mSocket.connected) {
			this.mSocket.send(xmlOut);
			//trace("Sent XML-encoded OSC destined for "+destAddr+", port "+destPort+"\n");
		} else {
			trace("sending failed");
		}
	}

	
	/*
		a control-handler for incoming messages
	*/
	tempCommClient.prototype.displayPacketHeaders = function(packet) {
		trace("** OSC Packet from "+packet.address+", port "+packet.port+" for time "+packet.time+"   sender id : "+packet.senderID+"\n");
	}
	
	
	
	tempCommClient.prototype.handleClose = function() {
		trace("The server at "+IPaddress+" has terminated the connection.\n");
		this.connected = false;
		trace("... disconnected "+this.mSocket.serverArray);
		trace(serverArray.length);
		if (serverArray.length>0) {
			this.connect(serverArray[0],13000);
		}
	}

	tempCommClient.prototype.checkConnection = function() {
		trace("connected to : "+this.IPaddress+":"+this.port+" > "+this.mSocket.connected+" ID:"+thisgetID());
	}
	
	tempCommClient.prototype.getID = function() {
		return this.mSocket.mID;	
	}
	
	tempCommClient.prototype.handleIncoming = function(xmlIn) {
		// USEFUL DEBUG - display the raw xml data in the output window
		//trace(xmlIn);
		// parse out the packet information
		e = xmlIn.firstChild;
		if (e != null && e.nodeName == "OSCPACKET") {
			tmpID = e.attributes.id;
			tmpSenderName = e.attributes.idname;
			mPacket = new OSCPacket(e.attributes.address, e.attributes.port, e.attributes.time, tmpID, xmlIn);
			//this.displayPacketHeaders(mPacket);
			this.parseMessages(xmlIn,tmpID);
		}
		// tell the text field manager it's time to scroll
		this.incomingUpdated = true;
	}
	
	
	tempCommClient.prototype.parseMessages = function(node,theSenderID) {
	if (node.nodeName == "MSG") 
	{
		//trace("-------------------------------");
		tmpName = node.attributes.NAME;
		//trace("Message name: "+tmpName+"\n");
		this.xmlArray = new Array();
		this.xmlArray.push(0);
		
		this.xmlReturn = new Array();
		this.xmlStructure = "";
		
		this.parseNode(node);
		this.msgHandler.handleMsg(theSenderID,tmpName,this.xmlReturn,this.xmlStructure);
	} 
	else if (node.nodeName == "SYSTEM") 
	{	tmpID = node.attributes.ID;
		if (node.attributes.ID!=null)  {
			this.mID=node.attributes.ID;
			trace("INIT ID :"+this.mID);
		}
		for (var child = node.firstChild; child != null; child=child.nextSibling) {
			if (child.nodeName == "SERVER") {
				serverArray = new Array();
				trace("SERVER");
				for (var cChild = child.firstChild; cChild != null; cChild=cChild.nextSibling) {
					if (cChild.nodeName == "IP") {
						trace(("another server : ")+cChild+"  "+cChild.firstChild.nodeValue);
						serverArray.push(cChild.firstChild.nodeValue);
					}
				}
			}
			trace("Alternative Servers: "+serverArray.length+"  "+serverArray);
		}
		
	}
	else 
	{
		// look recursively for a message node
		for (var child = node.firstChild; child != null; child=child.nextSibling) {
			this.parseMessages(child,theSenderID);
	}
	}
}
	tempCommClient.prototype.parseNode = function(node) {
		theArray = "";
		this.xmlArray.push(-1);
		tmpLength = this.xmlArray.length;
		if (tmpLength>2) this.xmlArray[tmpLength-2]++;
		for (var child = node.firstChild; child != null; child=child.nextSibling) {
			if (child.nodeName == "ARG") {
				this.xmlArray[this.xmlArray.length-1]++;
				this.xmlReturn.insertAt(child.attributes.VALUE,this.xmlArray);
				this.xmlStructure += child.attributes.TYPE;
				
				theArray += xmlArray+"\t   >   "+"Arg type "+child.attributes.TYPE+"  "+child.attributes.VALUE+"\n";
			}
			if (child.nodeName == "ARRAY") {
				this.xmlStructure += "[";
				this.parseNode(child);
				if (child.firstChild.nodeValue == null) {
					this.xmlStructure += "]";
					this.xmlArray.pop();
					arrayCount--;
				}
			}
		}
		//trace(theArray + this.xmlReturn);
	}


OSCPacket = function(address, port, time, senderID,xmlData) {
	this.address 	= address;
	this.port 		= port;
	this.time 		= time;
	this.senderID 	= senderID;
	this.xmlData 	= xmlData;
	//trace(xmlData);
}

// ===========================================
/*
	* Array.prototype.get
	* Array.prototype._$insertAt
	* Array.prototype.insertAt
	by
	Alessandro Crgunola
	alessandro@sephiroth.it
	http://www.sephiroth.it
*/
Array.prototype.get = function () {
	if (arguments[0] instanceof Array) {
		arguments = arguments[0];
	}
	return arguments.length == 1 ? this[arguments[0]] : this[arguments[0]].get (arguments.splice (1));
};

ASSetPropFlags(Array.prototype,"get",1);

Array.prototype._$insertAt = function () {
   if (!(arguments[0].length > 0)) {
      return;
   }
   
   var value = arguments[1];
   arguments = arguments[0];
   
   while (arguments.length > 0) { 
	   
      var _$key = arguments[0];
      if (!(this[_$key] instanceof Array)) {
         this[_$key] = new Array ();
      }
      if (arguments.length < 2) {
         this[_$key] = value;
         return;
      }
      arguments.splice (0, 1);
      var _$args = arguments.splice (0);
      this[_$key]._$insertAt (_$args, value);
   }
};

Array.prototype.insertAt = function (value) {
	if (arguments[1] instanceof Array) {
		tmpArg = arguments[1];
		arguments.splice(1);
		for(i=0;i<tmpArg.length;i++) {
			arguments.push(tmpArg[i]);
		}
	}
   this._$insertAt (arguments.splice(1), value);
};

ASSetPropFlags (Array.prototype, "_$insertAt,insertAt", 1);


/* Example usage:
my_array = [["aber hallo", 0], [0, 0]];
tmp=[0,0];
my_array.insertAt("mamma mia!",tmp);
trace(my_array+"  GET : "+my_array.get(tmp));
*/


// ===========================================

