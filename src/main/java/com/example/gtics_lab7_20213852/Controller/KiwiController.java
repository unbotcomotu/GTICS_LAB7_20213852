package com.example.gtics_lab7_20213852.Controller;

import com.example.gtics_lab7_20213852.Entity.Resource;
import com.example.gtics_lab7_20213852.Entity.User;
import com.example.gtics_lab7_20213852.Repository.ResourceRepository;
import com.example.gtics_lab7_20213852.Repository.UserRepository;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class KiwiController {

    private final UserRepository userRepository;
    private final ResourceRepository resourceRepository;

    public KiwiController(UserRepository userRepository,
                          ResourceRepository resourceRepository) {
        this.userRepository = userRepository;
        this.resourceRepository = resourceRepository;
    }

    @PostMapping("/crearContador")
    public ResponseEntity<HashMap<String, Object>> crearContador(@RequestBody Map<String,Object> datos){
        String name=(String) datos.get("name");
        HashMap<String,Object>response=new HashMap<>();
        HashMap<String,Object>errors=new HashMap<>();
        if(name==null||name.isEmpty()){
            response.put("status","error");
            errors.put("name","Ingrese un nombre");
        }else if(name.length()>100){
            response.put("status","error");
            errors.put("name","El nombre no puede tener más de 100 caracteres");
        }
        if(response.get("status")==null) {
            User user = new User();
            Resource resource=new Resource();
            resource.setId(5);
            user.setName(name);
            user.setType("Contador");
            user.setAuthorizedResource(resource);
            user.setActive(true);
            userRepository.save(user);
            response.put("status", "success");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }else {
            response.put("errors",errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/listarAutorizaciones")
    public ResponseEntity<HashMap<String, Object>> listarAutorizaciones(@RequestParam(value = "recurso",required = false)String idRecursoStr){
        HashMap<String,Object>response=new HashMap<>();
        HashMap<String,Object>errors=new HashMap<>();
        Integer idRecurso=null;
        if(idRecursoStr==null){
            response.put("status","error");
            errors.put("resource","Ingrese un ID para el recurso");
        }else {
            try{
                idRecurso=Integer.parseInt(idRecursoStr);
                if(resourceRepository.verificarRecursoPorId(idRecurso)==null){
                    response.put("status","error");
                    errors.put("resource","El recurso ingresado no existe. Ingrese el ID del recurso correctamente");
                }
            }catch (NumberFormatException ex){
                response.put("status","error");
                errors.put("resource","El ID ingresado debe ser un número");
            }
        }
        if(response.get("status")==null) {
            List<User> listaUsuariosConAutorizacion=userRepository.listarUsuarios(idRecurso);
            response.put("status", "success");
            response.put("lista",listaUsuariosConAutorizacion);
            return ResponseEntity.ok(response);
        }else {
            response.put("errors",errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/eliminarUsuario")
    public ResponseEntity<HashMap<String, Object>> eliminarUsuario(@RequestBody Map<String,Object> datos){
        Object idObj=datos.get("id");
        Integer id=null;
        HashMap<String,Object>response=new HashMap<>();
        HashMap<String,Object>errors=new HashMap<>();
        if(idObj==null){
            response.put("status","error");
            errors.put("id","Ingrese un ID");
        }else if(idObj instanceof String idStr){
            if(idStr.isEmpty()){
                response.put("status","error");
                errors.put("id","Ingrese un ID");
            }else {
                try{
                    id=Integer.parseInt(idStr);
                }catch (NumberFormatException ex){
                    response.put("status","error");
                    errors.put("id","El ID ingresado debe ser un número");
                }
            }
        }else if(idObj instanceof Integer idInt){
            id=idInt;
        }else {
            response.put("status","error");
            errors.put("id","El ID ingresado debe ser un número");
        }
        if(id!=null){
            if(!userRepository.existsById(id)){
                response.put("status","error");
                errors.put("id","El ID ingresado no corresponde a ningún usuario registrado");
            }
        }
        if(response.get("status").equals("success")) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }else {
            response.put("errors",errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/actualizarUsuario")
    public ResponseEntity<HashMap<String, Object>> actualizarUsuario(@RequestBody Map<String,Object> datos){
        Object idObj=datos.get("id");
        String name=(String) datos.get("name");
        Object idRecursoObj=datos.get("recurso");
        Integer id=null;
        Integer idRecurso=null;
        HashMap<String,Object>response=new HashMap<>();
        HashMap<String,Object>errors=new HashMap<>();
        if(idObj==null){
            response.put("status","error");
            errors.put("id","Ingrese un ID");
        }else if(idObj instanceof String idStr){
            if(idStr.isEmpty()){
                response.put("status","error");
                errors.put("id","Ingrese un ID");
            }else {
                try{
                    id=Integer.parseInt(idStr);
                }catch (NumberFormatException ex){
                    response.put("status","error");
                    errors.put("id","El ID ingresado debe ser un número");
                }
            }
        }else if(idObj instanceof Integer idInt){
            id=idInt;
        }else {
            response.put("status","error");
            errors.put("id","El ID ingresado debe ser un número");
        }

        if(idRecursoObj==null){
            response.put("status","error");
            errors.put("resource","Ingrese un ID para el recurso");
        }else if(idRecursoObj instanceof String idRecursoStr){
            if(idRecursoStr.isEmpty()){
                response.put("status","error");
                errors.put("resource","Ingrese un ID para el recurso");
            }else {
                try{
                    idRecurso=Integer.parseInt(idRecursoStr);
                    if(resourceRepository.verificarRecursoPorId(idRecurso)==null){
                        response.put("status","error");
                        errors.put("resource","El recurso ingresado no existe. Ingrese el ID del recurso correctamente");
                    }
                }catch (NumberFormatException ex){
                    response.put("status","error");
                    errors.put("resource","El ID ingresado debe ser un número");
                }
            }
        }else if(idRecursoObj instanceof Integer idRecursoInt){
            idRecurso=idRecursoInt;
        }else {
            response.put("status","error");
            errors.put("resource","El ID ingresado debe ser un número");
        }
        if(name==null||name.isEmpty()){
            response.put("status","error");
            errors.put("name","Ingrese un nombre");
        }else if(name.length()>100){
            response.put("status","error");
            errors.put("name","El nombre no puede tener más de 100 caracteres");
        }
        if(id!=null){
            if(!userRepository.existsById(id)){
                response.put("status","error");
                errors.put("id","El ID ingresado no corresponde a ningún usuario registrado");
            }
        }
        if(response.get("status")==null) {
            response.put("status","success");
            //Debido a que existe una relación implícita entre type y authorizedResource
            String tipo="";
            if(idRecurso==5){
                tipo="Contador";
            }else if(idRecurso==6){
                tipo="Cliente";
            }else if(idRecurso==7){
                tipo="Analista de promociones";
            }else if(idRecurso==8){
                tipo="Analista logístico";
            }
            userRepository.actualizarUsuario(name,tipo,idRecurso,id);
            return ResponseEntity.ok(response);
        }else {
            response.put("errors",errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/apagadoLogico")
    public ResponseEntity<HashMap<String, Object>> apagadoLogico(){
        HashMap<String,Object>response=new HashMap<>();
        HashMap<String,Object>errors=new HashMap<>();
        userRepository.apagadoLogico();
        response.put("status","success");
        return ResponseEntity.ok(response);
    }
}
