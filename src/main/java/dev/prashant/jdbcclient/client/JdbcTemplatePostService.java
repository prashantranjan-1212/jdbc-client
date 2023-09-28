package dev.prashant.jdbcclient.client;

import dev.prashant.jdbcclient.model.Post;
import dev.prashant.jdbcclient.service.PostService;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class JdbcTemplatePostService implements PostService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcTemplatePostService.class);
    private final JdbcTemplate jdbcTemplate;

    RowMapper<Post> rowMapper = (resultSet, rowNumber) -> new Post(
        resultSet.getString("id"),
        resultSet.getString("title"),
        resultSet.getString("slug"),
        resultSet.getDate("date").toLocalDate(),
        resultSet.getInt("time_to_read"),
        resultSet.getString("tags")
    );

    public JdbcTemplatePostService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Post> findAll() {
        String sql = "SELECT ID, TITLE, SLUG, DATE, TIME_TO_READ, TAGS FROM POST";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<Post> findById(String id) {
        String sql = "SELECT ID, TITLE, SLUG, DATE, TIME_TO_READ, TAGS FROM POST WHERE ID = ?";
        Post post = null;
        try {
            post = jdbcTemplate.queryForObject(sql, rowMapper);
        } catch (DataAccessException exception) {
            LOGGER.info("Post not found for id : {}", id);
        }

        return Optional.ofNullable(post);
    }

    @Override
    public void create(Post post) {
        String sql = "INSERT INTO POST(ID, TITLE, SLUG, DATE, TIME_TO_READ, TAGS) VALUES (?, ?, ?, ?, ?, ?)";
        int inserted = jdbcTemplate.update(sql, post.id(), post.title(), post.slug(), post.date(), post.timeToRead(), post.tags());
        if (inserted == 1 ) {
            LOGGER.info("Post created with id : {}", post.id());
        }
    }

    @Override
    public void update(Post post, String id) {
        String sql = "UPDATE POST SET TITLE = ?, SLUG = ?, DATE =?, TIME_TO_READ = ?, TAGS = ? WHERE ID = ?";
        int updated = jdbcTemplate.update(sql, post.title(), post.slug(), post.date(), post.timeToRead(), post.tags(), post.id());
        if (updated == 1 ) {
            LOGGER.info("Post update with id : {}", post.id());
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE POST WHERE ID = ?";
        int deleted = jdbcTemplate.update(sql, id);
        if (deleted == 1 ) {
            LOGGER.info("Post delete with id : {}", id);
        }
    }
}
