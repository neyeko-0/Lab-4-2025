import functions.*;
import functions.basic.*;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException, FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException, ClassNotFoundException {
        double right = Math.PI;
        Sin sin = new Sin();
        Cos cos = new Cos();
        TabulatedFunction TabSin = TabulatedFunctions.tabulate(sin, 0, right, 10);
        TabulatedFunction TabCos = TabulatedFunctions.tabulate(cos, 0, right, 10);
        System.out.println("Синус и косинус: ");
        for (double i = 0; i <= right; i += 0.1) {
            System.out.printf("Sin =  %f TabSin =  %f \t Cos = %f TabCos = %f\n",
                    sin.getFunctionValue(i), TabSin.getFunctionValue(i),
                    cos.getFunctionValue(i), TabCos.getFunctionValue(i));
        }
        Function trigonomSumOfSquares = Functions.sum(Functions.power(TabSin, 2.0), Functions.power(TabCos, 2.0));
        System.out.println("\n");

        System.out.println("Сумма квадратов синуса и косинуса: ");
        for (double i = 0; i <= right; i += 0.1) {
            System.out.printf("Arg =  %f Value = %f \n", i,  trigonomSumOfSquares.getFunctionValue(i));
        }
        System.out.println("\n");

        Exp exp = new Exp();
        FileWriter fw = new FileWriter(new File("out1.txt"));
        TabulatedFunction TabExp = TabulatedFunctions.tabulate(exp,0, 10, 11);
        TabulatedFunctions.writeTabulatedFunction(TabExp, fw);
        fw.close();

        FileReader fr = new FileReader(new File("out1.txt"));
        TabulatedFunction ReadTabExp = TabulatedFunctions.readTabulatedFunction(fr);

        System.out.println("Экспонента(символьный поток): ");
        for(int i = 0; i < TabExp.getPointsCount(); ++i) {
            System.out.printf("Exp =  %f \t ReadTabExp =  %f \n",
                    TabExp.getFunctionValue(i),
                    ReadTabExp.getFunctionValue(i));
        }
        System.out.println("\n");

        Log ln = new Log(Math.E);
        DataOutputStream out = new DataOutputStream(new FileOutputStream("out2.txt"));
        TabulatedFunction TabLn = TabulatedFunctions.tabulate(ln,1, 10, 11);
        TabulatedFunctions.outputTabulatedFunction(TabLn, out);
        out.close();

        DataInputStream in = new DataInputStream(new FileInputStream("out2.txt"));
        TabulatedFunction ReadTabLn = TabulatedFunctions.inputTabulatedFunction(in);

      System.out.println("Логарифм(байтовый поток): ");
for(int i = 0; i < TabLn.getPointsCount(); ++i) {
    double x = TabLn.getPointX(i);
    System.out.printf("x=%.1f: Ln = %f \t ReadTabLn = %f \n",
            x,
            TabLn.getFunctionValue(x),
            ReadTabLn.getFunctionValue(x));
}
        System.out.println("\n");

        Function LE = Functions.composition(new Log(Math.E), new Exp());
        TabulatedFunction LogExp = TabulatedFunctions.tabulate(LE, 0, 10, 11);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("out3.txt"));
        oos.writeObject(LogExp);
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("out3.txt"));
        TabulatedFunction SerLogExp = (TabulatedFunction)ois.readObject();
        ois.close();

        System.out.println("Логарифм от экспоненты: ");
        for(int i = 1; i <= 10; ++i) {
            System.out.printf("LogExp =  %f \t SerLogExp =  %f \n",
                    LogExp.getFunctionValue(i),
                    SerLogExp.getFunctionValue(i));
        }

    }

}