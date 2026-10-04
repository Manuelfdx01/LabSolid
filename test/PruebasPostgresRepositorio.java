import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PruebasPostgresRepositorio {

    @Test
    void guardaLaTransaccionEnPostgresSimulado() {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        PrintStream original = System.out;

        try {
            System.setOut(new PrintStream(salida));
            new PostgresRepositorio().guardarTransaccion("A", "B", 50_000, 0);
        } finally {
            System.setOut(original);
        }

        Assertions.assertTrue(salida.toString().contains("[POSTGRES]"));
        Assertions.assertTrue(salida.toString().contains("50000.0"));
    }
}
