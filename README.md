# FRC-Elo-App
An android app implementation and improvement of my FRC-Elo repo

## Background Info
Elo ratings are a commonly used measure of relative performance in competitive games like tennis, chess, and football.

The idea behind Elo ratings in a few words is this: you move up and down based on who you win and lose to. If you win against a really good team you move up more than if you beat a really bad team.

I got the idea to create this from watching [this video.](https://youtu.be/AsYfbmp0To0, "Singingbanana")

More info on Elo ratings in general can be found [here.](https://en.wikipedia.org/wiki/Elo_rating_system, "Wikipedia")

## Implementation with Alliances
All the normal implementations of the Elo rating system have just single players against single players, or teams against teams, without having teams that are randomly assigned to alliances throughout a tournament (which makes sense, where else is that done besides FRC?)

The solution I came up with is just to find the average Elo rating of each alliance, then when using the update function to add update delta to each team individually. An interesting side effect of this implementation is that the theoretical (but would never happen) upper limit of Elo ratings for an FRC team is 4200, rather than 1400.

## How to use the app
While I currently haven't uploaded an apk, I will soon.
Note: the app is designed for and optimized for a Google Pixel 2, seeing as that is what I have. If you use it on your device and it turns out looking weird, open up an issue and I'll see what I can do.
### Required specs:
Android 6.0.0 or higher

If you don't want to just run the apk, skip down to Installing from Android Studio.
### Installing from apk
1. Download the apk
2. Find it in downloads
3. Click install
4. Enjoy (or see below for troubleshooting)
5. If you want to have pre-loaded data, jump down to Get the Data
#### Troubleshooting
1. If it says "Do you want to allow apps from this source" be sure to allow it
2. If something else happens, please Google the issue, and if that doesn't help, open up an issue on this repo
### Installing from Android Studio
I am assuming you have some basic knowledge of how to use a computer (like how to unzip a file)
1. Download the repository (either .zip or to Github desktop)
2. Open the project in Android studio (if you have android studio, otherwise download it)
3. While waiting for about 20 minutes for gradle to sync, make sure you have developer options enabled on your device, and have USB debugging on
4. Connect your device to your computer
5. Run the app with the target device as the device you have plugged in
6. Voila
#### Troubleshooting
1. You might need to run the ADB troubleshooter
2. Try plugging the USB cable into different ports on your computer (mine are finicky)
3. Make sure that your device is running android 6.0.0 or later (if not it won't work)
### Get the Data
1. Open the app
2. Hit Load from File
3. That just set up a folder in your home directory called FRC-ELO and added elo.txt and upsets.txt to it
4. Go into the folder and delete elo.txt or upsets.txt or both
5. Download elo.txt/upsets.txt and move it to FRC-ELO
6. Go back to the app and try to load from file
7. Hit Display Ratings and make sure the ratings are actually there
