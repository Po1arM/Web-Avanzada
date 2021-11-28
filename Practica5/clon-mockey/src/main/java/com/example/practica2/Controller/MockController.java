package com.example.practica2.Controller;

import com.example.practica2.entidades.EnumMetodo;
import com.example.practica2.entidades.Mock;
import com.example.practica2.entidades.Proyecto;
import com.example.practica2.entidades.seguridad.Rol;
import com.example.practica2.entidades.seguridad.Usuario;
import com.example.practica2.repositorio.seguridad.MockRepository;
import com.example.practica2.repositorio.seguridad.ProyectoRepository;
import com.example.practica2.servicios.MockServices;

import com.example.practica2.servicios.UsuarioServices;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hazelcast.config.Config;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.session.hazelcast.HazelcastIndexedSessionRepository;
import org.springframework.session.hazelcast.PrincipalNameExtractor;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Value;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


import java.util.*;

@Controller
public class MockController {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private MockRepository mockRepository;

    @Autowired
    private UsuarioServices usuarioServices;

    @Autowired
    private ProyectoRepository proyectoRepository;
    //Se usa para guardar una referencia de un proyecto
    private long proyectoID;


    @Value("${server.port}")
    private String puerto;

    @RequestMapping("/")
    public String index(Model model, HttpServletResponse response){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
             username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        response.addHeader("server-port",puerto);

        model.addAttribute("username",username);
        List<Proyecto> proyectos = proyectoRepository.findAllByUsername(username);
        model.addAttribute("proyects",proyectos);
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(HttpServletResponse response) {
        
        response.addHeader("server-port", puerto);

        return "login";
    }
    
    @RequestMapping("addProyecto")
    public String addProyecto(Model model, @RequestParam String username,RedirectAttributes redirectAttributes){
        Proyecto proyecto = new Proyecto();
        proyecto.setUsername(username);
        proyecto.setEndoPoints(0);
        System.out.println(proyecto.getUsername());
        proyecto = proyectoRepository.save(proyecto);

        redirectAttributes.addAttribute("username",username);

        return "redirect:/proyectos";
    }
    @RequestMapping("/proyectos")
    public String verProyectos(Model model){
        return "redirect:/";
    }

    @RequestMapping("/verProyecto")
    public String verEndPoints(Model model, @RequestParam String idProyecto){
        proyectoID = Long.parseLong(idProyecto);

        List<Mock> mocks = mockRepository.findAllByIdProyecto(proyectoID);
        model.addAttribute("endpoints", mocks);
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
        Mock aux = new Mock();

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
        aux.setIdProyecto(proyectoID);
        System.out.println(proyectoID);
        aux = mockRepository.save(aux);

        Proyecto au1 = proyectoRepository.getById(proyectoID);
        au1.setEndoPoints(au1.getEndoPoints() + 1);
        proyectoRepository.save(au1);
        redirectAttributes.addAttribute("idProyecto", proyectoID);
        return "redirect:/verProyecto";
    }

    @RequestMapping("/verEndpoint")
    public String verEndpoint(Model model, @RequestParam String id){
        Mock mock = mockRepository.findById(Long.parseLong(id));
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
        Mock aux = mockRepository.findById(id);
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
            mockRepository.save(aux);

        }
        redirectAttributes.addAttribute("idProyecto", proyectoID);
        return "redirect:/verProyecto";
    }

    @RequestMapping("/eliminarEndpoint")
    public String borrarEndpoint(RedirectAttributes redirectAttributes,@RequestParam String id){
        mockRepository.deleteById(Long.parseLong(id));
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
    public String verProyectosByUser(Model model,@RequestParam String username){
        List<Proyecto> misProyectos = proyectoRepository.findAllByUsername(username);
        model.addAttribute("proyects",misProyectos);
        return "index";
    }

    @RequestMapping("/addUser")
    public String crearUsuario(Model model){
        model.addAttribute("action","addUser");
        return "addUser";
    }

    @RequestMapping(path = "/addUser", method = RequestMethod.POST)
    public String addUsuario(WebRequest request){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Usuario aux = new Usuario();

        String permiso = request.getParameter("permisos");

        aux.setUsername(request.getParameter("nombre"));
        aux.setNombre(request.getParameter("nombre"));
        aux.setPassword(bCryptPasswordEncoder.encode(request.getParameter("pass")));
        aux.setActivo(true);
        Rol rol = new Rol(permiso);
        aux.setRoles(new HashSet<>(Arrays.asList(rol)));

        usuarioServices.crearUsuario(aux);

        return "redirect:/usuarios";
    }

    @RequestMapping("/modUser")
    public String modUser(Model model,@RequestParam String id){
        //userID = Long.parseLong(id);
        Usuario user = usuarioServices.buscarPorId(id);
        model.addAttribute("user",user);
        model.addAttribute("action","addUser");
        return "addUser";
    }

    /*@RequestMapping(path = "/modUser", method = RequestMethod.POST)
    public String modUserPost(WebRequest request){
        String nombre = request.getParameter("nombre");
        String pass = request.getParameter("pass");
        String permiso = request.getParameter("permisos");

        Usuario aux = new Usuario(nombre, pass, permiso);
        //Llamar al servicio para actualizar los usuarios
        return "redirect:/usuarios";
    }*/

    @RequestMapping("/eliminarUser")
    public String deleteUser(@RequestParam String id){
        //UserService.delete(Long.parseLong(id));
        usuarioServices.borrarUsuario(id);
        return "redirect:/usuarios";
    }

    @RequestMapping("/accederEndpoint")
    public ResponseEntity<String> accederEndpoint(Model model,@RequestParam String id){
        Mock aux = mockRepository.findById(Long.parseLong(id));
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
