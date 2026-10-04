/**
 * Responsabilidad única: ORQUESTAR una transferencia. Ya no valida con sus
 * propias reglas hardcodeadas, ni sabe calcular comisiones por caso, ni
 * imprime, ni decide cómo se persiste o se notifica: delega todo eso a
 * colaboradores. Y ya no crea esos colaboradores con `new` (principio D):
 * los recibe por el constructor, armados por el programa principal.
 */
public class TransaccionService {
    private final RepositorioTransacciones repositorio;
    private final Notificador notificador;
    private final CalculadoraComision calculadoraComision;
    private final ValidadorTransferencia validador = new ValidadorTransferencia();
    private final ComprobanteImpresor comprobante = new ComprobanteImpresor();
    private final AuditoriaLogger auditoria = new AuditoriaLogger();

    public TransaccionService(RepositorioTransacciones repositorio,
                               Notificador notificador,
                               CalculadoraComision calculadoraComision) {
        this.repositorio = repositorio;
        this.notificador = notificador;
        this.calculadoraComision = calculadoraComision;
    }

    public void transferir(CuentaRetirable origen, Cuenta destino, double monto, String tipo) {
        validador.validar(monto);

        double comision = calculadoraComision.calcular(tipo, monto);

        origen.retirar(monto + comision);
        destino.depositar(monto);

        repositorio.guardarTransaccion(origen.getNumero(), destino.getNumero(), monto, comision);
        comprobante.imprimir(origen.getNumero(), destino.getNumero(), monto, comision);
        notificador.enviar(origen.getTitular(), "Transferiste $" + monto + " a la cuenta " + destino.getNumero());
        auditoria.registrar(tipo, origen.getNumero(), destino.getNumero(), monto);
    }
}
