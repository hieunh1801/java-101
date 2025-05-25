package org.hieunh1801.post;

import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.virtual.threads.VirtualThreads;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class PostRepository {
    @Transactional
    public void save(List<Post> posts) {
        Post.persist(posts);
        Post.getEntityManager().flush();
        Post.getEntityManager().clear();
    }

}
