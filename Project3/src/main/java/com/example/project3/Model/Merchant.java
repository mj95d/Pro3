package com.example.project3.Model;


@AllArgsConstructor
@Data
public class Merchant{
    @NotEmpty(message = "id must not be empty")
    @Length(min = 10, message = "id have to be 10 character long")
    private String id;

    @NotEmpty(message = "name must not be empty")
    @Size(min = 10, message = "name have to be 10 character long")
    private String name;
}
