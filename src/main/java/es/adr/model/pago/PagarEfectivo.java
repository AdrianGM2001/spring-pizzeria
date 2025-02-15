package es.adr.model.pago;

public class PagarEfectivo implements Pagable {
    @Override
    public void pagar(double cantidad) {
        System.out.printf("Se ha pagado %.2feur en efectivo%n", cantidad);
    }
}
