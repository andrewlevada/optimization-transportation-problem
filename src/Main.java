import structures.VectorFactory;
import structures.MatrixFactory;
import structures.Vector;
import structures.Matrix;

import java.util.Scanner;

public class Main {

    private static final int numberOfDestinations = 4;
    private static final int numberOfSources = 3;

    public static void main(String[] args) {
        TransportationProblem solver = input();
        if (solver == null) return;

        solver.printTransportationTable();

        Algorithm northWestAlgorithm = new NorthWest();
        Algorithm vogelAlgorithm = new VogelAlgorithm();
        Algorithm russelAlgorithm = new RusselAlgorithm();

        solver.setAlgorithm(northWestAlgorithm);
        Vector northWestSolution = solver.solve();

        solver.setAlgorithm(vogelAlgorithm);
        Vector vogelSolution = solver.solve();

        solver.setAlgorithm(russelAlgorithm);
        Vector russelSolution = solver.solve();
    }

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

interface Algorithm {
    Vector solve(Vector supply, Vector demand, Matrix costs);
}

class TransportationProblem {
    private Vector supply;
    private Vector demand;
    private Matrix costs;

    private Algorithm algorithm;

    public TransportationProblem() {
    }

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

    public Vector solve() {
        if (algorithm == null) {
            throw new IllegalStateException("Algorithm not set.");
        }
        return algorithm.solve(supply, demand, costs);
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

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

}

class NorthWest implements Algorithm {
    @Override
    public Vector solve(Vector supply, Vector demand, Matrix costs) {
        return supply;
    }
}

class VogelAlgorithm implements Algorithm {
    @Override
    public Vector solve(Vector supply, Vector demand, Matrix costs) {
        return supply;
    }
}

class RusselAlgorithm implements Algorithm {
    @Override
    public Vector solve(Vector supply, Vector demand, Matrix costs) {
        return supply;
    }
}

