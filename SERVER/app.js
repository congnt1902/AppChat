var express = require("express");
var app = express();
app.use(express.static(__dirname + '/'));
var server = require("http").createServer(app);
var io = require("socket.io").listen(server);
var fs = require("fs");
server.listen(process.env.PORT || 3000);
var mangUserName=[];

io.sockets.on('connection', function (socket) {
	
  console.log("Co nguoi connect");
    
  socket.on('clent-gui-username', function (data) {
	  var ketqua=false;
	  if (mangUserName.indexOf(data)>-1){
		  ketqua=false;
	  }
	  else{
	  mangUserName.push(data);
	  socket.un=data;
	  ketqua=true;
	  io.sockets.emit('server-gui-username',{danhsach : mangUserName});
	  io.sockets.emit("new_user_online", {username:socket.un});
	   
	  }
	  socket.emit('ket-qua-dang-ky',{noidung : ketqua});
	   
});
  socket.on('client-gui-tin-chat', function (content) {
	  
	  io.sockets.emit("new_user", {username:socket.un});
	   io.sockets.emit('server-gui-tin-chat',{message : socket.un+": "+ content});
});

  socket.on('disconnect', function () {
		if (socket.un != null) {
			let index = mangUserName.indexOf(socket.un);
			if (index > -1) {
 			 mangUserName.splice(index, 1);
			}
		console.log(socket.un +" disconnect");
		io.sockets.emit('user_disconnect', {username: socket.un});
		}			
	});
});
