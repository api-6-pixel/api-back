package br.gov.sp.cps.api.pixel.core.usecase;

import br.gov.sp.cps.api.pixel.core.domain.dto.FazendaDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.CadastrarFazendaCommand;
import br.gov.sp.cps.api.pixel.core.domain.entity.Fazenda;
import br.gov.sp.cps.api.pixel.core.domain.entity.Usuario;
import br.gov.sp.cps.api.pixel.core.domain.mapper.FazendaMapper;
import br.gov.sp.cps.api.pixel.core.domain.repository.FazendaRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CadastrarFazendaUC {

    private final FazendaRepository fazendaRepository;
    private final FazendaMapper fazendaMapper;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public FazendaDTO executar(CadastrarFazendaCommand command){
        Optional<Usuario> usuario = usuarioRepository.carregar(command.idUsuario());
        Fazenda fazenda = Fazenda.toEntity(command, usuario.get());
        Fazenda resultado = fazendaRepository.salvar(fazenda);
        return fazendaMapper.toDTO(resultado);
    }
}
