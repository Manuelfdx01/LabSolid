public class CuentaAhorros extends CuentaBase implements CuentaRetirable {
    public CuentaAhorros(String numero, String titular, double saldoInicial) {
        super(numero, titular, saldoInicial);
    }

    public void retirar(double monto) {
        if (monto > saldo) throw new IllegalStateException("Saldo insuficiente");
        saldo -= monto;
    }
}
