# Write your code here
import random
import string

wordlist = ['python', 'java', 'kotlin', 'javascript']
print("H A N G M A N")
action = ''
while action.lower() != 'exit':
    print('Type "play" to play the game, "exit" to quit: ', end="")
    action = input()
    if action.lower() == 'play':
        word_index = random.randint(0, len(wordlist) - 1)
        # word_index = 0
        word = wordlist[word_index]
        letters = set(word)
        guessed_letters = set()
        MAX_GUESSES = 8
        guesses = 0

        masked_word = '-' * len(word)
        masked_list = list(masked_word)
        print(f'\n{masked_word}')
        while masked_word != word and guesses < MAX_GUESSES:
            # print("-" * 30)
            # print(f"DEBUG - guessed = {guessed}")
            # print(f"DEBUG - guesses = {guesses}")
            # print(f"DEBUG - guessed_letters = {guessed_letters}")
            # print(f"DEBUG - letters = {letters}")
            # print("-" * 30)
            print("Input a letter: ", end="")
            guess = input()
            if len(guess) != 1:
                print("You should input a single letter")
            elif guess not in string.ascii_lowercase:
                print("Please enter a lowercase English letter")
            elif guess in guessed_letters:
                print("You've already guessed this letter")
            elif guess not in letters:
                print("That letter doesn't appear in the word")
                guesses = guesses + 1
            guessed_letters.add(guess)
            if guesses < MAX_GUESSES:
                for i in range(len(word)):
                    if word[i] in guessed_letters:
                        masked_list[i] = word[i]
                masked_word = ''.join(masked_list)
                print(f'\n{masked_word}')

        if masked_word == word:
            print('You guessed the word!')
            print('You survived!')
        else:
            print('You lost!')
        print()
