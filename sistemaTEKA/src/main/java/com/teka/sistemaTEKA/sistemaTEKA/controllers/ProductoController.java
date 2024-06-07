package com.teka.sistemaTEKA.sistemaTEKA.controllers;

import com.teka.sistemaTEKA.sistemaTEKA.models.Categoria;
import com.teka.sistemaTEKA.sistemaTEKA.models.Producto;
import com.teka.sistemaTEKA.sistemaTEKA.services.CategoriaService;
import com.teka.sistemaTEKA.sistemaTEKA.services.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/productos")
    public String obtenerProductos(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String codigo,
            @RequestParam(required = false) String modelo,
            Model model) {
        List<Producto> productos = productoService.filtrarPorParametros(categoria, codigo, modelo);
        List<String> categorias = productoService.obtenerTodasLasCategorias();

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categorias);
        model.addAttribute("categoriaSeleccionada", categoria);
        model.addAttribute("codigoSeleccionado", codigo);
        model.addAttribute("modeloSeleccionado", modelo);

        model.addAttribute("producto", new Producto()); // Aquí se agrega el producto al modelo
        return "dashboard/productos/lista_productos";
    }

    @GetMapping("/agregarProducto")
    public String mostrarFormularioAgregarProducto(Model model) {
        List<Categoria> categorias = categoriaService.obtenerTodasLasCategorias();
        model.addAttribute("categorias", categorias);
        model.addAttribute("producto", new Producto());
        return "dashboard/productos/agregar_productos";
    }
    @PostMapping("/agregarProducto")
    public String agregarProducto(@Valid @ModelAttribute Producto producto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Categoria> categorias = categoriaService.obtenerTodasLasCategorias();
            model.addAttribute("categorias", categorias);
            return "dashboard/productos/agregar_productos";
        }

        try {
            String codigo = producto.getCodigoProducto();
            String modelo = producto.getModelo();
            Long idCategoria = producto.getCategoria().getIdCategoria();
            productoService.guardarProducto(codigo, modelo, idCategoria);
        } catch (IllegalArgumentException e) {
            // Verificar si la excepción se debe a un código o modelo duplicado
            if (e.getMessage().equals("El código del producto ya existe")) {
                result.rejectValue("codigoProducto", "error.producto", e.getMessage()); // Agregar mensaje de error al campo de código
            } else if (e.getMessage().equals("El modelo del producto ya existe")) {
                result.rejectValue("modelo", "error.producto", e.getMessage()); // Agregar mensaje de error al campo de modelo
            } else {
                // Manejar otras excepciones aquí si es necesario
            }
            List<Categoria> categorias = categoriaService.obtenerTodasLasCategorias();
            model.addAttribute("categorias", categorias);
            return "dashboard/productos/agregar_productos";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "redirect:/productos";
    }


}
