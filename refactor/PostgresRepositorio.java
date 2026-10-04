public class PostgresRepositorio implements RepositorioTransacciones {

    @Override
    public void guardarTransaccion(
            String origen,
            String destino,
            double monto,
            double comision) {

        System.out.println(
                "[POSTGRES] INSERT INTO transacciones VALUES ('"
                        + origen + "', '"
                        + destino + "', "
                        + monto + ", "
                        + comision + ")"
        );
    }
}
