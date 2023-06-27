# write your code here
import random
import re

INITIAL_BALANCE = 1000
BET_AMOUNT = 1
QUIT_GAME = 'enough'
MIN_LENGTH_INITIAL = 100
MIN_LENGTH_RANDOM = 4


def get_user_input_initial_str() -> str:
    string = ''
    while len(string) < MIN_LENGTH_INITIAL:
        print("Print a random string containing 0 or 1:")
        string += re.sub(r'[^01]', '', input())
        if len(string) < MIN_LENGTH_INITIAL:
            print(f'Current data length is {len(string)}, {MIN_LENGTH_INITIAL - len(string)} symbols left')
    return string


def string_count(full_string: str, triad: str, char: str) -> int:
    test_string = triad + char
    count = 0
    for j in range(len(full_string)):
        if full_string[j:len(test_string) + j] == test_string:
            count += 1
    return count


def initialize_triad_dict() -> dict:
    triad_dict = {}
    for i in range(8):
        triad = str(bin(i)).lstrip('0b').zfill(3)
        triad_dict[triad] = (0, 0)
    return triad_dict


def analyze_triads(string: str, triad_dict: dict) -> dict:
    for triad, counts in triad_dict.items():
        triad_dict[triad] = (counts[0] + string_count(string, triad, '0'), counts[1] + string_count(string, triad, '1'))
    return triad_dict


def get_user_input_random_str() -> str:
    string = ''
    while len(string) < MIN_LENGTH_RANDOM:
        print('Print a random string containing 0 or 1:')
        user_string = input()
        if user_string.lower() == QUIT_GAME:
            string = user_string
        else:
            string = re.sub(r'[^01]', '', user_string)
    return string


def generate_predicted_string(user_entered_str: str, prediction_dict: dict) -> str:
    predicted_str = ''
    for i in range(len(user_entered_str) - 3):
        triad_key = user_entered_str[i:i+3]
        if prediction_dict[triad_key][0] > prediction_dict[triad_key][1]:
            predicted_str += '0'
        elif prediction_dict[triad_key][0] < prediction_dict[triad_key][1]:
            predicted_str += '1'
        else:
            predicted_str += str(random.randint(0, 1))
    return predicted_str


def calculate_accuracy(user_string: str, prediction_string: str) -> int:
    correct = 0
    for i in range(len(prediction_string)):
        if user_string[i + 3] == prediction_string[i]:
            correct += 1
    print('Calculate Accuracy has run')
    print('user string: ' + user_string)
    print('prediction string: ' + prediction_string)
    print('correct count: ' + str(correct))
    return correct


def update_balance(correct: int, total: int, balance: int) -> int:
    # add $1 (or other game bet amount) to balance for each symbol computer got wrong
    balance += (total - correct) * BET_AMOUNT
    # subtract $1 (or other game bet amount) to balance for each symbol computer got correct
    balance -= (correct * BET_AMOUNT)
    return balance


def print_status(correct: int, total: int, balance: int) -> None:
    percent = (correct * 100) / total
    print(f"Computer guessed {correct} out of {total} right ({percent:.2f} %)")
    print(f"Your balance is now ${balance}")


print("Please provide AI some data to learn...")
print(f"The current data length is 0, {MIN_LENGTH_INITIAL} symbols left")

string = get_user_input_initial_str()
print('Final data string:')
print(string)
probability_dict = analyze_triads(string, initialize_triad_dict())

print(f"""You have ${INITIAL_BALANCE}. Every time the system successfully predicts your next press, you lose ${BET_AMOUNT}.
Otherwise, you earn ${BET_AMOUNT}. Print "enough" to leave the game. """)

balance = INITIAL_BALANCE
random_string = ''
while balance > 0 and random_string.lower() != QUIT_GAME:
    random_string = get_user_input_random_str()
    if random_string != QUIT_GAME:
        print('predictions:')
        predicted_string = generate_predicted_string(random_string, probability_dict)
        print(predicted_string)
        num_correct = calculate_accuracy(random_string, predicted_string)
        balance = update_balance(num_correct, len(predicted_string), balance)
        print_status(num_correct, len(predicted_string), balance)
        # analyse the latest string and add to triad counts to improve computer predictions
        probability_dict = analyze_triads(random_string, probability_dict)
print('Game over!')
