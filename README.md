# optimization-transportation-problem
In this project we try to solve classical optimization tasks and realize famous optimization algorithms. You can test our code in Live on replit platform: https://replit.com/@AndrewLevada2/optimization-transportation-problem

## Test №1
Vector of coefficients of supply S:
- 160 140 170

Matrix of coefficients of cost C:
- 7 8 1 2
- 4 5 9 8
- 9 2 3 6

Vector of coefficients of demand D:
- 120 50 190 110 

Output:

|        | Cost | Per  | Unit | Distri | buted |        |
|--------|------|------|------|--------|-------|--------|
|        |      | Dest | inat | ion    |       |        |
|        |      | 1    | 2    | 3      | 4     | Supply |
|        | 1    | 7    | 8    | 1      | 2     | 160    |
| Source | 2    | 4    | 5    | 9      | 8     | 140    |
|        | 3    | 9    | 2    | 3      | 6     | 170    |
| Demand |      | 120  | 50   | 190    | 110   |        |


Initial basic feasible solution using North-West Corner method:
x = [120 40 0 0 0 10 130 0 0 0 60 110]

Initial basic feasible solution using Vogel’s Approximation method:
x = [0 0 50 110 120 20 0 0 0 30 140 0]

Initial basic feasible solution using Russel’s Approximation method: 
x = [0 0 160 0 120 0 0 20 0 50 30 90]


## Test №2
Vector of coefficients of supply S:
- 300 400 500

Matrix of coefficients of cost C:
- 3 1 7 4
- 2 6 5 9
- 8 3 3 2

Vector of coefficients of demand D:
- 250 350 400 200

Output:

|        | Cost | Per  | Unit | Distri | buted |        |
|--------|------|------|------|--------|-------|--------|
|        |      | Dest | inat | ion    |       |        |
|        |      | 1    | 2    | 3      | 4     | Supply |
|        | 1    | 3    | 1    | 7      | 4     | 300    |
| Source | 2    | 2    | 6    | 5      | 9     | 400    |
|        | 3    | 8    | 3    | 3      | 2     | 500    |
| Demand |      | 250  | 350  | 400    | 200   |        |


Initial basic feasible solution using North-West Corner method:
x = [250 50 0 0 0 300 100 0 0 0 300 200]

Initial basic feasible solution using Vogel’s Approximation method:
x = [0 300 0 0 250 0 150 0 0 50 250 200]

Initial basic feasible solution using Russel’s Approximation method: 
x = [0 300 0 0 250 50 100 0 0 0 300 200]


## Test №3
Vector of coefficients of supply S:
- 100 230 170

Matrix of coefficients of cost C:
- 2 1 4 5
- 8 2 4 7
- 3 3 6 1

Vector of coefficients of demand D:
- 80 120 100 150

Output:
The problem is not balanced!
