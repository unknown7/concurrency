package sharingConstrainedResources;

import sharingConstrainedResources.generator.Generator;
import sharingConstrainedResources.generator.MutexEvenGenerator;

public class Tester {
    public static void main(String[] args) {
        Generator generator = new MutexEvenGenerator();
        EvenChecker.test(generator);
    }
}
