package pe.cibertec.pedidoservicegrupo4.dto;

import java.util.ArrayList;
import java.util.List;

public class ResumenConsolidadoClienteDTO {

    private Long clienteId;
    private String nombreCliente;
    private Integer totalPedidosRealizados;
    private Double montoTotalAcumulado;
    private List<PedidoResponseDTO> historialPedidos = new ArrayList<>();
    private List<PagoDTO> historialPagos = new ArrayList<>();
    private String estadoCuenta;

    public ResumenConsolidadoClienteDTO() {
    }

    public ResumenConsolidadoClienteDTO(
            Long clienteId,
            String nombreCliente,
            Integer totalPedidosRealizados,
            Double montoTotalAcumulado,
            List<PedidoResponseDTO> historialPedidos,
            List<PagoDTO> historialPagos,
            String estadoCuenta) {

        this.clienteId = clienteId;
        this.nombreCliente = nombreCliente;
        this.totalPedidosRealizados = totalPedidosRealizados;
        this.montoTotalAcumulado = montoTotalAcumulado;
        this.historialPedidos = historialPedidos != null
                ? historialPedidos : new ArrayList<>();
        this.historialPagos = historialPagos != null
                ? historialPagos : new ArrayList<>();
        this.estadoCuenta = estadoCuenta;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Integer getTotalPedidosRealizados() {
        return totalPedidosRealizados;
    }

    public void setTotalPedidosRealizados(Integer totalPedidosRealizados) {
        this.totalPedidosRealizados = totalPedidosRealizados;
    }

    public Double getMontoTotalAcumulado() {
        return montoTotalAcumulado;
    }

    public void setMontoTotalAcumulado(Double montoTotalAcumulado) {
        this.montoTotalAcumulado = montoTotalAcumulado;
    }

    public List<PedidoResponseDTO> getHistorialPedidos() {
        return historialPedidos;
    }

    public void setHistorialPedidos(
            List<PedidoResponseDTO> historialPedidos) {
        this.historialPedidos = historialPedidos != null
                ? historialPedidos : new ArrayList<>();
    }

    public List<PagoDTO> getHistorialPagos() {
        return historialPagos;
    }

    public void setHistorialPagos(List<PagoDTO> historialPagos) {
        this.historialPagos = historialPagos != null
                ? historialPagos : new ArrayList<>();
    }

    public String getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(String estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }
}
