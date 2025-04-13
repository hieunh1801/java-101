package org.hieunh1801.book;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;

@GraphQLApi
public class BookResource {

    @Query("book")
    public Book getOne(@Name("id") Long id) {
        return Book.findById(id);
    }

    @Query("books")
    public List<Book> getMany(@Name("page") Integer page, @Name("perPage") Integer perPage) {
        return Book.listAll();
    }

    @Transactional
    @Mutation
    public Book createBook(@Valid @Name("book") Book book) {
        book.persist();
        return book;
    }

    @Mutation
    @Transactional
    public Book updateBook(@Valid @Name("book") Book book) {
        book.persist();
        return book;
    }

    @Mutation
    @Transactional
    public Book deleteBook(@Name("id") Integer id) {
        Book book = Book.findById(id);
        if (book != null) {
            book.delete();
            return book;
        }
        return null;
    }
}
