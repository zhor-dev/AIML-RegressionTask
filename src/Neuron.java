import java.util.Random;

public abstract class Neuron implements ActivationFunction {

    private double []inputs;
    private double []weights;
    private final double bias = 1;

    public Neuron(double []i) {
        //this.inputs = new double[i.length + 1];
        //inputs[0] = bias;
        //System.arraycopy(i, 0, this.inputs, 1, i.length);
        this.inputs = i;
        weights = new double[inputs.length];
        Random random = new Random(1);
        for (int j = 0; j < inputs.length; ++j) {
            //int sign = Math.random() < 0.5 ? -1 : 1;
            //weights[j] = sign * Math.random();
            weights[j] = random.nextDouble() * 2 - 1;
        }
    }

    public void setInputs(double []i) {
        //this.inputs = new double[i.length + 1];
        //this.inputs[0] = bias;
        //System.arraycopy(i, 0, this.inputs, 1, i.length);
        this.inputs = i;
    }

    public void setWeights(double []w) {
        this.weights = w;
    }

    public double[] getInputs() {
        return inputs;
    }

    public double[] getWeights() {
        return weights;
    }

    public double summingBlock() {
        double sum = 0;
        for (int i = 0; i < inputs.length; ++i) {
            sum += inputs[i] * weights[i];
        }
        return sum;
    }

    public double neuronOutput() {
        return activationFunction(summingBlock());
    }

}
