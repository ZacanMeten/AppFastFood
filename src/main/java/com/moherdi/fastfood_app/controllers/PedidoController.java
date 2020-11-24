package com.moherdi.fastfood_app.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.moherdi.fastfood_app.DAOs.interfaces.IClienteDAO;
import com.moherdi.fastfood_app.DAOs.interfaces.IPedidoDAO;
import com.moherdi.fastfood_app.DAOs.interfaces.IProductoDAO;
import com.moherdi.fastfood_app.DAOs.interfaces.IStaffDAO;
import com.moherdi.fastfood_app.DAOs.interfaces.IUsuarioRepo;
import com.moherdi.fastfood_app.entities.Cliente;
import com.moherdi.fastfood_app.entities.DetallePedido;
import com.moherdi.fastfood_app.entities.Pedido;
import com.moherdi.fastfood_app.entities.Producto;
import com.moherdi.fastfood_app.entities.Staff;
import com.moherdi.fastfood_app.services.UserActualService;

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
    private IStaffDAO repoStaff;

    @Autowired
    private IUsuarioRepo repoUsuario;

    @Autowired
    private IProductoDAO repoProducto;

    @Autowired
    private IPedidoDAO repoPedido;

    @Autowired
    private UserActualService actualUs;

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
        map.put("cliente", repoCliente.findByUser(repoUsuario.findByNombre(actualUs.obtener_Nombre())));
        return "pedido/form";
    }

    @GetMapping(value = "/cargar-postre/{termino}", produces = { "application/json" })
    public @ResponseBody List<Producto> cargarProductos(@PathVariable String termino) {
        return repoProducto.findByNombre(termino);
    }

    // Listar PEdidos
    @GetMapping(value = "/lista")
    public String verPedidos(Map<String, Object> map) {
        List<Pedido> pedidos = repoPedido.findAll();
        if (pedidos.isEmpty()) {
            return "redirect:/inicio";
        }
        map.put("pedidos", pedidos);
        map.put("titulo", "Pedidos");
        map.put("username", actualUs.obtener_Nombre());
        return "pedido/ver_staff";
    }

    // Listar PEdidos
    @GetMapping(value = "/lista/{cliente}")
    public String verPedidosxCliente(@PathVariable(value = "cliente") int id, Map<String, Object> map) {
        Optional<Cliente> c = repoCliente.findById(id);
        List<Pedido> pedidos = repoPedido.findByCliente(c.get());
        if (pedidos.isEmpty()) {
            return "redirect:/inicio";
        }
        map.put("pedidos", pedidos);
        map.put("titulo", "Pedidos de " + actualUs.obtener_nombresC());
        map.put("cliente", repoCliente.findByUser(actualUs.obtener_Usuario()));
        return "pedido/ver_cliente";
    }

    // Buscar Pedido ID
    @GetMapping(value = "/buscar/{id}")
    public String verPedido(@PathVariable(value = "id") int id, Map<String, Object> model) {

        Optional<Pedido> pedido = repoPedido.findById(id);
        if (!pedido.isPresent()) {
            return "redirect:/";
        }
        model.put("titulo", "Pedido N° " + id);
        model.put("pedido", pedido.get());
        model.put("username", actualUs.obtener_Nombre());
        return "pedido/pedidoID";
    }

    // Buscar Pedido para el Staff
    @GetMapping(value = "/setEstado/{id}")
    public String verPedidoStaff(@PathVariable(value = "id") int id, Map<String, Object> model) {
        Optional<Pedido> pedido = repoPedido.findById(id);
        if (!pedido.isPresent()) {
            return "redirect:/";
        }
        if (pedido.get().getStaff() == null) {
            List<Staff> repartidores = repoStaff.findByRol("Repartidor");
            model.put("repartidores", repartidores);
        }
        model.put("titulo", "Pedido N° " + id);
        model.put("pedido", pedido.get());
        model.put("estate", pedido.get().getEstado());
        model.put("username", actualUs.obtener_Nombre());
        return "pedido/ped_staff_es";
    }

    // Actualizar el Estado del Pedido
    @PostMapping(value = "/setEstado")
    public String actualizarEstado(@RequestParam(name = "id_pedido") int id,
            @RequestParam(name = "estado") String estado,
            @RequestParam(name = "staff", required = false) Integer idStaff) {
        // Buscar el pedidoID
        Optional<Pedido> pedido = repoPedido.findById(id);
        Pedido pedido2 = pedido.get();
        if (pedido2 == null) {
            return "redirect:/pedido/buscar/id";
        }
        // Si el repartidor no es un vacio, se actualiza con el nuevo repartidor
        // asignado
        if (idStaff != null)
            pedido2.setStaff(repoStaff.findById(idStaff).get());

        pedido2.setEstado(estado); // Actualiza el estado
        repoPedido.save(pedido2); // Guarda el cambio
        return "redirect:/pedido/lista";
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
