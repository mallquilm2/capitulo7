function ventasIns() {
    window.location = "ventasIns.jsp";
}

function ventasDel() {
    var ids = [];

    $("input[name='codigoventaDel']:checked").each(function () {
        ids.push($(this).val());
    });

    if (ids.length === 0) {
        alert("Seleccione fila(s) a Retirar");
    } else {
        if (confirm("¿Retirar Venta(s)?")) {
            window.location = "Ventas?accion=DEL&ids=" + ids.toString();
        }
    }
}

function ventasUpd() {
    var id = $("input[name='codigoventaUpd']:checked").val();

    if (isNaN(id)) {
        alert("Seleccione Fila para Actualizar Datos");
    } else {
        window.location = "Ventas?accion=GET&cod=" + id;
    }
}
