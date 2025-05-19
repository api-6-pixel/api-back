package br.gov.sp.cps.api.pixel.core.usecase;

import br.gov.sp.cps.api.pixel.core.domain.dto.DadosRelatorioDTO;
import br.gov.sp.cps.api.pixel.core.domain.dto.RelatorioDTO;
import br.gov.sp.cps.api.pixel.core.domain.entity.ChaveUsuario;
import br.gov.sp.cps.api.pixel.core.domain.entity.Usuario;
import br.gov.sp.cps.api.pixel.core.domain.mapper.UsuarioMapper;
import br.gov.sp.cps.api.pixel.core.domain.repository.*;
import br.gov.sp.cps.api.pixel.core.service.RelatorioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GerarRelatorioUC {

    private final RelatorioService relatorioService;
    private final UsuarioRepository usuarioRepository;
    private final AtualizacaoPlantioRepository atualizacaoPlantioRepository;
    private final CriptografiaRepository criptografiaRepository;
    private final ChaveUsuarioRepository chaveUsuarioRepository;

    @Transactional
    public RelatorioDTO executar(Long idUsuario, Long idPlantacao) throws IOException {
        Optional<Usuario> usuario = usuarioRepository.carregar(idUsuario);
        if (usuario.isEmpty()) {
            throw new RuntimeException("Nenhum usuário encontrado para o ID informado.");
        }

        Usuario result = usuario.get();

        ChaveUsuario chaveUsuario = chaveUsuarioRepository.carregar(idUsuario);
        if (chaveUsuario == null) {
            throw new RuntimeException("Chave do usuário não encontrada para o ID informado.");
        }

        byte[] decodedKey = Base64.getDecoder().decode(chaveUsuario.getChave());
        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        Usuario usuarioDescriptografado;
        try {
            usuarioDescriptografado = (Usuario) criptografiaRepository.getObjectDescriptografado(result, secretKey);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao descriptografar os dados do usuário.", e);
        }

        String nomeUsuario = usuarioDescriptografado.getNome();

        var plantacaoUsuario = usuario.get().getPlantacao().stream()
                .filter(p -> p.getId().equals(idPlantacao))
                .findFirst();
        if (plantacaoUsuario.isEmpty()) {
            throw new RuntimeException("Plantação não encontrada para o usuário informado.");
        }

        var plantacao = plantacaoUsuario.get();

        List<DadosRelatorioDTO> dadosRelatorio = atualizacaoPlantioRepository.buscarPorPlantacao(plantacao).stream()
                .map(atualizacao -> new DadosRelatorioDTO(
                        nomeUsuario,
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
        String nomeArquivo = relatorioService.gerarNomeArquivo(dadosRelatorio.get(1));

        return new RelatorioDTO(relatorio, nomeArquivo);
    }
}
