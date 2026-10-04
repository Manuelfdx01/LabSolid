import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PruebasTransferenciaLlave {

    @Test
    void transferenciaPorLlaveNoCobraComision() {
        RepositorioFalso repo = new RepositorioFalso();
        NotificadorFalso notificador = new NotificadorFalso();

        CalculadoraComision calculadora = new CalculadoraComision();
        calculadora.registrar("LLAVE", new TransferenciaLlave());

        TransaccionService servicio =
                new TransaccionService(repo, notificador, calculadora);

        CuentaAhorros origen = new CuentaAhorros("A", "Ana", 100_000);
        CuentaAhorros destino = new CuentaAhorros("B", "Beto", 0);

        servicio.transferir(origen, destino, 50_000, "LLAVE");

        Assertions.assertEquals(50_000, origen.getSaldo());
        Assertions.assertEquals(50_000, destino.getSaldo());
        Assertions.assertEquals(1, repo.guardadas.size());
        Assertions.assertTrue(repo.guardadas.get(0).endsWith("50000.0/0.0"));
    }
}
