package vector;

import java.util.logging.Logger;

public class Vector {
    private int[] vector;

    public Vector(int[] vector) {
        this.vector = vector;
    }

    public int[] getVector() {
        return vector;
    }

    public double detVectorLength() {
        double vectorLength = 0;

        for(int i = 0; i < vector.length; i++)
            vectorLength += Math.pow(vector[i] , 2);

        return Math.sqrt(vectorLength);
    }

    public static Vector operationsWithVectors(Vector frstVector, Vector scndVector, Operation operation) {
        if(frstVector.vector.length != scndVector.vector.length) {
            try {
                Logger LOGGER = Logger.getLogger(Vector.class.getName());
                LOGGER.info(("Vectors have different lengths."));
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int[] currVector = new int[frstVector.vector.length];

        for(int i = 0; i < frstVector.vector.length; i++) {
            if(operation.equals(Operation.SUMMATION)) {
                currVector[i] = frstVector.vector[i] + scndVector.vector[i];
            } else if(operation.equals(Operation.DIFFERENCE)) {
                currVector[i] = frstVector.vector[i] - scndVector.vector[i];
            }
        }

        return new Vector(currVector);
    }
}
