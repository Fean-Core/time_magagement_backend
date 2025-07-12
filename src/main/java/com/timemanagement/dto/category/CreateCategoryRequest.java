package com.timemanagement.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateCategoryRequest {
    
    @NotBlank(message = "Nome da categoria é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String name;
    
    @Size(max = 7, message = "Cor deve ter no máximo 7 caracteres")
    private String color;
    
    @Size(max = 50, message = "Ícone deve ter no máximo 50 caracteres")
    private String icon;
    
    // Default constructor
    public CreateCategoryRequest() {}
    
    // Constructor with parameters
    public CreateCategoryRequest(String name, String color, String icon) {
        this.name = name;
        this.color = color;
        this.icon = icon;
    }
    
    // Getters and setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
}
