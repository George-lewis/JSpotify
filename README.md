# JSpotify
A simple wrapper for the Spotify local api.
This library allows easy integration with the Spotify desktop client.
It's good for things like getting the current song or requesting a song be played.

# TODO

- Write functions to match all of the supported api functions
- Write docs, examples, and flesh out the readme
- Consider creating data classes to represent api results

# How to use it

Before you can use JSpotify it needs to be initialized (a few tokens must be obtained and the local api address resolved)

```
try {
  JSpotify.initialize();
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

# Installation

TODO

## JAR

TODO

## Maven

TODO

# How it works

TODO
