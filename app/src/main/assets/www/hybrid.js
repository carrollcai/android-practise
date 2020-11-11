
function callJs(str) {
    document.getElementById("root").innerHTML = str;
}

function callback(str) {
    document.getElementById("root").innerHTML = str;
}

document.getElementById("root").onclick = function() {
     if (window.jsBridge) {
        const callbackName = "callback";
        window.messageObjects[callbackName] = callback;

         window.jsBridge.receiveJsAndEvaluateCallback("来自JS", callbackName);
     }
 }