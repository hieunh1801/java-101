package org.hieunh1801;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;


@GraphQLApi
public class HelloGraphQLResource {

    @Query
    public String ping() {
        return "pong";
    }

    @Query
    public String serverTime(){
        return LocalDateTime.now().toString();
    }
}