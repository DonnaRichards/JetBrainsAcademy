def print_board():
    header = '---------'
    print(header)
    for row in board:
        print('| ', end='')
        for square in row:
            print(f"{square} ", end='')
        print('|')
    print(header)


def three_in_row(ch):
    compare_str = ch * BOARD_SIZE

    for row in range(BOARD_SIZE):
        row_str = ''
        for col in range(BOARD_SIZE):
            row_str += board[row][col]
        if row_str == compare_str:
            return True

    for col in range(BOARD_SIZE):
        col_str = ''
        for row in range(BOARD_SIZE):
            col_str += board[row][col]
        if col_str == compare_str:
            return True

    diag1 = ''.join([board[0][0], board[1][1], board[2][2]])
    if diag1 == compare_str:
        return True
    diag2 = ''.join([board[0][2], board[1][1], board[2][0]])
    if diag2 == compare_str:
        return True
    return False


def check_state():
    count_X = len([x for row in board for x in row if x == 'X'])
    count_O = len([x for row in board for x in row if x == 'O'])
    count_blank = len([x for row in board for x in row if x == '_'])
    state = ''
    # there are a lot more X's than O's or vice versa (the difference should be 1 or 0;
    # if the difference is 2 or more, then the game state is impossible).
    # if more X than O or v.v (diff 2 or more), game state is impossible
    if abs(count_X - count_O) > 1:
        state = 'Impossible'
    # Impossible when the grid has three X’s in a row as well as three O’s in a row,
    elif three_in_row('X') and three_in_row('O'):
        state = 'Impossible'
    # X wins when the grid has three X’s in a row.
    elif three_in_row('X'):
        state = 'X wins'
    # O wins when the grid has three O’s in a row.
    elif three_in_row('O'):
        state = 'O wins'
    # Game not finished when neither side has three in a row but the grid still has empty cells.
    elif count_blank > 0:
        state = 'Game not finished'
    # Draw when no side has a three in a row and the grid has no empty cells.
    else:
        state = 'Draw'
    return state


BOARD_SIZE = 3
board = [['_' for _ in range(BOARD_SIZE)] for _ in range(BOARD_SIZE)]
print_board()
game_state = check_state()
turn = 'X'

while game_state == 'Game not finished':
    valid_coods = False
    while not valid_coods:
        try:
            x_str, y_str = input("Enter the coordinates: ").split()
            x = int(x_str)
            y = int(y_str)
            if x in range(1, BOARD_SIZE + 1) and y in range(1, BOARD_SIZE + 1):
                if board[x - 1][y - 1] in 'XO':
                    print('This cell is occupied! Choose another one!')
                else:
                    valid_coods = True
                    board[x - 1][y - 1] = turn
            else:
                print(f'Coordinates should be from 1 to {BOARD_SIZE}!')
        except ValueError:
            print('You should enter numbers!')
    print_board()
    game_state = check_state()
    turn = 'X' if turn == 'O' else 'O'
print(game_state)