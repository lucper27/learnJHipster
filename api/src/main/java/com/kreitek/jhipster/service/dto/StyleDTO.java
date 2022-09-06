package com.kreitek.jhipster.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.kreitek.jhipster.domain.Style} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StyleDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StyleDTO)) {
            return false;
        }

        StyleDTO styleDTO = (StyleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, styleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StyleDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
