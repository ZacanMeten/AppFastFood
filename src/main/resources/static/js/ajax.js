$(document).ready(function () {
  $("#buscar_producto").autocomplete({
    source: function (request, response) {
      $.ajax({
        url: "/pedido/cargar-postre/" + request.term,
        dataType: "json",
        data: {
          term: request.term,
        },
        success: function (data) {
          response(
            $.map(data, function (item) {
              return {
                value: item.id_producto,
                label: item.nombre,
                precio: item.precio_unit,
                descuento: item.dsct_prc,
              };
            })
          );
        },
      });
    },
    select: function (event, ui) {
      if (itemsHelper.existeProducto(ui.item.value)) {
        itemsHelper.incrementarCantidad(ui.item.value, ui.item.precio);
        return false;
      }
      /*$('#buscar_producto').val(ui.item.label);*/
      var linea = $("#plantillaItemsPedido").html();
      linea = linea.replace(/{ID}/g, ui.item.value);
      linea = linea.replace(/{NOMBRE}/g, ui.item.label);
      linea = linea.replace(/{PRECIO}/g, ui.item.precio);
      linea = linea.replace(/{DSCT}/g, ui.item.descuento);
      $("#cargarItemProductos").append(linea);
      itemsHelper.calcularImporte(
        ui.item.value,
        ui.item.precio,
        ui.item.descuento,
        1
      );
      return false;
    },
  });
});

$("form").submit(function () {
  debugger;
  $("#plantillaItemsPedido").remove();
  return;
});
var itemsHelper = {
  calcularImporte: function (id, precio, descuento, cantidad) {
    $("#total_importe_" + id).html(
      precio * cantidad * ((100 - descuento) / 100)
    );
    this.calcularTotal();
  },

  existeProducto: function (id) {
    var resultado = false;
    $('input[name="item_id[]"]').each(function () {
      if (parseInt(id) == parseInt($(this).val())) {
        resultado = true;
      }
    });
    return resultado;
  },

  incrementarCantidad: function (id, precio) {
    var cantidad = $("#cantidad_" + id).val()
      ? parseInt($("#cantidad_" + id).val())
      : 0;
    $("#cantidad_" + id).val(++cantidad);
    this.calcularImporte(id, precio, cantidad);
  },

  eliminarLineaFactura: function (id) {
    $("#row_" + id).remove();
    this.calcularTotal();
  },

  calcularTotal: function () {
    var total = 0;
    $('span[id^="total_importe_"]').each(function () {
      total += parseFloat($(this).html());
    });
    $("#total").html(total);
  },
};
