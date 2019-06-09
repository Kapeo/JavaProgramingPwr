var style = function(text, style) {
  var how_start;
  var how_end;
  if(style==='bold'){
	  how_start = '<strong>';
	  how_end = '</strong>';
  }
  if(style==='italic'){
	  how_start = '<i>';
	  how_end = '</i>';
  }
  var ret = '<html>'+how_start+text+how_end+'</html>';
  return ret;
};