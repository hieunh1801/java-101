package org.hieunh1801.post;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostResource {
    private static final Logger log = LoggerFactory.getLogger(PostResource.class);
    @Inject
    PostService postService;
    @Inject
    private PostRepository postRepository;

    @GET
    public List<Post> getMany() {
        return Post.listAll();
    }

    @GET
    @Path("/fake")
    public String fake(@QueryParam("count") int count, @QueryParam("batchSize") int batchSize) {
        Instant startTime = Instant.now();
        postService.fake(count, batchSize);
        Instant endTime = Instant.now();
        Duration executedTime = Duration.between(startTime, endTime);
        log.info("executed time: {}ms", executedTime.toMillis());
        int total = count * batchSize;
        // Executed 10000 in 4133 ms
        // Executed 100000 in 40484 ms
        return String.format("Executed %d(count=%d, batchSize=%d) in %d ms",
                total, count, batchSize, executedTime.toMillis());
    }

    @GET
    @Path("/fakethreads")
    public String fakeThreads(@QueryParam("count") int count, @QueryParam("batchSize") int batchSize) {
        Instant startTime = Instant.now();
        postService.fakeThreads(count, batchSize);
        Instant endTime = Instant.now();
        Duration executedTime = Duration.between(startTime, endTime);
        log.info("executed time: {}ms", executedTime.toMillis());
        // Executed 10000(count=10, batchSize=1000) in 1791 ms
        // Executed 100000(count=100, batchSize=1000) in 13417 ms
        // Executed 100000(count=100, batchSize=1000) in 13417 ms
        // Executed 100000(count=100, batchSize=1000) in 15136 ms // 5 threads
        // Executed 100000(count=100, batchSize=1000) in 9170 ms // 10 threads
        // Executed 100000(count=100, batchSize=1000) in 8389 ms // 20 threads
        // Executed 200000(count=200, batchSize=1000) in 14871 ms // 20 threads
        // Executed 500000(count=500, batchSize=1000) in 34871 ms // 20 threads
        // Executed 500000(count=500, batchSize=1000) in 26980 ms // 20 threads
        int total = count * batchSize;
        return String.format("Executed %d(count=%d, batchSize=%d) in %d ms",
                total, count, batchSize, executedTime.toMillis());
    }


    @GET
    @Path("/fakevirtualthreads")
    public String fakeVirtualThreads(@QueryParam("count") int count, @QueryParam("batchSize") int batchSize) {
        Instant startTime = Instant.now();
        postService.fakeVirtualThreads(count, batchSize);
        Instant endTime = Instant.now();
        Duration executedTime = Duration.between(startTime, endTime);
        log.info("executed time: {}ms", executedTime.toMillis());
        int total = count * batchSize;
        return String.format("Executed %d(count=%d, batchSize=%d) in %d ms",
                total, count, batchSize, executedTime.toMillis());
    }


    @GET
    @Path("/{id}")
    @Transactional
    public Post getOne(@PathParam("id") Long id) {
        return Post.findById(id);
    }

    @POST
    @Transactional
    public Post create(Post post) {
        post.persist();
        return post;
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Post update(@PathParam("id") Long id, Post post) {
        Post existingPost = Post.findById(id);
        if (existingPost == null) {
            post.persist();
            return post;
        }
        existingPost.email = post.email;
        existingPost.name = post.name;
        existingPost.persist();
        return post;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Post delete(@PathParam("id") Long id) {
        Post post = Post.findById(id);
        if (post == null) {
            return null;
        }

        post.delete();
        return post;
    }
}
