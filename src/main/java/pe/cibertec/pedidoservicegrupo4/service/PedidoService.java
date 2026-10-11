package pe.cibertec.pedidoservicegrupo4.service;

import pe.cibertec.pedidoservicegrupo4.client.PagoClient;
import pe.cibertec.pedidoservicegrupo4.dto.DetallePedidoDTO;
import pe.cibertec.pedidoservicegrupo4.dto.PagoDTO;
import pe.cibertec.pedidoservicegrupo4.dto.PedidoResponseDTO;
import pe.cibertec.pedidoservicegrupo4.dto.ResumenConsolidadoClienteDTO;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PagoClient pagoClient;

    private final List<PedidoResponseDTO> pedidos = new ArrayList<>();

    public PedidoService(PagoClient pagoClient) {
        this.pagoClient = pagoClient;

        List<DetallePedidoDTO> detalles1 = new ArrayList<>();

        detalles1.add(new DetallePedidoDTO(
                1L, "Laptop", 1, 2500.00
        ));

        detalles1.add(new DetallePedidoDTO(
                2L, "Mouse", 2, 50.00
        ));

        pedidos.add(new PedidoResponseDTO(
                1L,
                "PED-2026-001",
                null,
                1001L,
                "PENDIENTE",
                detalles1,
                0.0
        ));

        List<DetallePedidoDTO> detalles2 = new ArrayList<>();

        detalles2.add(new DetallePedidoDTO(
                3L, "Teclado", 1, 120.00
        ));

        detalles2.add(new DetallePedidoDTO(
                4L, "Monitor", 2, 650.00
        ));

        pedidos.add(new PedidoResponseDTO(
                2L,
                "PED-2026-002",
                null,
                1001L,
                "PENDIENTE",
                detalles2,
                0.0
        ));
    }

    public List<PedidoResponseDTO> listarPedidos() {
        return pedidos;
    }

    public Optional<PedidoResponseDTO> buscarPorId(Long idPedido) {
        return pedidos.stream()
                .filter(p -> p.getIdPedido().equals(idPedido))
                .findFirst();
    }

    public List<PedidoResponseDTO> listarPorCliente(Long clienteId) {
        return pedidos.stream()
                .filter(p -> p.getClienteId().equals(clienteId))
                .toList();
    }

    public PedidoResponseDTO actualizarEstado(
            Long idPedido, String estado) {

        Optional<PedidoResponseDTO> resultado = buscarPorId(idPedido);

        if (resultado.isEmpty()) {
            return null;
        }

        PedidoResponseDTO pedido = resultado.get();
        pedido.setEstadoPedido(estado);

        return pedido;
    }

    public PedidoResponseDTO verificarYCompletar(Long idPedido) {

        Optional<PedidoResponseDTO> resultado = buscarPorId(idPedido);

        if (resultado.isEmpty()) {
            return null;
        }

        PedidoResponseDTO pedido = resultado.get();

        PagoDTO pago = pagoClient.obtenerPagoPorPedido(idPedido);

        if (pago != null
                && pago.getMontoPagado() != null
                && Math.abs(
                pago.getMontoPagado()
                        - pedido.getMontoTotalCalculado()
        ) < 0.01
                && pago.getEstadoPago() != null
                && pago.getEstadoPago().equalsIgnoreCase("APROBADO")) {

            pedido.setEstadoPedido("COMPLETADO");
        }

        return pedido;
    }

    public ResumenConsolidadoClienteDTO consolidarCliente(Long clienteId) {

        List<PedidoResponseDTO> pedidosCliente =
                listarPorCliente(clienteId);

        List<PagoDTO> pagosCliente =
                pagoClient.obtenerPagosPorCliente(clienteId);

        if (pagosCliente == null) {
            pagosCliente = new ArrayList<>();
        }

        double montoTotalPedidos = pedidosCliente.stream()
                .mapToDouble(PedidoResponseDTO::getMontoTotalCalculado)
                .sum();

        double montoTotalPagado = pagosCliente.stream()
                .filter(p -> p.getEstadoPago() != null
                        && p.getEstadoPago().equalsIgnoreCase("APROBADO"))
                .mapToDouble(p -> p.getMontoPagado() == null
                        ? 0.0
                        : p.getMontoPagado())
                .sum();

        String estadoCuenta =
                montoTotalPagado >= montoTotalPedidos
                        ? "AL_DIA"
                        : "CON_DEUDAS";

        ResumenConsolidadoClienteDTO resumen =
                new ResumenConsolidadoClienteDTO();

        resumen.setClienteId(clienteId);
        resumen.setNombreCliente("Cliente " + clienteId);
        resumen.setTotalPedidosRealizados(pedidosCliente.size());
        resumen.setMontoTotalAcumulado(montoTotalPedidos);
        resumen.setHistorialPedidos(pedidosCliente);
        resumen.setHistorialPagos(pagosCliente);
        resumen.setEstadoCuenta(estadoCuenta);

        return resumen;
    }
}
