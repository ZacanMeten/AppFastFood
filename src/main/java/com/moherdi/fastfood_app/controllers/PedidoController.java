package com.moherdi.fastfood_app.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.moherdi.fastfood_app.DAOs.interfaces.IClienteDAO;
import com.moherdi.fastfood_app.DAOs.interfaces.IPedidoDAO;
import com.moherdi.fastfood_app.DAOs.interfaces.IProductoDAO;
import com.moherdi.fastfood_app.entities.Cliente;
import com.moherdi.fastfood_app.entities.DetallePedido;
import com.moherdi.fastfood_app.entities.Pedido;
import com.moherdi.fastfood_app.entities.Producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/pedido")
@SessionAttributes("pedido")
public class PedidoController {
    @Autowired
    private IClienteDAO repoCliente;

    @Autowired
    private IProductoDAO repoProducto;

    @Autowired
    private IPedidoDAO repoPedido;

    @RequestMapping(value = "/form/{clienteID}")
    public String crearPedido_cliente(@PathVariable(value = "clienteID") Integer clientID, Map<String, Object> map,
            RedirectAttributes flash) {

        Optional<Cliente> cliente = repoCliente.findById(clientID);
        if (cliente == null) {
            flash.addFlashAttribute("error", "El Cliente no existe");
            return "redirect:/";
        }
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente.get());
        map.put("pedido", pedido);
        map.put("titulo", "Realizar Pedido");
        return "pedido/form";
    }

    @GetMapping(value = "/cargar-postre/{termino}", produces = { "application/json" })
    public @ResponseBody List<Producto> cargarProductos(@PathVariable String termino) {
        return repoProducto.findByNombre(termino);
    }

    // Guardar el pedido
    @PostMapping(value = "/form")
    public String guardarPedido(Pedido pedido, @RequestParam(name = "item_id[]", required = false) Integer[] itemId,
            @RequestParam(name = "cantidad[]", required = false) Integer[] cantidad, RedirectAttributes flash,
            SessionStatus status) {
        // Verificar que haya mas de un producto
        if (itemId == null || cantidad == null)
            return "redirect:/catalogo";
        // CAlcular el pedido
        Double precioRela;
        for (int i = 0; i < itemId.length; i++) {
            Producto prod = repoProducto.buscarProducto(itemId[i]);

            DetallePedido linea = new DetallePedido();
            linea.setCantidad(cantidad[i]);

            precioRela = cantidad[i] * prod.getPrecio_unit() * (100 - prod.getDsct_prc()) / 100;

            linea.setProducto(prod);
            linea.setPrecio(precioRela);
            pedido.addDetallePedido(linea);
            pedido.getPrecio_total();
        }
        repoPedido.save(pedido);
        status.setComplete();
        flash.addFlashAttribute("success", "Pedido creado con exito");
        return "redirect:/catalogo";
    }

}
