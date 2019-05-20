package sharingConstrainedResources.lock;

import sharingConstrainedResources.lock.generator.Generator;
import sharingConstrainedResources.lock.generator.MutexEvenGenerator;

public class Tester {
    public static void main(String[] args) {
        Generator generator = new MutexEvenGenerator();
        EvenChecker.test(generator);
    }
}
