evilhangman
===========

A game of hangman that is very hard to beat

The game has multiple difficulties with the easy and medium
being more or less what you'd expect from a game of hangman.
The hard difficulty though will be more or less impossible to
win as the computer will cheat. It will not commit to a single 
word until the end of the game. Instead it will keep track of a
list of words that the answer could possibly be and with each guess
cut the list down the to the largest possible list.

For example, if the word list is ECHO, HEAL, BEST, and LAZY and the 
player guesses the letter 'E', then there would be three word families:
E---, containing ECHO.
-E--, containing HEAL and BEST.
----, containing LAZY.

since the second family has the most options it will tell the user that there
is one E in the second place.

The idea for the project came from a Stanford assignment posted on 
http://nifty.stanford.edu/2011/schwarz-evil-hangman/

and the dictionary file used (though slightly edited) from
http://wordlist.sourceforge.net/12dicts-readme.html
