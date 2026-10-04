public abstract class CuentaBase implements Cuenta {
    protected final String numero;
    protected final String titular;
    protected double saldo;

    protected CuentaBase(String numero, String titular, double saldoInicial) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldoInicial;
    }

    public String getNumero() { return numero; }
    public String getTitular() { return titular; }
    public double getSaldo() { return saldo; }

    public void depositar(double monto) {
        if (monto <= 0) throw new IllegalArgumentException("Monto inválido");
        saldo += monto;
    }

    public String generarExtracto() {
        return "Cuenta " + numero + " (" + titular + ") - saldo: $" + saldo;
    }
}
