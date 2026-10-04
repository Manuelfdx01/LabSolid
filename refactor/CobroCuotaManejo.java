import java.util.List;

public class CobroCuotaManejo {
    private static final double CUOTA = 12_900;

    // Antes: List<Cuenta>. Ahora: List<CuentaRetirable>. Un CDT ya NO es
    // CuentaRetirable, así que ni siquiera compila pasarlo aquí.
    public void cobrarMensual(List<CuentaRetirable> cuentas) {
        for (CuentaRetirable cuenta : cuentas) {
            cuenta.retirar(CUOTA);
            System.out.println("Cuota de manejo cobrada a " + cuenta.getNumero());
        }
    }
}
