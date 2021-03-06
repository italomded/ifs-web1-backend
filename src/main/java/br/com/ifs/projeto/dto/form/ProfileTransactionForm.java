package br.com.ifs.projeto.dto.form;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileTransactionForm {

	@NotNull
	private Long profileId;
	
	@NotNull
	private Long transactionId;
	
}
