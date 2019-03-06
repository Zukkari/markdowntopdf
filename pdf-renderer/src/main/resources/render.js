"use strict";

console.log("[PHANTOM] Initializing renderer...");
var page = require('webpage').create();
var system = require('system');
var out = system.args[1];
console.log("[PHANTOM] Output: " + out);
var address = system.args[2];
console.log("[PHANTOM] Rendering with file: " + address);

page.paperSize = {
    format: "A4",
    orientation: "portrait",
    margin: '1cm'
};


var zoom = 0.75;

page.open(address, function (status) {
   if (status !== 'success') {
       console.log('[PHANTOM] Unable to open address =>' + address)
   } else {
       page.evaluate(function(zoom) {
           document.getElementsByTagName('html')[0].style.zoom=zoom;
       }, zoom);

       window.setTimeout(function () {
           console.log("[PHANTOM] Rendering pdf with id => " + out);
           page.render(out + ".pdf");
           console.log("[PHANTOM] Finished rendering pdf => " + out + ".pdf");
           phantom.exit();
       }, 200);
   }
});