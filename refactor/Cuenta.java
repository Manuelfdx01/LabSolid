/**
 * Contrato mínimo común a cualquier producto tipo "cuenta": se le puede
 * depositar y consultar su saldo. Deliberadamente NO incluye retirar():
 * no todas las cuentas permiten retiros libres (ver CDT), así que esa
 * capacidad vive en la interfaz separada CuentaRetirable (principio L).
 */
public interface Cuenta extends GeneraExtracto {
    String getNumero();
    String getTitular();
    double getSaldo();
    void depositar(double monto);
}
