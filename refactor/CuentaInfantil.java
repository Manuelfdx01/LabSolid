import java.time.LocalDate;

public class CuentaInfantil extends CuentaAhorros {

    private static final double LIMITE_RETIROS_DIARIO = 200_000;

    private double retirosHoy = 0;
    private LocalDate fechaRetiros = LocalDate.now();

    public CuentaInfantil(String numero, String titular, double saldoInicial) {
        super(numero, titular, saldoInicial);
    }

    @Override
    public void retirar(double monto) {
        actualizarDia();

        if (retirosHoy + monto > LIMITE_RETIROS_DIARIO) {
            throw new IllegalStateException(
                    "La cuenta infantil supera el límite de retiros diarios de $200.000"
            );
        }

        super.retirar(monto);
        retirosHoy += monto;
    }

    public double getRetirosHoy() {
        actualizarDia();
        return retirosHoy;
    }

    private void actualizarDia() {
        LocalDate hoy = LocalDate.now();
        if (!hoy.equals(fechaRetiros)) {
            retirosHoy = 0;
            fechaRetiros = hoy;
        }
    }
}
