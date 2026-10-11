
package pe.cibertec.pedidoservicegrupo4.controller;

import pe.cibertec.pedidoservicegrupo4.dto.PedidoResponseDTO;
import pe.cibertec.pedidoservicegrupo4.dto.ResumenConsolidadoClienteDTO;
import pe.cibertec.pedidoservicegrupo4.service.PedidoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }

    @GetMapping("/{idPedido}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(
            @PathVariable Long idPedido
    ) {
        return pedidoService.buscarPorId(idPedido)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorCliente(
            @PathVariable Long clienteId
    ) {
        return ResponseEntity.ok(
                pedidoService.listarPorCliente(clienteId)
        );
    }

    @PatchMapping("/{idPedido}/estado")
    public ResponseEntity<PedidoResponseDTO> actualizarEstado(
            @PathVariable Long idPedido,
            @RequestParam String estado
    ) {
        PedidoResponseDTO pedido =
                pedidoService.actualizarEstado(idPedido, estado);

        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/verificar-y-completar/{idPedido}")
    public ResponseEntity<PedidoResponseDTO> verificarYCompletar(
            @PathVariable Long idPedido
    ) {
        try {
            PedidoResponseDTO pedido =
                    pedidoService.verificarYCompletar(idPedido);

            if (pedido == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(pedido);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .build();
        }
    }

    @GetMapping("/consolidado-cliente/{clienteId}")
    public ResponseEntity<ResumenConsolidadoClienteDTO> consolidarCliente(
            @PathVariable Long clienteId
    ) {
        try {
            return ResponseEntity.ok(
                    pedidoService.consolidarCliente(clienteId)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .build();
        }
    }
}
