package pe.cibertec.pedidoservicegrupo4.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/pedidos")
@RefreshScope
public class ConfiguracionController {

    @Value("${ecom.impuesto-igv:0.18}")
    private double impuestoIgv;

    @Value("${ecom.monto-minimo-envio-gratis:150.00}")
    private double montoMinimoEnvioGratis;

    @Value("${ecom.mensaje-bienvenida:Bienvenido al servicio de pedidos}")
    private String mensajeBienvenida;

    @Value("${spring.datasource.url:No configurado}")
    private String datasourceUrl;

    @GetMapping("/configuracion")
    public Map<String, Object> obtenerConfiguracion() {
        return Map.of(
                "mensajeBienvenida", mensajeBienvenida,
                "impuestoIgv", impuestoIgv,
                "montoMinimoEnvioGratis", montoMinimoEnvioGratis,
                "datasourceUrl", datasourceUrl
        );
    }
}
