This is the readme file for my CS 351 Project - Scrabble. 


Paper view
I’m thinking of using a [2x2] for both the rows and the columns. The arrays would be initialized with all their values set to null and the spaces for the letter and word multiplier will be initialized with a special character. The tiles already in play will be filled with their respective letter characters. 
 

The dictionary will be set up in a different file, maybe like a .txt file. The words in the text file will be separated by a space. The program will take in each letter/character as a char value and compare them with the letters in the tray and find the word with the maximum point. 
 

When searching for all the possible words the computer can play we must take into account:
The letters in the computer's tray.

The total number of empty squares on each column or row to check whether the word will fit or not. 

 

I would search for the best scoring play by trying out all the possible options within a loop and selecting the one that yields the highest score after the letter and the word multiplier. 




# Scrabble
