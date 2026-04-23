package br.com.ativa.ocorrencias.model.entity;

import org.hibernate.annotations.ColumnDefault;

import br.com.ativa.ocorrencias.model.enums.Retorno;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Ocorrencia {
    @Id
    private String key;
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'Aguardando'")
    private Retorno retorno;
}
