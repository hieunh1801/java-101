package org.hieunh1801.post;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostResource {
    @GET
    public List<Post> getMany() {
        return Post.listAll();
    }

    @GET
    @Path("/{id}")
    public Post getOne(@PathParam("id") Long id) {
        return Post.findById(id);
    }

    @POST
    public Post create(Post post) {
        post.persist();
        return post;
    }
}
