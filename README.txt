LazyBST Project

This project was to create a Binary Search Tree for generic items that implemented lazy deletion. It was tested with a
simulated grocery store inventory. The main file is LazySearchTree.java



Brief description of submitted files:

src/cs1c/MillionSongDataSubset.java
    - One object of class MillionSongDataSubset opens, reads, and parses a JSON file containing a list of songs and their information
      It stores the information in a array.

src/cs1c/SongEntry.java
    - One object of class SongEntry stores a simplified version of the information from MillionSongDataSubset.

src/cs1c/TimeConverter.java
    - One object of class TimeConverter converts a duration of time in seconds and converts to a string representation with
      hour, min, sec.

src/lazyTrees/Item
    - One object of class Item represents an Item in a store (used in SuperMarket currently)
    - Item has 2 class members. A String for the name of the instance (item) and a count for the amount of items in inventory.

src/lazyTrees/LazySearchTree
    - This is the largest class in the project and was the purpose of the project. An object of this class is a Binary
      Search Tree (BST).It was originally copied from the class FHsearch_tree.java in our course examples.
      It was then modified for this project (from project 5) to include 'collectGarbage' which hard removes any soft deleted nodes.
    - It is currently used by SuperMarket class
    - A Generic class
    - Extends comparable
    - Implements clonable
    - It has 3 class members(variables): int mSize to hold the BST soft size (does not show lazy
      deleted items), int mSizeHard to hold the BST hard size (shows all items in the tree), and LazySTNode mRoot which
      is a reference to the root(top) of the tree.
    - It contains a inner helper class LazySTNode that wraps an object to be stored in the BST.


src/lazyTrees/PrintObject
    - This is a 'function class' (terminology could be wrong)
    - It is a generic class that takes an object and has a method to print the object.
    - Implements Traverser


src/lazyTrees/SuperMarket
    - This class contains the 'main()'. It was given to us by professor Bita Mazloom.
    - It simulates a supermarket. It reads a file with keywords 'add' and 'buy' which denotes adding or removing
      an item from the stores inventory.
    - It creates a LazySearchTree of items objects to manage the stores invetory.

src/lazyTrees/Traverser
    - Interface that currently has only 1 method, 'visit'

src/lazyTrees/FoothillTunesStore
    - Not Written Yet. For Extra Credit

resources/inventory_invalid_removal.txt
    - A text file containing a list of command words and inventory items that is opened and read by the main().
    - It is written to test exception handling of the program by trying to remove an item not in inventory.

resources/inventory_log.txt
    - A text file containing a list of command words and inventory items that is opened and read by the main().
    - Used to test SuperMarket class

resources/inventory_short.txt
    - A shortened version of 'inventory_log.txt'
    - It allows for an easier test while writing code for LazySearchTree

resources/music_genre_subset.json
    - JSON file containing over 5,000 songs and their information. Used in creation of SongEntry Objects
    - For extra credit, not currently used

resources/RUN.txt
    - A copy of the console output from this program. There are multiple runs for different conditions and resource files
      used.

resources/SuperMarketFullTest
   - A text file containing a list of command words and inventory items that is opened and read by the main().
   - It is written to test for error handling of SuperMarket and LazySearchTree classes
   - List of errors tested for found in SuperMarketFullTestDetails

resources/SuperMarketFullTestDetails
   - Explains what lines in SuperMarketFullTest are designed to test

