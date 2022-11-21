package br.com.mo.financeiroapi.service.usuario;

import br.com.mo.financeiroapi.controller.InfoController;
import br.com.mo.financeiroapi.mensageria.AtividadesSender;
import br.com.mo.financeiroapi.model.dto.GenericResponseDto;
import br.com.mo.financeiroapi.model.dto.usuario.UsuarioSalvarDto;
import br.com.mo.financeiroapi.model.enums.StatusEnvio;
import br.com.mo.financeiroapi.repository.usuario.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioDAO usuarioDAO;
    private final AtividadesSender atividadesSender;

    @Autowired
    public UsuarioService(AtividadesSender atividadesSender, UsuarioDAO usuarioDAO) {
        this.atividadesSender = atividadesSender;
        this.usuarioDAO = usuarioDAO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public GenericResponseDto salvar(UsuarioSalvarDto pUsuario) throws Exception {
        int idUsuario = pUsuario.getId();

        int idLogin = usuarioDAO.getIdLogin(pUsuario.getId(), pUsuario.getLogin());

        GenericResponseDto oGenericResponse = new GenericResponseDto();

        if (idLogin > 0) {
            oGenericResponse.setStatus(StatusEnvio.FALHA.getId());
            oGenericResponse.setMensagem("Login já cadastrado para o usuário " + idLogin + "!");

        } else {
            if (pUsuario.getId() > 0) {
                usuarioDAO.alteraUsuario(pUsuario);
                
                atividadesSender.sendInfo(UsuarioService.class, "Usuário " + pUsuario.getId() + " alterado.");

            } else {
                idUsuario = usuarioDAO.inserirUsuario(pUsuario);
                
                atividadesSender.sendInfo(UsuarioService.class, "Usuário " + idUsuario + " cadastrado.");
            }

            oGenericResponse.setStatus(StatusEnvio.SUCESSO.getId());
            oGenericResponse.setMensagem(String.valueOf(idUsuario));
        }

        return oGenericResponse;
    }

}
