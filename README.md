# Cryptogram User Manual

**Author**: Team 19

**Version**: 1.1

| Version | Time | Description |
| --------| -----| ------------|
| 1.1 | 07/15/2017 | revised for D4 |
| 1.0 | 07/07/2017 | initial version submitted for D3 |


### Introduction

A cryptogram is a puzzle where a sentence is encoded by substituting the actual letters of the sentence with different letters or symbols. The challenge of the puzzle is to 'decode' the sentence to reveal the original English sentence.

Cryptograms can be fun brain-teasers and mind-melters, or they can quickly leave you wanting to throw your pencil against the wall. Learning a few easy patterns and tricks, though, can help you crack the code and make them a whole lot more fun. Eager to work one out to the end? Get started by learning the basics, then learning patterns and thinking outside the box to get those blanks filled in.

### System Requirements

- **Operating System**: Android 4.4 and higher (Android Watch and Android TV are not supported)
- **CPU**: 1.2 GHz and higher
- **RAM**: 1.0 GB and larger
- **Internet access** through cellular or WLAN is available when necessary.

### Installation

** Install from source**

- Import the project into Android Studio.
- Conncet to an Android device. You can choose one of the following two options
	- Create and start an Android Virtual Device (AVD)
	- Connect to a Android phone via a USB cable. Enable developer options.
- Click "Run app" to install and run the app on the device you choose.

**Install using APK**

- Copy `SDPCryptogram.apk` from the `/APK` directory to an Andorid phone.
- Open the copied .apk file in the phone and following the instruction to install.

### For the administrator

#### Login

1. Start the application.
2. Select the "Administrator" radio button.
3. Click the "Login" button.
4. You would see the administrator mennu.

#### Add a new player

1. Click the "Add new players" button in the menu.
2. Input "Username", "First Name", "Last Name" for the new player. Username must be unique. All these three fields are limited to 32 characters.
3. Click "Save" button to create the new player.
4. Click "Cancel" to return to the administartor menu.

#### Add a new cryptogram

1. Click the "Add new cryptograms" button in the menu.
2. Input "Encoded Phrases", and "Solution Phrases" in the blanks for the new cryptogram. The new cryptogram cannot be duplicated with old ones. The encoded phrases should be encoded with a simple substitute cipher. The phrases are limited to 512 characters.
3. Click "Save" button to create the new cryptogram.
4. Click "Reset" button to clear input.
5. Click "Cancel" to return to the administartor menu.

### For players

#### Login

1. Start the application.
2. Select the "Player" radio button.
3. Input a valid username.
4. Click the "Login" button.
5. You would see the cryptogram list.

#### Solve a cryptogram

1. Click one cryptogram whose status is "in progress" or "new" from the cryptogram list.
2. Input a character below the encoed character you'd like to substitute.
3. Click "Submit" to check your answer if all characters are assigned.
4. Click "Reset" to clear all you input.
5. Click "Cancel" to return to the cryptogram list.

#### View a solved cryptogram

1. Click one cryptogram whose status is "won" from the cryptogram list.
2. Click "Cancel" to return to the cryptogram list.

#### View the player rating list

1. Click the menu button on the up-left corner in the view of the cryptogram list.
2. In the sidebar menu, click "Player ratings".
3. The player rating list will shown, sorted by the number of cyptograms solved.

#### Request new cryptograms

1. Each time a player is logged in, the cyptogram list will be updated automatically.
2. A player can click the "Request new cryptogram" button to update the list manually.


### TROUBLESHOOTING

Force quite the app if something wrong happens.

### TECHNICAL SUPPORT

Technical support E-mail: lfan42@gatech.edu
