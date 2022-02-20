package br.com.mo.financeiroapi.controller.usuario;

import br.com.mo.financeiroapi.model.dto.GenericResponseDto;
import br.com.mo.financeiroapi.model.dto.usuario.UsuarioSalvarDto;
import br.com.mo.financeiroapi.service.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping()
    @RequestMapping("/salvar")
    public ResponseEntity salvar(@RequestBody UsuarioSalvarDto pUsuario) throws Exception {
        try {
            GenericResponseDto oGenericResponse = usuarioService.salvar(pUsuario);

            return new ResponseEntity(oGenericResponse, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
