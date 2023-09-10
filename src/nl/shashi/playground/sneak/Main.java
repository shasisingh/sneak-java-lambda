package nl.shashi.playground.sneak;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static nl.shashi.playground.sneak.Sneaky.sneak;
import static nl.shashi.playground.sneak.Sneaky.sneaked;


public class Main {
    public static void main(String[] args) {

        //Test01
        sneak( () ->  Thread.sleep(1000));
        sneak( () ->  TimeUnit.SECONDS.sleep(1));
        sneak(Main::testThrowCheckedException);

        String result = sneak(Main::testThrowCheckedExceptionWithReturn);
        System.out.println(result);

        IntStream.range(1,100)
                .boxed().map( vs -> {
            sneak(() -> Thread.sleep(1));
            return vs+1;
        }).forEach(System.out::println);

        sneaked(Main::testThrowCheckedException).run();
        sneaked(Main::testThrowCheckedExceptionWithReturn).get();
    }

    private static void testThrowCheckedException() throws IOException {
            System.out.println("some work to do..");
            throw new IOException("error => testThrowCheckedException");
    }

    private static String testThrowCheckedExceptionWithReturn() throws IOException {
        try {
            throw new IOException("execution");
        } catch (IOException e){
            return "some work to do..";
        }
    }
}
