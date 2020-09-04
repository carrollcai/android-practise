
function callJs(str) {
    document.getElementById("root").innerHTML = str;
}

document.getElementById("root").onclick = function() {
     if (window.jsBridge) {
         window.jsBridge.receiveJs("来自JS");
     }

 }