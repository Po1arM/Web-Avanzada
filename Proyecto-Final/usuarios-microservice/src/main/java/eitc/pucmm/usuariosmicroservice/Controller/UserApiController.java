package eitc.pucmm.usuariosmicroservice.Controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import eitc.pucmm.usuariosmicroservice.entidades.EnumPermiso;
import eitc.pucmm.usuariosmicroservice.entidades.Usuario;
import eitc.pucmm.usuariosmicroservice.repository.UserRepository;

@RestController
public class UserApiController {
    
    @Autowired
    UserRepository userRepository;

    @GetMapping("/usuarios")
    List<Usuario> getUsuarios(){
        return userRepository.findAll();
    }

    @PostMapping("/usuarios")
    Usuario registrUsuario(@RequestBody String usuario) throws IOException{
        Usuario aux =  new Gson().fromJson(usuario, Usuario.class);
        aux.setPassword(encryp(aux.getNombre()+aux.getTelefono().substring(8, 11)));
        userRepository.save(aux);
        //Agregar llamada asincrona al mail-microservice para confirmar registro

        URL url = new URL("http://mail-microservice:8080/notificacion/registro");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("body", new Gson().toJson(aux));
        con.connect();
        return aux;
    }

    @PutMapping("/usuarios")
    public boolean uptdateUsuario(@RequestBody String user){
        try {
            Usuario aux =  new Gson().fromJson(user, Usuario.class);
            userRepository.save(aux);
            return true;
        } catch (Exception e) {
            return false;
        } 
    }

    @DeleteMapping("usuarios/{id}")
    public boolean deleteUsuario(@PathVariable Long id){
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @GetMapping("/usuarios/{id}")
    public Usuario getUsuarioById(@PathVariable Long id){
        Usuario usuario = userRepository.getById(id);
        return usuario;
    }

    
    @PostMapping("/usuarios/aut")
    public Usuario autentificacion(@RequestBody String loginData){
        JsonObject json = new Gson().fromJson(loginData, JsonObject.class);
        Usuario user = userRepository.findByCorreo(json.get("correo").getAsString());
        if (user!= null){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  
            if(encoder.matches(json.get("password").getAsString(), user.getPassword())){
                return user;
            }
        }
        return null;
    }

    @PostMapping("/usuarios/tipo/{permiso}")
    public List<String> getUsuariosByPermiso(@PathVariable String permiso){
        EnumPermiso enumPermiso = EnumPermiso.valueOf(permiso);
        return userRepository.findCorreoByPermiso(enumPermiso);
    }
    

    private String encryp(String string) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(string);
    }

}
