package br.com.zup.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErroDeFormularioDTO {

	private String campo;

	private String erro;

	public ErroDeFormularioDTO(String erro) {
		super();
		this.erro = erro;
	}

	public ErroDeFormularioDTO(String campo, String erro) {
		super();
		this.campo = campo;
		this.erro = erro;
	}

    public String getCampo() {
		return campo;
	}

	public String getErro() {
		return erro;
	}

}
