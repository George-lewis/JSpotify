# JSpotify
A simple wrapper for the Spotify local api.
This library allows easy integration with the Spotify desktop client.
It's good for things like getting the current song or requesting a song be played.

# TODO

- :heavy_check_mark:? Write functions to match all of the supported api functions
- Write docs, examples, and flesh out the readme
- :heavy_check_mark: Consider creating data classes to represent api results
- :heavy_check_mark: Find a faster way to acquire the port (threading?)
- :heavy_check_mark: Process control features
- Create a release

# How to use it

Before you can use JSpotify it needs to be initialized (a few tokens must be obtained and the local api address resolved)

```
try {
  JSpotify.initialize(true); // Will attempt to start the Spotify client if it is not running (Only supported on Windows)
  JSpotify.initialize(false); // Will not attempt to start the Spotify client if it is not running
} catch (SpotifyException e) {
  // JSpotify failed to initialize
}
```

After this you can make api calls

Playing a song:

```
try {
  JSpotify.play("spotify:track:1ZqHjApl3pfzwjweTfMi0g"); // The Spotify URI for Coldplay's Violet Hill
} catch (SpotifyException e) {
  // Jspotify failed to make the api call
}
```

Getting the various client info:

```
try {
  Status status = JSpotify.getStatus();
  status.getClientVersion();
  status.getVolume();
  status.getTrack().getArtist().getName();
} catch (SpotifyException e) {
  // Jspotify failed to make the api call
}
```

Stopping the client:

```
try {
  JSpotify.stopSpotify();
  // Should be supported on all OS' (untested) check if the client can be closed with JSpotify.canStopSpotify()
} catch (SpotifyException e) {
  // JSpotify failed to close the client
}
```

# Installation

TODO

## JAR

TODO

## Maven

TODO

# How it works

The Spotify client has its own webserver that the web client uses to communicate information with to the desktop client. Third party applications can connect to this server and control the desktop client remotely.
