var log = typeof console != 'undefined'
				? function (x) { console.log(x) } 
				: function () {},
		dir = typeof console != 'undefined' 
				? function (x) { console.dir(x) } 
				: function () {}

 pktbConfirm = function(text, ok, cancel, title) {
  var dialog = $('<div>', { text: text })
	  				 .addClass('pktb')
	  				 .appendTo(document.body);
      
  dialog.dialog({
   	title: title,
    resizable: false,
    modal: true,
    buttons: {
    	'ОК': function() {
       	dialog.remove();
       	if (ok) ok();
       },
       'Отмена': function() {
         	dialog.remove();
             if (cancel) cancel();
    	}
  	}
	}).css('min-height', 0);
},
pktbAlert = function(text, title) {
	var dialog = $('<div>', { text: text })
						 .addClass('pktb')
						 .appendTo(document.body);
	dialog.dialog({
  	title: title,
    resizable: false,
		modal: true,
    buttons: {
    	'ОК': function() { 
    		dialog.remove(); 
    	}
    }
	}).css('min-height', 0);
}