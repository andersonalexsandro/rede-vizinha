package com.github.redevizinha.models.group.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class GroupUpdateRequest {
    @Size(max = 500, message = "A descrição pode ter no máximo 500 caracteres")
    private String description;

    private List<@Size(min = 1, max = 40) String> tags;
}
