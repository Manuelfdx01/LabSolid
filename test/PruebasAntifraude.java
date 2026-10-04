import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PruebasAntifraude {

    @Test
    void reportaUnaTransferenciaExitosa() {
        RepositorioFalso repo = new RepositorioFalso();
        NotificadorFalso notificador = new NotificadorFalso();
        CalculadoraComision calculadora = new CalculadoraComision();
        calculadora.registrar("LLAVE", new TransferenciaLlave());

        AntifraudeFalso antifraude = new AntifraudeFalso();
        TransaccionService servicio =
                new TransaccionService(repo, notificador, calculadora, antifraude);

        servicio.transferir(
                new CuentaAhorros("A", "Ana", 100_000),
                new CuentaAhorros("B", "Beto", 0),
                50_000,
                "LLAVE"
        );

        Assertions.assertEquals(1, antifraude.reportes);
        Assertions.assertEquals("A", antifraude.origen);
        Assertions.assertEquals("B", antifraude.destino);
        Assertions.assertEquals(50_000, antifraude.monto);
    }

    @Test
    void noReportaUnaTransferenciaRechazada() {
        RepositorioFalso repo = new RepositorioFalso();
        NotificadorFalso notificador = new NotificadorFalso();
        CalculadoraComision calculadora = new CalculadoraComision();
        calculadora.registrar("LLAVE", new TransferenciaLlave());

        AntifraudeFalso antifraude = new AntifraudeFalso();
        TransaccionService servicio =
                new TransaccionService(repo, notificador, calculadora, antifraude);

        Assertions.assertThrows(
                IllegalStateException.class,
                () -> servicio.transferir(
                        new CuentaAhorros("A", "Ana", 1_000),
                        new CuentaAhorros("B", "Beto", 0),
                        50_000,
                        "LLAVE"
                )
        );

        Assertions.assertEquals(0, antifraude.reportes);
        Assertions.assertTrue(repo.guardadas.isEmpty());
        Assertions.assertTrue(notificador.mensajesEnviados.isEmpty());
    }

    private static class AntifraudeFalso implements Antifraude {
        int reportes;
        String origen;
        String destino;
        double monto;

        @Override
        public void reportar(String origen, String destino, double monto) {
            this.reportes++;
            this.origen = origen;
            this.destino = destino;
            this.monto = monto;
        }
    }
}
