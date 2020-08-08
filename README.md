# continuity
Seamlessly share links from your smartphone to your computers  

## Background
I often find myself browsing hackernews or some other sites on my phone, I'm starting to read an article or watch a video and think that it would be more comfortable to continue on my computer with a bigger screen. Sure I can send the link to myself via one of the available messengers or via mail but I think there should be a more seamless solution like just share the url with an app and the app will send a request to the computer you want to continue reading or watching on. Later on the app and the service could maybe do more like implement one of the existing cast protocols.

## Projects

### Continuity
Continuity is the backend service. It runs as a daemon on your desktop/laptop/raspberry pi whatever you want. It tries to open the medium with the default application on that device e.g. an url will be open with the default browser, an image with the default image viewer etc.

### ContinuityApp
ContinuityApp is an Android app. Currently you can share urls with the app and it will send it to the backend running on the selected device.
## WARNING
**This is a very early development version basically just a proof of concept right now**

