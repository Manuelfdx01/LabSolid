import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PruebasCuentaInfantil {

    @Test
    void rechazaRetiroQueSuperaElLimiteDiario() {
        CuentaInfantil cuenta = new CuentaInfantil("CI-1", "Menor", 500_000);

        cuenta.retirar(150_000);

        Assertions.assertThrows(
                IllegalStateException.class,
                () -> cuenta.retirar(60_000)
        );

        Assertions.assertEquals(350_000, cuenta.getSaldo());
        Assertions.assertEquals(150_000, cuenta.getRetirosHoy());
    }

    @Test
    void permiteDepositosSinLimiteDiario() {
        CuentaInfantil cuenta = new CuentaInfantil("CI-1", "Menor", 0);

        cuenta.depositar(500_000);
        cuenta.depositar(750_000);

        Assertions.assertEquals(1_250_000, cuenta.getSaldo());
    }

    @Test
    void puedeSerOrigenDeTransferencias() {
        RepositorioFalso repo = new RepositorioFalso();
        NotificadorFalso notificador = new NotificadorFalso();
        CalculadoraComision calculadora = new CalculadoraComision();
        calculadora.registrar("LLAVE", new TransferenciaLlave());

        TransaccionService servicio =
                new TransaccionService(repo, notificador, calculadora);

        CuentaInfantil origen = new CuentaInfantil("CI-1", "Menor", 100_000);
        CuentaAhorros destino = new CuentaAhorros("A", "Ana", 0);

        servicio.transferir(origen, destino, 50_000, "LLAVE");

        Assertions.assertEquals(50_000, origen.getSaldo());
        Assertions.assertEquals(50_000, destino.getSaldo());
    }
}
