package org.hieunh1801.book;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Entity
public class Book extends PanacheEntity {
    @Size(min = 3, message = "Title must be at least 3 characters")
    public String title;
    @Min(value = 3, message = "Required")
    public String author;
    public String description;
}
