Stage 4/4: "Generate randomness" game
Description
In the final stage, we will gather all the components to construct a game where you will try to beat the system. Initially, the player has a virtual balance of $1000. Every time the computer guesses a symbol correctly, the player loses one dollar. Every time the system is wrong, the player gets one dollar.

Objectives
In this final stage, your program should:

Print two new lines at the start:
Please provide AI some data to learn...
The current data length is 0, 100 symbols left
Get and preprocess user input just like in stage 1;
Learn the user patterns by collecting triad statistics like in stage 2;
Prompt the user to play a game or type enough to exit the game. Use the following phrases:
You have $1000. Every time the system successfully predicts your next press, you lose $1.
Otherwise, you earn $1. Print "enough" to leave the game. Let's go!
Ask the user to enter random strings. Each random string must be preprocessed to have only 0 and 1 symbols. After preprocessing the string length must be 4, at minimum. If this is not the case — ask to enter a whole new string again. Use this text: Print a random string containing 0 or 1:;
Now we are playing a game. And you need to ask to enter a random string, not a test string as before. Please pay attention to this.
Launch the prediction algorithm and calculate the number of correctly guessed symbols, like in stage 3. After each iteration, you should calculate the remaining balance and show it with the message Your balance is now $X, where X is the player's virtual balance. For example, if the program guessed 4 out of 7, then the balance will decrease by 1 (lost: 4, won: 3); it will be $999;
It would be great if you keep updating the system by allowing it to learn from new data as well. However, this update should happen only when the prediction and the verification stages are done — be honest with the user;
Finish the game with the words Game over!
Before implementing the solution, examine the example carefully. The output of your program should provide instructions and feedback in the same format.

Example
The greater-than symbol followed by a space (> ) represents the user input. Note that it's not part of the input.

Please provide AI some data to learn...
The current data length is 0, 100 symbols left
Print a random string containing 0 or 1:

> 010100100101010101000010001010101010100100100101001
The current data length is 51, 49 symbols left
Print a random string containing 0 or 1:

> 011010001011111100101010100011001010101010010001001010010011

Final data string:
010100100101010101000010001010101010100100100101001011010001011111100101010100011001010101010010001001010010011

You have $1000. Every time the system successfully predicts your next press, you lose $1.
Otherwise, you earn $1. Print "enough" to leave the game. Let's go!

Print a random string containing 0 or 1:

> 0111001001
predictions:
0101011

Computer guessed 4 out of 7 symbols right (57.14 %)
Your balance is now $999

Print a random string containing 0 or 1:

> some wrong input

Print a random string containing 0 or 1:

> 0101001001
predictions:
1011011

Computer guessed 5 out of 7 symbols right (71.43 %)
Your balance is now $996

Print a random string containing 0 or 1:

> enough
Game over!