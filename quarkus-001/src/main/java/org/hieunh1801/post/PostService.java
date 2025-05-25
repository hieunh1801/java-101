package org.hieunh1801.post;

import io.quarkus.hibernate.orm.panache.Panache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@ApplicationScoped
public class PostService {

    @Inject
    private PostRepository postRepository;

    private static final Logger log = LoggerFactory.getLogger(PostService.class);

    public Post getOne(Long id) {
        return Post.findById(id);
    }

    public List<Post> getMany() {
        return Post.listAll();
    }

    @Transactional
    public Post create(Post post) {
        post.persist();
        return post;
    }

    @Transactional
    public Post update(Long id, Post post) {
        Post oldPost = Post.findById(id);
        if (oldPost == null) {
            return null;
        }
        oldPost.name = post.name;
        oldPost.email = post.email;
        oldPost.persist();
        return oldPost;
    }

    @Transactional
    public void fake(int count, int batchSize) {
        for (int i = 0; i < count; i++) {
            List<Post> posts = generate(batchSize);
            Post.persist(posts);
            Post.getEntityManager().flush();
            log.info("INSERTED {}", posts.size());
        }
    }

    @Transactional
    public void fakeThreads(int count, int batchSize) {
        int threads = 20;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            futures.add(executor.submit(() -> {
                postRepository.save(generate(batchSize));
                log.info("INSERTED {}", batchSize);
            }));
        }

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void fakeVirtualThreads(int count, int batchSize) {
//        Executors executors = Executors.newVirtualThreadPerTaskExecutor();
        for (int i = 0; i < count; i++) {
            List<Post> posts = generate(batchSize);
            log.info("INSERTED {}", posts.size());
        }
    }


    private List<Post> generate(int count) {
        List<Post> postList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Post post = new Post();
            post.name = "Name " + i;
            post.email = "Email " + i;
            postList.add(post);
        }
        return postList;
    }
}
