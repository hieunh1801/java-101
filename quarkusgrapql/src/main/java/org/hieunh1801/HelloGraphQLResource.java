package org.hieunh1801;

import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.hieunh1801.model.Book;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.logging.Logger;

@GraphQLApi
public class HelloGraphQLResource {


    private static final org.slf4j.Logger log = LoggerFactory.getLogger(HelloGraphQLResource.class);

    @Query
    List<Book> getBooks(){
        return
    }
}