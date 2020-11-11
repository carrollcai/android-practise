window.messageObjects = {};

window.jsBridge._handleMessageFromNative = function(messageJSON) {
 if (!messageJSON) throw Error("messageJSON must be required");
 document.getElementById("test").innerHTML = messageJSON.functionName;

 if (messageJSON && window.messageObjects[messageJSON.functionName]) {
     window.messageObjects[messageJSON.functionName](messageJSON.data);
 }
}