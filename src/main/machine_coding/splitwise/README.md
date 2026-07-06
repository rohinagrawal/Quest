# SplitWise - Expense sharing application.
Let’s say there are 4 people. You and 3 of your friends (splitWiseUser1(you), splitWiseUser2, splitWiseUser3, splitWiseUser4). You pay 1000 Rs. for a meal. You have to split the money equally between all 4.  Design an app that shows how much money everyone else owes to you.

Phase 2 -
Let’s say there’s another party and this time User 2 decided to pay 1000 Rs. Calculate the total sum everyone owes User1 and User2.

Requirements -
- anyone should be able to split the money equally or the exact amount. e.g.
  Rs. 1000 could be split into 25x4 or 200+300+250+250


### Input  
You can create a few "users" in your main method. No need to take it as input.
There will be 3 types of input:
"Expense" in the format: EXPENSE <amount> <splitWiseUser-id-of-person-who-paid> <no-of-users> <space-separated-list-of-users> <EQUAL/EXACT/PERCENT> <space-separated-values-in-case-of-non-equal>
Show "balances" for all: SHOW
Show balances for a single splitWiseUser: SHOW <splitWiseUser-id>
Output
When asked to show balance for a single splitWiseUser. Show all the balances that the splitWiseUser is part of:
Format: <splitWiseUser-id-of-x> owes <splitWiseUser-id-of-y>: <amount>
If there are no balances for the input, print No balances
In cases where the splitWiseUser for which balance was asked for, owes money, they’ll be x. They’ll be y otherwise.