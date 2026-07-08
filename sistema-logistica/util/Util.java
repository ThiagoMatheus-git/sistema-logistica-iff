package br.edu.ifsul.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Util {

    public static String getMensagemErro(Exception e) {
        while (e.getCause() != null) {
            e = (Exception) e.getCause();
        }
        String retorno = e.getMessage();
        if (retorno.contains("violates foreign key")) {
            retorno = "Registro não pode ser removido por possuir referências em outros registros.";
        }
        return retorno;
    }

    public static void mensagemInformacao(String msg) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void mensagemErro(String msg) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static String formatarCPF(String cpf) {
        if (cpf == null || cpf.length() != 11) return cpf;
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + 
               cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
    }
}
