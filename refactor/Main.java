import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CuentaAhorros ana = new CuentaAhorros("001-1", "Ana", 2_000_000);
        CuentaAhorros luis = new CuentaAhorros("001-2", "Luis", 500_000);
        CDT cdtAna = new CDT("CDT-9", "Ana", 10_000_000, LocalDate.now().plusMonths(6));

        // --- Armado del sistema (composition root): aquí, y solo aquí,
        // se decide qué implementaciones concretas se usan. ---
        CalculadoraComision calculadoraComision = new CalculadoraComision();
        calculadoraComision.registrar("MISMO_BANCO", new TransferenciaMismoBanco());
        calculadoraComision.registrar("OTRO_BANCO", new TransferenciaOtroBanco());
        calculadoraComision.registrar("INTERNACIONAL", new TransferenciaInternacional());

        TransaccionService servicio = new TransaccionService(
                new OracleRepositorio(),
                new SmsGateway(),
                calculadoraComision
        );

        servicio.transferir(ana, luis, 150_000, "OTRO_BANCO");

        new CobroCuotaManejo().cobrarMensual(List.of(ana, luis));

        List<GeneraExtracto> productos =
                List.of(new TarjetaCredito(3_000_000), new CreditoVivienda(120_000_000));
        for (GeneraExtracto p : productos) System.out.println(p.generarExtracto());
    }
}
