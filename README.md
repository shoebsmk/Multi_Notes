# Multi_Notes
This app allows the creation and maintenance of personal notes. Any number of notes are allowed (including no notes at all). Notes are made up of a title, a note text, and a last-update time.

Android Notes app allows the creation and maintenance of personal notes. Any number of notes are allowed (including no notes at all). Notes are made up of a title, a note text, and a last-update time. This app uses RecyclerView, Multi-Activity, JSON file, and Options-Menus. This app has been built under the guidance of Prof. Christopher Jones as a part of CSC 472 coursework.

Notes is saved to (and loaded from) the internal file system in JSON format. If no file is found upon loading, the application starts with no existing notes and no errors. (A new JSON file would then be created when new notes are saved).

JSON file loading happens in the onCreate(). Saving happens whenever a new note is added or a note is deleted.

A Note class (with title, note text, and last save date) is created to represent each individual note in the application.

The application is made up of 3 activities. These are described below:

Main Activity : Notes are displayed in a list, in time order (latest-update-first, oldest update- last). The note list is implemented using the RecyclerView. The Main Activity allows the user to create a new note via an Add options menu item. It also has an Info options-menu item.
Edit Activity : The Edit Activity contains editable fields for the note title and note text. The last-save time is NOT displayed here and is never user-editable – it is automatically generated and saved when the note is saved.
About Activity : The About Activity contains a full-screen image background (designed by me using Canva). Over the background image, key information on the application is displayed. This information includes the application title, a copyright date and my name, and the version number (1.0). There is no functionality present on this activity. The only action a user takes is press the Back arrow to exit the activity.

Screenshots:

![Main Activity](https://user-images.githubusercontent.com/42929978/213860231-fb06372d-de8b-4ce9-a8a6-bb6afe481bcf.jpeg)
![Delete prompt](https://user-images.githubusercontent.com/42929978/213860232-d4d035d7-6110-4e24-b911-cc49958cc457.jpeg)
![Undo](https://user-images.githubusercontent.com/42929978/213860234-bbb6b923-0f2f-49ba-bfbd-4d2fd8320a0b.jpeg)
![Note not Saved Prompt](https://user-images.githubusercontent.com/42929978/213860235-ac9ebf18-5749-4f71-8579-eb610c7f22a4.jpeg)
