package br.gov.sp.cps.api.pixel.core.usecase;

import br.gov.sp.cps.api.pixel.core.domain.dto.AtualizacaoPlantioDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.PlantacaoDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.CadastrarAtualizacaoPlantioCommand;
import br.gov.sp.cps.api.pixel.core.domain.dto.command.CadastrarPlantacaoCommand;
import br.gov.sp.cps.api.pixel.core.domain.entity.Especie;
import br.gov.sp.cps.api.pixel.core.domain.entity.Fazenda;
import br.gov.sp.cps.api.pixel.core.domain.entity.Plantacao;
import br.gov.sp.cps.api.pixel.core.domain.entity.Usuario;
import br.gov.sp.cps.api.pixel.core.domain.mapper.PlantacaoMapper;
import br.gov.sp.cps.api.pixel.core.domain.repository.EspecieRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.FazendaRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.PlantacaoRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CadastrarPlantacaoUC {

    private final PlantacaoRepository plantacaoRepository;
    private final FazendaRepository fazendaRepository;
    private final EspecieRepository especieRepository;
    private final CadastrarAtualizacaoPlantioUC cadastrarAtualizacaoPlantioUC;
    private final PlantacaoMapper mapper;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public PlantacaoDTO executar(CadastrarPlantacaoCommand command) {
        plantacaoRepository.buscarPorFazenda(command.fazendaNome())
                .ifPresent(existing -> {
                    throw new RuntimeException("Já existe uma plantação cadastrada para a fazenda: " + command.fazendaNome());
                });
        Optional<Usuario> usuario = usuarioRepository.carregar(command.idUsuario());
        Plantacao plantacao = plantacaoRepository.salvar(Plantacao.toEntity(command,usuario.get()));

        AtualizacaoPlantioDTO atualizacaoPlantioDTO = cadastrarAtualizacaoPlantioUC.executar(CadastrarAtualizacaoPlantioCommand
                .toCommand(plantacao.getId(), command.fazendaNome(), command.temperaturaAmbiente(), command.temperaturaSolo(),
                        command.umidadeAmbiente(), command.umidadeSolo(), command.phSolo(), command.indiceUV(), command.custoEsperado()));

        return mapper.toDTO(plantacao, atualizacaoPlantioDTO.id());
    }
}
