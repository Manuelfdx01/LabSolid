



/**
 * 5 pruebas pedidas en el bloque 3. Ninguna toca Oracle ni envía un SMS
 * real: TransaccionService recibe RepositorioFalso y NotificadorFalso
 * (dobles de prueba) gracias a que ahora depende de abstracciones
 * (RepositorioTransacciones, Notificador) y no las crea con `new`
 * (principio D, punto de control D).
 */
public class PruebasTransaccionService {

    // --- Fábrica de un TransaccionService "de prueba" para cada caso ---
    private static TransaccionService crearServicio(RepositorioFalso repo, NotificadorFalso sms) {
        CalculadoraComision calculadora = new CalculadoraComision();
        calculadora.registrar("MISMO_BANCO", new TransferenciaMismoBanco());
        calculadora.registrar("OTRO_BANCO", new TransferenciaOtroBanco());
        calculadora.registrar("INTERNACIONAL", new TransferenciaInternacional());
        return new TransaccionService(repo, sms, calculadora);
    }

    static void test1_mismoBancoSinComision() {
        RepositorioFalso repo = new RepositorioFalso();
        NotificadorFalso sms = new NotificadorFalso();
        TransaccionService servicio = crearServicio(repo, sms);

        CuentaAhorros origen = new CuentaAhorros("A", "Ana", 1_000_000);
        CuentaAhorros destino = new CuentaAhorros("B", "Beto", 0);

        servicio.transferir(origen, destino, 100_000, "MISMO_BANCO");

        Assertions.assertEquals(900_000, origen.getSaldo(), "Saldo origen tras transferencia mismo banco");
        Assertions.assertEquals(100_000, destino.getSaldo(), "Saldo destino tras transferencia mismo banco");
        Assertions.assertTrue(repo.guardadas.get(0).endsWith("100000.0/0.0"), "Comisión guardada debe ser 0");
    }

    static void test2_otroBancoCobraComisionFija() {
        RepositorioFalso repo = new RepositorioFalso();
        NotificadorFalso sms = new NotificadorFalso();
        TransaccionService servicio = crearServicio(repo, sms);

        CuentaAhorros origen = new CuentaAhorros("A", "Ana", 1_000_000);
        CuentaAhorros destino = new CuentaAhorros("B", "Beto", 0);

        servicio.transferir(origen, destino, 100_000, "OTRO_BANCO");

        // monto + comision = 100000 + 7500 = 107500 descontados del origen
        Assertions.assertEquals(892_500, origen.getSaldo(), "Origen debe descontar monto + comisión ($7.500)");
        Assertions.assertEquals(100_000, destino.getSaldo(), "Destino solo recibe el monto, sin comisión");
    }

    static void test3_saldoInsuficienteNoGuardaNiNotifica() {
        RepositorioFalso repo = new RepositorioFalso();
        NotificadorFalso sms = new NotificadorFalso();
        TransaccionService servicio = crearServicio(repo, sms);

        CuentaAhorros origen = new CuentaAhorros("A", "Ana", 1_000);
        CuentaAhorros destino = new CuentaAhorros("B", "Beto", 0);

        Assertions.assertThrows(IllegalStateException.class,
                () -> servicio.transferir(origen, destino, 100_000, "MISMO_BANCO"),
                "Transferencia con saldo insuficiente debe rechazarse");

        Assertions.assertTrue(repo.guardadas.isEmpty(), "No debe guardarse nada si la transferencia falla");
        Assertions.assertTrue(sms.mensajesEnviados.isEmpty(), "No debe notificarse nada si la transferencia falla");
    }

    static void test4_unaSolaTransaccionUnaSolaNotificacion() {
        RepositorioFalso repo = new RepositorioFalso();
        NotificadorFalso sms = new NotificadorFalso();
        TransaccionService servicio = crearServicio(repo, sms);

        CuentaAhorros origen = new CuentaAhorros("A", "Ana", 1_000_000);
        CuentaAhorros destino = new CuentaAhorros("B", "Beto", 0);

        servicio.transferir(origen, destino, 50_000, "MISMO_BANCO");

        Assertions.assertEquals(1, repo.guardadas.size(), "Debe guardarse exactamente una transacción");
        Assertions.assertEquals(1, sms.mensajesEnviados.size(), "Debe enviarse exactamente una notificación");
    }

    static void test5_tipoDesconocidoNoMueveElSaldo() {
        RepositorioFalso repo = new RepositorioFalso();
        NotificadorFalso sms = new NotificadorFalso();
        TransaccionService servicio = crearServicio(repo, sms);

        CuentaAhorros origen = new CuentaAhorros("A", "Ana", 1_000_000);
        CuentaAhorros destino = new CuentaAhorros("B", "Beto", 0);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> servicio.transferir(origen, destino, 100_000, "CRIPTO"),
                "Tipo de transferencia desconocido debe rechazarse");

        Assertions.assertEquals(1_000_000, origen.getSaldo(), "El saldo de origen no debe cambiar");
    }

    public static void main(String[] args) {
        long inicio = System.nanoTime();
        String[] nombres = {
            "test1_mismoBancoSinComision",
            "test2_otroBancoCobraComisionFija",
            "test3_saldoInsuficienteNoGuardaNiNotifica",
            "test4_unaSolaTransaccionUnaSolaNotificacion",
            "test5_tipoDesconocidoNoMueveElSaldo"
        };
        Runnable[] tests = {
            PruebasTransaccionService::test1_mismoBancoSinComision,
            PruebasTransaccionService::test2_otroBancoCobraComisionFija,
            PruebasTransaccionService::test3_saldoInsuficienteNoGuardaNiNotifica,
            PruebasTransaccionService::test4_unaSolaTransaccionUnaSolaNotificacion,
            PruebasTransaccionService::test5_tipoDesconocidoNoMueveElSaldo
        };

        int ok = 0;
        for (int i = 0; i < tests.length; i++) {
            try {
                tests[i].run();
                System.out.println("OK   - " + nombres[i]);
                ok++;
            } catch (AssertionError e) {
                System.out.println("FAIL - " + nombres[i] + " : " + e.getMessage());
            }
        }
        long finMs = (System.nanoTime() - inicio) / 1_000_000;
        System.out.println(ok + "/" + tests.length + " pruebas pasaron en " + finMs + " ms");
    }
}
