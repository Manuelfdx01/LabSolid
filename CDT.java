import java.time.LocalDate;

/**
 * Un CDT ya NO implementa CuentaRetirable: no promete algo que no cumple.
 * Ofrece su propia operación, con su propio nombre y su propia regla de
 * negocio (retirarAlVencimiento), en vez de fingir ser un retirar()
 * genérico que explota si lo llaman antes de tiempo.
 */
public class CDT extends CuentaBase {
    private final LocalDate vencimiento;

    public CDT(String numero, String titular, double monto, LocalDate vencimiento) {
        super(numero, titular, monto);
        this.vencimiento = vencimiento;
    }

    public LocalDate getVencimiento() { return vencimiento; }

    public void retirarAlVencimiento(double monto) {
        if (LocalDate.now().isBefore(vencimiento)) {
            throw new UnsupportedOperationException(
                "Un CDT no permite retiros antes del vencimiento");
        }
        if (monto > saldo) throw new IllegalStateException("Saldo insuficiente");
        saldo -= monto;
    }
}
