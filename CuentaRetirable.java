/**
 * Solo las cuentas que de verdad permiten retiros libres implementan esta
 * interfaz. Un CDT NO la implementa: así, pasar un CDT a un método que
 * exige CuentaRetirable es un error de COMPILACIÓN, no una excepción en
 * producción a las 2 a.m. durante el cobro masivo de cuota de manejo.
 */
public interface CuentaRetirable extends Cuenta {
    void retirar(double monto);
}
