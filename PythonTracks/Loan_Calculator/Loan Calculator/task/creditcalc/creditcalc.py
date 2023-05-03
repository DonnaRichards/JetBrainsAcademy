import math
import argparse
import sys


# def get_principal():
#     while True:
#         print('Enter the loan principal:')
#         principal_str = input()
#         try:
#             principal = float(principal_str)
#             if principal > 0:
#                 return principal
#             else:
#                 print('Error - negative or zero loan principal, please retry')
#         except ValueError:
#             print('Error - Non numeric loan principal value entered, please retry')
#
#
# def get_periods():
#     while True:
#         print('Enter the number of periods:')
#         period_str = input()
#         try:
#             period = float(period_str)
#             if period > 0:
#                 return period
#             else:
#                 print('Error - negative or zero loan period, please retry')
#         except ValueError:
#             print('Error - Non numeric loan period value entered, please retry')
#
#
# def get_interest():
#     while True:
#         print('Enter the loan interest:')
#         interest_str = input()
#         try:
#             interest = float(interest_str)
#             if interest > 0:
#                 return interest / 100
#             else:
#                 print('Error - negative loan interest, please retry')
#         except ValueError:
#             print('Error - Non numeric loan interest value entered, please retry')
#
#
# def get_annuity():
#     while True:
#         print('Enter the monthly payment:')
#         annuity_str = input()
#         try:
#             annuity = float(annuity_str)
#             if annuity > 0:
#                 return annuity
#             else:
#                 print('Error - negative or zero loan annuity, please retry')
#         except ValueError:
#             print('Error - Non numeric loan annuity value entered, please retry')


def calc_nominal_interest(interest):
    return interest / 1200


def calc_months():
    principal = args.principal
    annuity = args.payment
    interest = args.interest
    nominal_rate = calc_nominal_interest(interest)
    base = 1 + nominal_rate
    x = annuity / (annuity - nominal_rate * principal)
    n = math.log(x, base)
    full_months = math.ceil(n)
    overpayment = full_months * annuity - principal
    return full_months, overpayment


def calc_annuity():
    principal = args.principal
    n = args.periods
    interest = args.interest
    nominal_rate = calc_nominal_interest(interest)
    annuity = math.ceil(principal * ((nominal_rate * (1+nominal_rate)**n) / ((1+nominal_rate)**n - 1)))
    overpayment = (annuity * n) - principal
    return annuity, overpayment


def calc_principal():
    annuity = args.payment
    n = args.periods
    interest = args.interest
    nominal_rate = calc_nominal_interest(interest)
    principal = math.floor(annuity / ((nominal_rate * (1+nominal_rate)**n) / ((1+nominal_rate)**n - 1)))
    overpayment = (annuity * n) - principal
    return principal, overpayment


def format_months(full_months):
    years = full_months // 12
    months = full_months % 12
    year_str = 'years' if years > 1 else 'year'
    month_str = 'months' if months > 1 else 'month'
    full_year_str = f'{years} {year_str}' if years > 0 else ''
    full_month_str = f'{months} {month_str}' if months > 0 else ''
    and_str = ' and ' if years > 0 and months > 0 else ''
    print(f'It will take {full_year_str}{and_str}{full_month_str} to repay this loan!')


def process_annuity_calculation():
    overpayment = 0
    if args.periods is None:
        months, overpayment = calc_months()
        format_months(months)
    elif args.payment is None:
        payment, overpayment = calc_annuity()
        print(f'Your monthly payment = {payment}!')
    elif args.principal is None:
        principal, overpayment = calc_principal()
        print(f'Your loan principal = {principal:.0f}!')
    else:
        print('Invalid arguments for annuity calculation')
        return
    print(f'Overpayment = {overpayment:.0f}')


def process_diff_calculation():
    p = args.principal
    n = args.periods
    interest = args.interest
    i = calc_nominal_interest(interest)
    x = p / n
    total = 0
    for m in range(1, n+1):
        d = x + (i * (p - (p * (m - 1) / n)))
        payment = math.ceil(d)
        total += payment
        print(f'Month {m}: payment is {payment}')
    overpayment = total - p
    print(f'\nOverpayment = {overpayment:.0f}')

parser = argparse.ArgumentParser(description="Loan Calculator")
parser.add_argument("-t", "--type",
                    choices=['annuity', 'diff'],
                    help="Loan type - REQUIRED - 'annuity' or 'diff' ")
parser.add_argument("-l", "--principal",
                    type=float,
                    help="the loan principal")
parser.add_argument("-p", "--payment",
                    type=int,
                    help="monthly payment amount for loan")
parser.add_argument("-m", "--periods",
                    type=int,
                    help="number of months needed to repay loan")
parser.add_argument("-i", "--interest",
                    type=float,
                    help="loan interest rate (just enter a number, no % sign")
args = parser.parse_args()
if len(sys.argv) < 4:
    print("Incorrect parameters.")
    sys.exit()

if not args.type:
    print("Incorrect parameters.")
    sys.exit()

if not args.interest:
    print("Incorrect parameters.")
    sys.exit()

if args.type == 'annuity':
    process_annuity_calculation()
elif args.type == 'diff':
    if args.principal and args.periods and args.interest and not args.payment:
        process_diff_calculation()
    else:
        print("Invalid parameters for diff type loan - principal, periods, interest required; payment not allowed")
else:
    print('--type parameter is required (annuity or diff)')
