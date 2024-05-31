package ru.maxima.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private Long id;

    @NotEmpty(message = "title should not be empty")
    @Size(min = 2, max = 50, message = "title should be min 2 symbols and max 50 symbols")
    private String title;

    @NotEmpty(message = "Name of author should not be empty")
    @Size(min = 2, max = 50, message = "Name of author should be min 2 symbols and max 50 symbols")
    private String author;

    @Min(value = 1900, message = "year should be min 1900 years")
    private Integer year;
}
