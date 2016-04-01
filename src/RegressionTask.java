import BackPropagationLearning.ActivationFunction;
import BackPropagationLearning.ActivationFunctions;
import BackPropagationLearning.BackPropagationNetwork;
import RegFunctions.RegressionFunction;

import java.awt.*;
import java.util.Random;

public abstract class RegressionTask implements RegressionFunction {

    private double [][]inputs;
    private double [][]desiredOutputs;

    public RegressionTask(double minRange, double maxRange, int iSize) {
        inputs = new double[iSize][2];
        Random random = new Random(1);
        double j = 0;
        for (int i = 0; i < iSize; ++i) {
            inputs[i][1] = minRange + random.nextDouble() * (maxRange - minRange) / iSize + j;
            inputs[i][0] = 1;
            j += (maxRange - minRange) / iSize;
        }

        desiredOutputs = new double[iSize][1];
        for (int i = 0; i < iSize; ++i) {
            desiredOutputs[i][0] = fun(inputs[i][1]);
            System.out.println(inputs[i][1] + "        " + desiredOutputs[i][0]);
        }
    }

    public boolean trainNetwork(int iterationCount, LinesComponent comp) {
        int []lSize = {inputs.length + 10, 1};
        ActivationFunction af = new ActivationFunction() {
            @Override
            public double activationFunction(double S) {
                return ActivationFunctions.tanh(S);
            }

            @Override
            public double functionDerivative(double S) {
                return ActivationFunctions.derivativeTanh(S);
            }
        };
        BackPropagationNetwork network =
                new BackPropagationNetwork(inputs[0].length, lSize, desiredOutputs, af);
        Random random = new Random(1);
        for (int i = 0; i < network.getLayers().length; ++i) {
            for (int j = 0; j < network.getLayers()[i].getNeurons().length; ++j) {
                double[] w = new double[network.getLayers()[i].getNeurons()[j].getInputs().length];
                for (int k = 0; k < w.length; ++k) {
                    w[k] = random.nextDouble() * 2 - 1;
                }
                network.getLayers()[i].getNeurons()[j].setWeights(w);
            }
        }
        network.setEpsilon(1);
        network.setAlpha(0.1);
        //network.disableMomentum();
        network.disableWeightMinimization();
        int k = 0;
        for (int j = 0; j < iterationCount; ++j) {
            network.setInputs(inputs[k % (inputs.length)]);
            network.setDesiredOutput(desiredOutputs[k % (inputs.length)]);
            network.trainNetwork();
            System.out.println(j);
            ++k;
            if (j % 40 == 0) {
                k = 0;
                comp.clearLines();
                network.err_cnt = 0;
                for (int i = 0; i < 10; ++i) {
                    comp.addLine(50 + i * 100, 0, 50 + i * 100, 400);
                }
                for (int i = -10; i < 30; i += 2) {
                    comp.addLine(50, 300 - i * 10, 1000, 300 - i * 10);
                }
                for (double i = 0; i < 10; i += 0.25) {
                    comp.addLine(
                            50 + (int) (i * 100),
                            (int)(300 - fun(i) * 100),
                            50 + (int)((i + 0.25) * 100),
                            (int)(300 - fun(i + 0.25) * 100), new Color(255, 0, 0)
                    );
                }
                for (int i = 0; i < inputs.length - 1; ++i) {
                    comp.addLine(
                            50 + (int) (inputs[i][1] * 100),
                            (int) (300 - network.networkOutput(inputs[i])[0] * 100),
                            50 + (int) (inputs[i + 1][1] * 100),
                            (int) (300 - network.networkOutput(inputs[i + 1])[0] * 100), new Color(0, 153, 0)
                    );
                }
            }
        }
        return true;
    }
}
