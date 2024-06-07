$(document).ready(function() {
    function filtrarProductos() {
        $.ajax({
            url: '/productos',
            type: 'GET',
            data: $('#filtroForm').serialize(),
            success: function(data) {
                $('#tablaProductos').html($(data).find('#tablaProductos').html());
            }
        });
    }

    $('#categoria, #codigoProducto, #modelo').on('input change', function() {
        filtrarProductos();
    });
});