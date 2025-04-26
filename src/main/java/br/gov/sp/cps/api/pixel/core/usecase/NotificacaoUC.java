package br.gov.sp.cps.api.pixel.core.usecase;

import br.gov.sp.cps.api.pixel.core.domain.dto.command.EnviarEmailCommand;
import br.gov.sp.cps.api.pixel.core.domain.entity.Usuario;
import br.gov.sp.cps.api.pixel.core.domain.repository.UsuarioRepository;
import br.gov.sp.cps.api.pixel.outbound.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacaoUC {

    private final EmailService emailService;
    private final UsuarioRepository usuarioRepository;

    public void notificarVazamento(List<String> dadosVazados, String medidas) {
        List<String> emails = usuarioRepository.buscarTodos()
                .stream()
                .map(Usuario::getEmail)
                .filter(email -> email != null && !email.isBlank())
                .toList();

        if (emails.isEmpty()) return;

        String mensagem = "Detectamos um incidente de segurança envolvendo seus dados. " +
                "Os seguintes dados foram comprometidos: " + dadosVazados + ". " +
                "Estamos tomando as seguintes medidas: " + medidas + ". " +
                "Recomendamos que você tome precauções, como a troca de senhas e o monitoramento de suas contas.";

        EnviarEmailCommand emailCommand = new EnviarEmailCommand(
                emails,
                mensagem,
                "Notificação de Vazamento de Dados",
                null
        );

        emailService.sendSimpleMail(emailCommand);
    }
}
