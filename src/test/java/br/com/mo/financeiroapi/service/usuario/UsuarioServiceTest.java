package br.com.mo.financeiroapi.service.usuario;

import br.com.mo.financeiroapi.model.dto.GenericResponseDto;
import br.com.mo.financeiroapi.model.dto.usuario.UsuarioSalvarDto;
import br.com.mo.financeiroapi.model.enums.SituacaoCadastro;
import br.com.mo.financeiroapi.model.enums.StatusEnvio;
import br.com.mo.financeiroapi.repository.usuario.UsuarioDAO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UsuarioServiceTest {

    @Test
    public void testSalvar() throws Exception {
        UsuarioDAO usuarioDAO = Mockito.mock(UsuarioDAO.class);

        Mockito.when(usuarioDAO.getIdLogin(Mockito.anyInt(), Mockito.anyString())).thenReturn(0);
        Mockito.when(usuarioDAO.inserirUsuario(Mockito.any())).thenReturn(1);

        UsuarioSalvarDto oUsuario = new UsuarioSalvarDto();
        oUsuario.setLogin("MARCUS");
        oUsuario.setNome("MARCUS ORNAGUI");
        oUsuario.setEmail("MARCUSORNAGUI@GMAIL.COM");
        oUsuario.setSenha("123456");
        oUsuario.setIdSituacaoCadastro(SituacaoCadastro.ATIVO.getId());

        UsuarioService instance = new UsuarioService(usuarioDAO);
        GenericResponseDto retorno = instance.salvar(oUsuario);

        GenericResponseDto oRetornoEsperado = new GenericResponseDto();
        oRetornoEsperado.setStatus(StatusEnvio.SUCESSO.getId());
        oRetornoEsperado.setMensagem("1");

        assertEquals(oRetornoEsperado, retorno);
    }

    @Test
    public void testSalvar2() throws Exception {
        UsuarioDAO usuarioDAO = Mockito.mock(UsuarioDAO.class);

        Mockito.when(usuarioDAO.getIdLogin(Mockito.anyInt(), Mockito.anyString())).thenReturn(1);

        UsuarioSalvarDto oUsuario = new UsuarioSalvarDto();
        oUsuario.setLogin("MARCUS");
        oUsuario.setNome("MARCUS ORNAGUI");
        oUsuario.setEmail("MARCUSORNAGUI@GMAIL.COM");
        oUsuario.setSenha("123456");
        oUsuario.setIdSituacaoCadastro(SituacaoCadastro.ATIVO.getId());

        UsuarioService instance = new UsuarioService(usuarioDAO);
        GenericResponseDto retorno = instance.salvar(oUsuario);

        GenericResponseDto oRetornoEsperado = new GenericResponseDto();
        oRetornoEsperado.setStatus(StatusEnvio.FALHA.getId());
        oRetornoEsperado.setMensagem("Login já cadastrado para o usuário 1!");

        assertEquals(oRetornoEsperado, retorno);
    }

    @Test
    public void testSalvar3() throws Exception {
        UsuarioDAO usuarioDAO = Mockito.mock(UsuarioDAO.class);

        Mockito.when(usuarioDAO.getIdLogin(Mockito.anyInt(), Mockito.anyString())).thenReturn(0);

        UsuarioSalvarDto oUsuario = new UsuarioSalvarDto();
        oUsuario.setId(1);
        oUsuario.setLogin("MARCUS");
        oUsuario.setNome("MARCUS ORNAGUI");
        oUsuario.setEmail("MARCUSORNAGUI@GMAIL.COM");
        oUsuario.setSenha("123456");
        oUsuario.setIdSituacaoCadastro(SituacaoCadastro.ATIVO.getId());

        UsuarioService instance = new UsuarioService(usuarioDAO);
        GenericResponseDto retorno = instance.salvar(oUsuario);

        GenericResponseDto oRetornoEsperado = new GenericResponseDto();
        oRetornoEsperado.setStatus(StatusEnvio.SUCESSO.getId());
        oRetornoEsperado.setMensagem("1");

        Mockito.verify(usuarioDAO, Mockito.times(1)).alteraUsuario(Mockito.any());
    }

}
