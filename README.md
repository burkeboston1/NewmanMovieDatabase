Newman Movie Database
=====================

An Android application that connects Newman Library's database of rentable DVDs with the [Open Movie Database API](http://www.omdbapi.com/). 

Created by Boston Burke and Tom Evans

## Table of Contents
 * [What's New](#what's-new)
 * [Background](#Background)
 * [Design](#Design)

What's New
-----------
Version 1.0 (pending)
  * Search: users can search for titles, actors, directors, etc. and get a list of matching movies. 
  * Movie Details: OMDB provides extra details (plot, poster, etc.) about movies that Newman doesn't store.

Background
-----------
In the summer of 2016, I had no access to the internet, so I started checking out movies from Newman Library at Virginia Tech where I am a student. 
There were two ways to look for movies:
  1. Use the library's Windows '95 computer to search the database online. 
  2. Flip through the hardcopy of the database, which was a giant white binder sitting on top of the stacks. This was quicker. 
  
So we created NMDB, which is designed to run on an Android tablet where that old binder used to be. 

Design
------------
The app loads an 8MB database dump into memory and indexes it by keyword. The user can then query the database using the search function. 

