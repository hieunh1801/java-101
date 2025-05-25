package org.hieunh1801.post;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
public class Post extends PanacheEntityBase {
    @Id
    @GeneratedValue
    public Long id;

    public String name;
    public String email;
}
