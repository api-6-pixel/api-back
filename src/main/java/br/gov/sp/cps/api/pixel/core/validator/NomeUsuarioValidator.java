package br.gov.sp.cps.api.pixel.core.validator;

import org.springframework.stereotype.Component;

@Component
public class NomeUsuarioValidator {

    public boolean isUsernameValid(String nomeCompleto, String username) {
        String[] partes = nomeCompleto.toLowerCase().split("\\s+");
        StringBuilder iniciais = new StringBuilder();

        for (String parte : partes) {
            if (!parte.isEmpty()) {
                iniciais.append(parte.charAt(0));
            }
        }

        // Monta regex: começa com as iniciais, seguido de qualquer combinação de letras/números
        String regex = "^" + iniciais.toString() + "[a-z0-9]*$";

        return username.toLowerCase().matches(regex);
    }

    public static void main(String[] args) {
        NomeUsuarioValidator nomeUsuarioValidator = new NomeUsuarioValidator();
        String nome = "Wagner de Deus Silva Junior";
        System.out.println(nomeUsuarioValidator.isUsernameValid(nome, "wagner.silva"));
        System.out.println(nomeUsuarioValidator.isUsernameValid(nome, "wagner.junior"));
        System.out.println(nomeUsuarioValidator.isUsernameValid(nome, "wagner.deus"));
        System.out.println(nomeUsuarioValidator.isUsernameValid(nome, "w.deus"));
        System.out.println(nomeUsuarioValidator.isUsernameValid(nome, "wagne.deus"));
        System.out.println(nomeUsuarioValidator.isUsernameValid(nome, "wddsj"));
    }
}
