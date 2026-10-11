
package pe.cibertec.pedidoservicegrupo4.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoResponseDTO {

    private Long idPedido;
    private String codigoSeguimiento;
    private LocalDateTime fechaEmision;
    private Long clienteId;
    private String estadoPedido;
    private List<DetallePedidoDTO> listaDetalles;
    private Double montoTotalCalculado;

    public PedidoResponseDTO() {
        this.listaDetalles = new ArrayList<>();
        this.montoTotalCalculado = 0.0;
    }

    public PedidoResponseDTO(
            Long idPedido,
            String codigoSeguimiento,
            LocalDateTime fechaEmision,
            Long clienteId,
            String estadoPedido,
            List<DetallePedidoDTO> listaDetalles,
            Double montoTotalCalculado
    ) {
        this.idPedido = idPedido;
        this.codigoSeguimiento = codigoSeguimiento;
        this.fechaEmision = fechaEmision;
        this.clienteId = clienteId;
        this.estadoPedido = estadoPedido;
        this.listaDetalles = listaDetalles != null
                ? listaDetalles
                : new ArrayList<>();
        calcularMontoTotal();
    }

    public void calcularMontoTotal() {
        double total = 0.0;

        if (listaDetalles != null) {
            for (DetallePedidoDTO detalle : listaDetalles) {
                if (detalle != null) {
                    double subtotal = detalle.getCantidad()
                            * detalle.getPrecioUnitario();

                    detalle.setSubtotal(subtotal);
                    total += subtotal;
                }
            }
        }

        this.montoTotalCalculado = total;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public String getCodigoSeguimiento() {
        return codigoSeguimiento;
    }

    public void setCodigoSeguimiento(String codigoSeguimiento) {
        this.codigoSeguimiento = codigoSeguimiento;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public List<DetallePedidoDTO> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<DetallePedidoDTO> listaDetalles) {
        this.listaDetalles = listaDetalles != null
                ? listaDetalles
                : new ArrayList<>();
        calcularMontoTotal();
    }

    public Double getMontoTotalCalculado() {
        calcularMontoTotal();
        return montoTotalCalculado;
    }

    public void setMontoTotalCalculado(Double montoTotalCalculado) {
        this.montoTotalCalculado = montoTotalCalculado;
    }
}
