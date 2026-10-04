public class TransferenciaInternacional implements TipoTransferencia {
    public double calcularComision(double monto) { return monto * 0.03 + 25_000; }
}
