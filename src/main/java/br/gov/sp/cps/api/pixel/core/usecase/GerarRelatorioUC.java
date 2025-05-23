package br.gov.sp.cps.api.pixel.core.usecase;

import br.gov.sp.cps.api.pixel.core.domain.dto.DadosRelatorioDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.RelatorioDTO;
import br.gov.sp.cps.api.pixel.core.domain.entity.ChaveUsuario;
import br.gov.sp.cps.api.pixel.core.domain.entity.Usuario;
import br.gov.sp.cps.api.pixel.core.domain.repository.AtualizacaoPlantioRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.ChaveUsuarioRepository;
import br.gov.sp.cps.api.pixel.core.domain.repository.UsuarioRepository;
import br.gov.sp.cps.api.pixel.core.service.RelatorioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GerarRelatorioUC {

    private final UsuarioRepository usuarioRepository;
    private final AtualizacaoPlantioRepository atualizacaoPlantioRepository;
    private final ChaveUsuarioRepository chaveUsuarioRepository;
    private final RelatorioService relatorioService;

    @Transactional
    public RelatorioDTO executar(Long idUsuario, Long idPlantacao) throws IOException {
        Usuario usuario = usuarioRepository.carregar(idUsuario)
                .orElseThrow(() -> new RuntimeException("Nenhum usuário encontrado para o ID informado."));

        ChaveUsuario chaveUsuario = chaveUsuarioRepository.carregar(idUsuario);
        if (chaveUsuario == null) {
            throw new RuntimeException("Chave do usuário não encontrada para o ID informado.");
        }

        var plantacaoUsuario = usuario.getPlantacao().stream()
                .filter(p -> p.getId().equals(idPlantacao))
                .findFirst();

        if (plantacaoUsuario.isEmpty()) {
            throw new RuntimeException("Plantação não encontrada para o usuário informado.");
        }

        var plantacao = plantacaoUsuario.get();

        List<DadosRelatorioDTO> dadosRelatorio = atualizacaoPlantioRepository.buscarPorPlantacao(plantacao).stream()
                .map(atualizacao -> new DadosRelatorioDTO(
                        plantacao.getFazendaNome(),
                        plantacao.getEspecieNome(),
                        plantacao.getAreaPlantada(),
                        plantacao.getCustoEsperado(),
                        plantacao.getStatus(),
                        plantacao.getDataPlantio(),
                        atualizacao.getTemperaturaAmbiente(),
                        atualizacao.getTemperaturaSolo(),
                        atualizacao.getUmidadeAmbiente(),
                        atualizacao.getUmidadeSolo(),
                        atualizacao.getPhSolo(),
                        atualizacao.getIndiceUV(),
                        atualizacao.getDataRegistro()
                )).toList();

        if (dadosRelatorio.isEmpty()) {
            throw new RuntimeException("Não há dados de atualização para a plantação informada.");
        }

        InputStreamResource relatorio = RelatorioService.exportarDadosLoteUsuario(dadosRelatorio);
        String nomeRelatorio = relatorioService.gerarNomeArquivo(dadosRelatorio.getFirst());

        return new RelatorioDTO(relatorio, nomeRelatorio);
    }
}
