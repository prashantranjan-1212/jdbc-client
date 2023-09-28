package dev.prashant.jdbcclient.client;

import dev.prashant.jdbcclient.model.Post;
import dev.prashant.jdbcclient.service.PostService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

@Service
public class JdbcClientPostService implements PostService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcClientPostService.class);

    private final JdbcClient jdbcClient;

    public JdbcClientPostService(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<Post> findAll() {
        return jdbcClient.sql("SELECT ID, TITLE, SLUG, DATE, TIME_TO_READ, TAGS FROM POST")
                .query(Post.class)
                .list();
    }

    @Override
    public Optional<Post> findById(String id) {
        return jdbcClient.sql("SELECT ID, TITLE, SLUG, DATE, TIME_TO_READ, TAGS FROM POST WHERE ID = ?")
                .param(id)
                .query(Post.class)
                .optional();
    }

    @Override
    public void create(Post post) {
        int created = jdbcClient.sql("INSERT INTO POST(ID, TITLE, SLUG, DATE, TIME_TO_READ, TAGS) VALUES (?, ?, ?, ?, ?, ?)")
                .params(post.id(), post.title(), post.slug(), post.date(), post.timeToRead(), post.tags())
                .update();

        if (created == 1 ) {
            LOGGER.info("Post created with id : {}", post.id());
        }
    }

    @Override
    public void update(Post post, String id) {
        int updated = jdbcClient.sql("UPDATE POST SET TITLE = ?, SLUG = ?, DATE =?, TIME_TO_READ = ?, TAGS = ? WHERE ID = ?")
                .params(post.title(), post.slug(), post.date(), post.timeToRead(), post.tags(), id)
                .update();

        if (updated == 1 ) {
            LOGGER.info("Post created with id : {}", post.id());
        }

    }

    @Override
    public void delete(String id) {
        int deleted = jdbcClient.sql("DELETE POST WHERE ID = ?")
                .params(id)
                .update();

        if (deleted == 1 ) {
            LOGGER.info("Post delete with id : {}", id);
        }
    }
}
