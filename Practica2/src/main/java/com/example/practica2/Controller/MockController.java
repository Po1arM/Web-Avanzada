package com.example.practica2.Controller;

import com.example.practica2.entidades.EnumMetodo;
import com.example.practica2.entidades.Mock;
import com.example.practica2.entidades.Proyecto;
import com.example.practica2.entidades.seguridad.Usuario;
import com.example.practica2.servicios.MockServices;
import com.example.practica2.servicios.UsuarioServices;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
public class MockController {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private MockServices mockService;

    @Autowired
    private UsuarioServices usuarioServices;

    //Se usa para guardar una referencia de un proyecto
    private long proyectoID;
    private long userID;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    /*@RequestMapping(path = "/login", method = RequestMethod.POST)
    public String autentificar(Model model, WebRequest request){
        String usuario = request.getParameter("nombre");
        String pass = request.getParameter("pass");

        //Verificar credenciales de usuario, si existe se hace redireccion a index donde se muestran los proyectos del usuario, si no, se vuelve al login
        return "redirect:/verProyectos/"; //+ id del usuario
    }*/
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginPage(@RequestParam Optional<String> error) {
        return new ModelAndView("login", "error", error);
    }

    @RequestMapping("/proyectos")
    public String verProyectos(Model model, @RequestParam String id){
        /*
        List<Proyectos> misProyectos = ProyectoService.buscarProyectosPorUserId(Integer.parseInt(id));
            model.addAttribute("proyects", misProyectos);
        */
        return "index";
    }

    @RequestMapping("/verProyecto")
    public String verEndPoints(Model model, @RequestParam String idProyecto){
        proyectoID = Long.parseLong(idProyecto);
        /*
        List<Mock> mocks = MockService.buscarMocksPorProyectoId(proyectoID);
        model.addAttribute("endpoints", mocks);
         */
        model.addAttribute("action","verEndpoint");
        return "proyecto";
    }
    @RequestMapping("/addEndpoint")
    public String addEndpoint(Model model){
        model.addAttribute("action","addEndpoint");
        return "endPoint";
    }
    @RequestMapping(path = "/addEndpoint", method = RequestMethod.POST)
    public String crearEndpoint(WebRequest request, RedirectAttributes redirectAttributes){
        String ruta = request.getParameter("path");
        String metodo = request.getParameter("verbo");
        String headers = request.getParameter("header");
        int codigo = Integer.parseInt(request.getParameter("status"));
        String conType = request.getParameter("type");
        String cuerpo = request.getParameter("body");
        String descripcion = request.getParameter("descripcion");
        String nombre = request.getParameter("nombre");
        Date expiracion = MockServices.calcularFecha(request.getParameter("exp"));
        int tRespuesta = Integer.parseInt(request.getParameter("time"));
        boolean jwt = Boolean.parseBoolean(request.getParameter("jwt"));

        Mock aux = new Mock(proyectoID,ruta,metodo,headers,codigo,conType,cuerpo,descripcion,nombre,expiracion,tRespuesta,jwt);
        mockService.crear(aux);
        redirectAttributes.addAttribute("idProyecto", proyectoID);
        return "redirect:/verProyecto";
    }

    @RequestMapping("/verEndpoint")
    public String verEndpoint(Model model, @RequestParam String id){
        Mock mock = mockService.buscarPorId(Long.parseLong(id));
        if(mock != null){
            model.addAttribute("endpoint",mock);
            return "endPoint";
        }else{
            return "error";
        }
    }

    @RequestMapping(path = "/verEndpoint", method = RequestMethod.POST)
    public String editarEndpoint(WebRequest request, RedirectAttributes redirectAttributes){
        long id = Long.parseLong(request.getParameter("id"));
        Mock aux = mockService.buscarPorId(id);
        if(aux != null) {
            aux.setRuta(request.getParameter("path"));
            aux.setMetodo(EnumMetodo.valueOf(request.getParameter("verbo")));
            aux.setHeaders(request.getParameter("header"));
            aux.setCodigo(Integer.parseInt(request.getParameter("status")));
            aux.setContype(request.getParameter("type"));
            aux.setCuerpo(request.getParameter("body"));
            aux.setDescripcion(request.getParameter("descripcion"));
            aux.setNombre(request.getParameter("nombre"));
            aux.setExpiracion(MockServices.calcularFecha(request.getParameter("exp")));
            aux.setTiempoRespuesta(Integer.parseInt(request.getParameter("time")));
            aux.setJwt(Boolean.parseBoolean(request.getParameter("jwt")));
            mockService.editar(aux);

        }
        redirectAttributes.addAttribute("idProyecto", proyectoID);
        return "redirect:/verProyecto";
    }

    @RequestMapping("/eliminar")
    public String borrarEndpoint(RedirectAttributes redirectAttributes,@RequestParam String id){
        mockService.eliminar(Long.parseLong(id));
        redirectAttributes.addAttribute("idProyecto", proyectoID);
        return "redirect:/verProyecto";
    }

    @RequestMapping("/usuarios")
    public String verUsuarios(Model model){
        List<Usuario> usuarios = usuarioServices.listar();
        model.addAttribute("users", usuarios);
        return "users";
    }

    @RequestMapping("/verProyectos")
    public String verProyectosByUser(Model model,@RequestParam String userID){
        //List<Proyecto> misProyectos = ProyectoService.getProyectosByUserId(userID);
        //model.addAttribute("proyects",misProyectos);
        return "index";
    }

    @RequestMapping("/addUser")
    public String crearUsuario(Model model){
        model.addAttribute("action","addUser");
        return "addUser";
    }
    /*@RequestMapping(path = "/addUser", method = RequestMethod.POST)
    public String addUsuario(WebRequest request){
        Usuario aux = new Usuario();
        //Usuario aux = new Usuario(username,request.getParameter("pass"), true, nombre, roles);
        aux.setUsername();
        aux.setNombre();
        aux.setPassword(request.getParameter("pass"));
        aux.setActivo(true);
        aux.setRoles();
        String nombre = request.getParameter("nombre");
        String pass = request.getParameter("pass");
        String permiso = request.getParameter("permisos");
        usuarioServices.crearUsuario(aux);
        return "redirect:/usuarios";
    }*/

    @RequestMapping("/modUser")
    public String modUser(Model model,@RequestParam String id){
        //userID = Long.parseLong(id);
        Usuario user = usuarioServices.buscarPorId(id);
        model.addAttribute("user",user);
        model.addAttribute("action","addUser");
        return "addUser";
    }

    @RequestMapping(path = "/modUser", method = RequestMethod.POST)
    public String modUserPost(WebRequest request){
        /*String nombre = request.getParameter("nombre");
        String pass = request.getParameter("pass");
        String permiso = request.getParameter("permisos");

        User aux = new User(nombre, pass, permiso);*/
        //Llamar al servicio para actualizar los usuarios
        return "redirect:/usuarios";
    }

    @RequestMapping("/eliminarUser")
    public String deleteUser(@RequestParam String id){
        //UserService.delete(Long.parseLong(id));
        usuarioServices.borrarUsuario(id);
        return "redirect:/usuarios";
    }

    public ResponseEntity<String> accederEndpoint(Model model,@RequestParam String endpoint){
        Mock aux = mockService.buscarPorId(Long.parseLong(endpoint));
        //verificar que mock != null
        Date auxDate = new Date();
        if(auxDate.before(aux.getExpiracion())){
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.parseMediaType(aux.getContype()));
            httpHeaders = convertStringtoHeaders(httpHeaders, aux.getHeaders());

            return new ResponseEntity<>(aux.getCuerpo(),httpHeaders,aux.getCodigo());
        }
        return new ResponseEntity<>("",null,404);
    }

    private HttpHeaders convertStringtoHeaders(HttpHeaders httpHeaders, String aux) {
        JsonObject jsonObject = new JsonParser().parse(aux).getAsJsonObject();
        Iterator<String> keys = jsonObject.keySet().iterator();

        while(keys.hasNext()) {
            String key = keys.next();
            httpHeaders.set(key, String.valueOf(jsonObject.get(key)));
        }
        return httpHeaders;

    }


}
