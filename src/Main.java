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
            Matrix costs = MatrixFactory.createMatrixFromInput(numberOfDestinations, numberOfSources, scanner);
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

