# Demo Application

Experience Hawcx Authentication in action through our demo application. This demo showcases the key features and security capabilities of Hawcx Authentication in a real-world scenario.

<div class="grid-container">
  <!-- <div class="grid-item" onclick="showVideo()">
    <i style="font-size: 3rem; margin-top: 25px; opacity: 0.5" class="fa fa-play-circle"></i>
    <h2>Watch the Video</h2>
    <p style="margin-top: -15px">See Hawcx Authentication in action</p>
  </div> -->

  <div class="grid-item" onclick="window.open('https://github.com/hawcx/authenticator', '_blank')">
    <i style="font-size: 3rem; margin-top: 25px; opacity: 0.5" class="fa fa-code"></i>
    <h2>Get the Code</h2>
    <p style="margin-top: -15px">Explore the demo app source</p>
  </div>

  <div class="grid-item" onclick="window.open('https://github.com/hawcx/authenticator/releases/latest/download/hawcx-authenticator.apk', '_blank')">

    <i style="font-size: 3rem; margin-top: 25px; opacity: 0.5" class="fa fa-download"></i>
    <h2>Download the App</h2>
    <p style="margin-top: -15px">Try the demo on your device</p>
  </div>
</div>

<div id="videoModal" class="modal">
  <div class="modal-content">
    <span class="close">&times;</span>
    <iframe width="560" height="315" src="https://www.youtube.com/embed/ArcI4A5nvBo?si=qSdRqpxS4p2NkGui" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>
  </div>
</div>

<script>
function showVideo() {
  var modal = document.getElementById("videoModal");
  modal.style.display = "block";
}

var span = document.getElementsByClassName("close")[0];
span.onclick = function() {
  var modal = document.getElementById("videoModal");
  modal.style.display = "none";
}

window.onclick = function(event) {
  var modal = document.getElementById("videoModal");
  if (event.target == modal) {
    modal.style.display = "none";
  }
}
</script>

<style>
.grid-container {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  padding: 20px;
}

.grid-item {
  background-color: #f1f1f1;
  border-radius: 5px;
  padding: 20px;
  text-align: center;
  cursor: pointer;
  transition: background-color 0.3s;
}

.grid-item:hover {
  background-color: #ddd;
}

.grid-item i {
  font-size: 48px;
  margin-bottom: 10px;
}

.modal {
  display: none;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0,0,0,0.4);
}

.modal-content {
  background-color: #fefefe;
  margin: 15% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 700px;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
  cursor: pointer;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}
</style>

## Features Demonstrated

Our demo application showcases the following key features of Hawcx Authentication:

1. Secure User Authentication
2. Biometric Login
3. Encrypted Data Storage
4. Secure API Communications
5. Tamper Detection

Explore the demo to see how Hawcx Authentication can enhance the security of your Android application.

