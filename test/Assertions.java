/**
 * Mini-framework de aserciones hecho a mano. Este contenedor no tiene salida
 * de red hacia Maven Central, así que no pude descargar JUnit aquí. La
 * lógica de las pruebas es la misma que escribirían con JUnit 5: solo
 * cambia la sintaxis de las aserciones (@Test, assertEquals de org.junit...)
 * En el README explico línea por línea cómo pasar esto a JUnit real.
 */
public class Assertions {
    static void assertEquals(double esperado, double real, String mensaje) {
        if (Math.abs(esperado - real) > 0.0001) {
            throw new AssertionError(mensaje + " -> esperado=" + esperado + " real=" + real);
        }
    }

    static void assertEquals(int esperado, int real, String mensaje) {
        if (esperado != real) {
            throw new AssertionError(mensaje + " -> esperado=" + esperado + " real=" + real);
        }
    }

    static void assertTrue(boolean condicion, String mensaje) {
        if (!condicion) throw new AssertionError(mensaje);
    }

    interface Bloque { void ejecutar(); }

    static void assertThrows(Class<? extends Throwable> tipoEsperado, Bloque bloque, String mensaje) {
        try {
            bloque.ejecutar();
        } catch (Throwable t) {
            if (tipoEsperado.isInstance(t)) return;
            throw new AssertionError(mensaje + " -> se lanzó " + t.getClass().getSimpleName()
                    + " en vez de " + tipoEsperado.getSimpleName());
        }
        throw new AssertionError(mensaje + " -> no se lanzó ninguna excepción");
    }
}
