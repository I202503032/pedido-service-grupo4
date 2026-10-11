
package pe.cibertec.pedidoservicegrupo4.client;

import pe.cibertec.pedidoservicegrupo4.dto.PagoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "PAGO-SERVICE-GRUPO4")
public interface PagoClient {

    @GetMapping("/api/v1/pagos/pedido/{idPedido}")
    PagoDTO obtenerPagoPorPedido(
            @PathVariable("idPedido") Long idPedido
    );

    @GetMapping("/api/v1/pagos/cliente/{clienteId}")
    List<PagoDTO> obtenerPagosPorCliente(
            @PathVariable("clienteId") Long clienteId
    );
}
