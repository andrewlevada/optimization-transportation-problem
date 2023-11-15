import structures.VectorFactory;
import structures.MatrixFactory;
import structures.Vector;
import structures.Matrix;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final int numberOfDestinations = 4;
    private static final int numberOfSources = 3;

    public static void main(String[] args) {
        //Reading the input and initializing a transportation problem instance
        TransportationProblem solver = input();
        if (solver == null) return;
        if (!solver.checkAdditionalRestrictions()) return;

        //Printing the initial problem table
        solver.printTransportationTable();

        //Solving the problem using North-West Corner method and printing the result
        solver.setAlgorithm(new NorthWest());
        Vector northWestSolution = solver.solve();
        printSolution(northWestSolution, "North-West Corner");

        //Solving the problem using Vogel's Approximation and printing the result
        solver.setAlgorithm(new VogelAlgorithm());
        Vector vogelSolution = solver.solve();
        printSolution(vogelSolution, "Vogel's Approximation");

        //Solving the problem using Russel's Approximation and printing the result
        solver.setAlgorithm(new RusselAlgorithm());
        Vector russelSolution = solver.solve();
        printSolution(russelSolution, "Russel's Approximation");
    }

    /**
     * Method for printing an initial basic feasible solution (vector x)
     *
     * @param solution      is the solution vector
     * @param algorithmName is the algorithm used for getting the solution
     */
    private static void printSolution(Vector solution, String algorithmName) {
        System.out.print("Initial basic feasible solution using " + algorithmName + " method: x = [");

        for (int i = 0; i < solution.getLength(); i++) {
            System.out.print(solution.get(i));
            if (i == solution.getLength() - 1) System.out.print("]");
            else System.out.print(", ");
        }

        System.out.println();
    }

    /**
     * Method for reading the input and initializing a TransportationProblem instance
     *
     * @return a variable storing demand, supply and costs
     */
    private static TransportationProblem input() {
        Scanner scanner = new Scanner(System.in);

        //Reading the input data
        try {
            System.out.println("Enter a vector of coefficients of supply (S):");
            Vector supply = VectorFactory.createVectorFromInput(numberOfSources, scanner);
            System.out.println("Enter a matrix of coefficients of costs (C):");
            Matrix costs = MatrixFactory.createMatrixFromInput(numberOfSources, numberOfDestinations, scanner);
            System.out.println("Enter a vector of coefficients of demand (D):");
            Vector demand = VectorFactory.createVectorFromInput(numberOfDestinations, scanner);

            return new TransportationProblem.Builder()
                    .setVectorSupply(supply)
                    .setMatrixCosts(costs)
                    .setVectorDemand(demand)
                    .algorithm(null)
                    .build();
        } catch (Exception ex) {
            System.out.println("Wrong input!");
            return null;
        }
    }

}

/**
 * Algorithm's interface with method for solving the problem
 */
interface Algorithm {
    Vector solve(Vector supply, Vector demand, Matrix costs);
}

/**
 * Class for storing demand, supply, costs and the method for solving the transportation problem
 */
class TransportationProblem {
    private Vector supply;
    private Vector demand;
    private Matrix costs;

    private Algorithm algorithm;

    public TransportationProblem() {
    }

    /**
     * Class builder for initializing vectors, matrix and algorithm
     */
    public static class Builder {
        private final TransportationProblem solver = new TransportationProblem();

        public Builder setVectorSupply(Vector supply) {
            solver.supply = supply;
            return this;
        }

        public Builder setVectorDemand(Vector demand) {
            solver.demand = demand;
            return this;
        }

        public Builder setMatrixCosts(Matrix costs) {
            solver.costs = costs;
            return this;
        }

        public Builder algorithm(Algorithm algorithm) {
            solver.algorithm = algorithm;
            return this;
        }

        public TransportationProblem build() {
            return solver;
        }
    }

    /**
     * Method that solves the problem using a specified method stored as 'algorithm'
     *
     * @return a vector solution
     */
    public Vector solve() {
        if (algorithm == null)
            throw new IllegalStateException("Algorithm not set.");

        Vector supplyClone = supply.buildClone();
        Vector demandClone = demand.buildClone();
        Matrix costsClone = costs.buildClone();

        return algorithm.solve(supplyClone, demandClone, costsClone);
    }

    /**
     * Method for setting a type of algorithm used for solving the problem
     *
     * @param algorithm is an algorithm type
     */
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Method for printing an initial problem table
     */
    public void printTransportationTable() {
        if (costs == null || supply == null || demand == null) {
            throw new IllegalStateException("Cost matrix, supply vector, and demand vector must be set before printing the table.");
        }

        // Print table header
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%11s %30s %9s %10s%n", "|", "Cost Per Unit Distributed", "|", "|");
        System.out.printf("%52s %10s%n", "|----------------------------------------|", "|");
        System.out.printf("%11s %23s %16s %10s%n", "|", "Destination", "|", "|");
        System.out.printf("%52s %10s%n", "|----------------------------------------|", "|");
        System.out.printf("%11s %9s %9s %9s %9s| %10s%n", "|", "1", "2", "3", "4", "Supply  |");
        System.out.println("----------|----------------------------------------|----------|");

        // Print cost matrix and supply vector
        for (int i = 0; i < costs.getNumberOfRows(); i++) {
            if (i == 1) System.out.printf("%10s|", "Source  " + (i + 1));
            else System.out.printf("%10s|", i + 1);
            for (int j = 0; j < costs.getNumberOfColumns(); j++) {
                System.out.printf("%10s", costs.getItem(i, j));
            }
            System.out.print("|");
            System.out.printf("%10s|%n", supply.get(i));
        }

        // Print demand vector
        System.out.println("----------|----------------------------------------|----------|");
        System.out.printf("%10s|", "Demand   ");
        for (int i = 0; i < costs.getNumberOfColumns(); i++) {
            System.out.printf("%10s", demand.get(i));
        }
        System.out.printf("|%11s%n", "|");
        System.out.println("---------------------------------------------------------------");
    }

    /**
     * Function for checking if methods are applicable
     *
     * @return true if applicable, false otherwise
     */
    public boolean checkAdditionalRestrictions() {
        for (int i = 0; i < supply.getLength(); i++)
            if (supply.get(i) < 0) {
                System.out.println("The method is not applicable!");
                return false;
            }

        for (int i = 0; i < demand.getLength(); i++)
            if (demand.get(i) < 0) {
                System.out.println("The method is not applicable!");
                return false;
            }

        if (supply.getSum() == 0 || demand.getSum() == 0) {
            System.out.println("The method is not applicable!");
            return false;
        }

        if (supply.getSum() != demand.getSum()) {
            System.out.println("The problem is not balanced!");
            return false;
        }

        return true;
    }
}

/**
 * Class for North-West method
 */
class NorthWest implements Algorithm {
    /**
     * Method for solving the problem using North-West Corner method
     *
     * @param supply is a vector of supply
     * @param demand is a vector of demand
     * @param costs is a matrix of costs
     * @return a solution vector
     */
    @Override
    public Vector solve(Vector supply, Vector demand, Matrix costs) {
        Vector answer = VectorFactory.createEmptyVector(costs.getNumberOfRows() * costs.getNumberOfColumns());
        int answerIndex = 0;

        for (int rowIndex = 0; rowIndex < costs.getNumberOfRows(); rowIndex++) {
            for (int columnIndex = 0; columnIndex < costs.getNumberOfColumns(); columnIndex++) {
                if (supply.get(rowIndex) <= demand.get(columnIndex)) {
                    answer.set(answerIndex, supply.get(rowIndex));
                    answerIndex++;
                    demand.set(columnIndex, demand.get(columnIndex) - supply.get(rowIndex));
                    supply.set(rowIndex, 0);
                } else {
                    answer.set(answerIndex, demand.get(columnIndex));
                    answerIndex++;
                    supply.set(rowIndex, supply.get(rowIndex) - demand.get(columnIndex));
                    demand.set(columnIndex, 0);
                }
            }
        }
        return answer;
    }
}

/**
 * Class for Vogel's Approximation
 */
class VogelAlgorithm implements Algorithm {
    /**
     * Method for solving the problem using Vogel's Approximation
     *
     * @param supply is a vector of supply
     * @param demand is a vector of demand
     * @param costs is a matrix of costs
     * @return a solution vector
     */
    @Override
    public Vector solve(Vector supply, Vector demand, Matrix costs) {
        Vector answer = VectorFactory.createEmptyVector(costs.getNumberOfRows() * costs.getNumberOfColumns());

        do {
            Vector rowsDiffs = getDiffsForListOfVectors(costs.getRows());
            Vector colsDiffs = getDiffsForListOfVectors(costs.getColumns());

            Vector targetVector;
            int maxRowDiffIndex = rowsDiffs.getMaxValueIndex();
            int maxColDiffIndex = colsDiffs.getMaxValueIndex();

            if (rowsDiffs.get(maxRowDiffIndex) > colsDiffs.get(maxColDiffIndex))
                targetVector = costs.getRow(maxRowDiffIndex);
            else targetVector = costs.getColumn(maxColDiffIndex);

            int minIndex = -1;
            for (int i = 0; i < targetVector.getLength(); i++) {
                if (targetVector.get(i) == -1) continue;

                if (minIndex == -1) minIndex = i;
                else if (targetVector.get(i) < targetVector.get(minIndex)) minIndex = i;
            }

            int[] cellToPick = new int[2];
            if (rowsDiffs.get(maxRowDiffIndex) > colsDiffs.get(maxColDiffIndex)) {
                cellToPick[0] = maxRowDiffIndex;
                cellToPick[1] = minIndex;
            } else {
                cellToPick[0] = minIndex;
                cellToPick[1] = maxColDiffIndex;
            }

            int allocate = Math.min(supply.get(cellToPick[0]), demand.get(cellToPick[1]));
            supply.set(cellToPick[0], supply.get(cellToPick[0]) - allocate);
            demand.set(cellToPick[1], demand.get(cellToPick[1]) - allocate);
            costs.setItem(cellToPick[0], cellToPick[1], -1);

            answer.set(cellToPick[0] * costs.getNumberOfColumns() + cellToPick[1], allocate);

        } while (supply.getSum() != 0 || demand.getSum() != 0);

        return answer;
    }

    /**
     * Method for finding the differences between two minimums in a rows or columns of a matrix of costs
     * @param vectors rows or columns of a matrix of costs
     * @return vector of differences
     */
    private Vector getDiffsForListOfVectors(List<Vector> vectors) {
        Vector diffs = VectorFactory.createEmptyVector(vectors.size());

        for (int i = 0; i < vectors.size(); i++) {
            Vector item = vectors.get(i);
            int min = Integer.MAX_VALUE;
            int secondMin = Integer.MAX_VALUE;

            for (int j = 0; j < item.getLength(); j++) {
                int value = item.get(j);

                if (value == -1) continue;

                if (value < min) {
                    secondMin = min;
                    min = value;
                } else if (value < secondMin) {
                    secondMin = value;
                }
            }

            diffs.set(i, secondMin - min);
        }

        return diffs;
    }
}

/**
 * Class for Russel's Approximation
 */
class RusselAlgorithm implements Algorithm {
    /**
     * Method for solving the problem using Russel's Approximation
     *
     * @param supply is a vector of supply
     * @param demand is a vector of demand
     * @param costs is a matrix of costs
     * @return a solution vector
     */
    @Override
    public Vector solve(Vector supply, Vector demand, Matrix costs) {
        Vector answer = VectorFactory.createEmptyVector(costs.getNumberOfRows() * costs.getNumberOfColumns());

        do {
            Vector rowsMaxes = getMaxesForListOfVectors(costs.getRows());
            Vector colsMaxes = getMaxesForListOfVectors(costs.getColumns());

            Matrix scores = MatrixFactory.createEmptyMatrix(costs.getNumberOfRows(), costs.getNumberOfColumns());

            for (int i = 0; i < costs.getNumberOfRows(); i++) {
                for (int j = 0; j < costs.getNumberOfColumns(); j++) {
                    if (costs.getItem(i, j) == -1) continue;
                    scores.setItem(i, j, costs.getItem(i, j) - rowsMaxes.get(i) - colsMaxes.get(j));
                }
            }

            int[] coordsOfMostNegative = scores.getCoordsOfMostNegative();

            int allocate = Math.min(supply.get(coordsOfMostNegative[0]), demand.get(coordsOfMostNegative[1]));
            supply.set(coordsOfMostNegative[0], supply.get(coordsOfMostNegative[0]) - allocate);
            demand.set(coordsOfMostNegative[1], demand.get(coordsOfMostNegative[1]) - allocate);
            costs.setItem(coordsOfMostNegative[0], coordsOfMostNegative[1], -1);

            answer.set(coordsOfMostNegative[0] * costs.getNumberOfColumns() + coordsOfMostNegative[1], allocate);

        } while (supply.getSum() != 0 || demand.getSum() != 0);

        return answer;
    }

    /**
     * Method for finding maximums in each row or each column of a matrix of costs
     * @param vectors rows or columns of a matrix
     * @return a vector of maximums
     */
    private Vector getMaxesForListOfVectors(List<Vector> vectors) {
        Vector maxes = VectorFactory.createEmptyVector(vectors.size());

        for (int i = 0; i < vectors.size(); i++) {
            Vector item = vectors.get(i);
            int max = Integer.MIN_VALUE;

            for (int j = 0; j < item.getLength(); j++) {
                int value = item.get(j);
                if (value == -1) continue;
                if (value > max) max = value;
            }

            maxes.set(i, max);
        }

        return maxes;
    }
}

