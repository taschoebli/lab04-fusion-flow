package io.flowing.retail.reporting.repository;

import io.flowing.retail.reporting.model.BlacklistEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BlacklistRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<BlacklistEntry> BLACKLIST_ROW_MAPPER = (rs, rowNum) -> {
        BlacklistEntry entry = new BlacklistEntry();
        entry.setFirstName(rs.getString("firstname"));
        entry.setLastName(rs.getString("lastname"));
        entry.setEmail(rs.getString("email"));
        return entry;
    };

    public List<BlacklistEntry> findByEmail(String email) {
        String sql = "SELECT firstname, lastname, email FROM blacklist WHERE email = ?";
        return jdbcTemplate.query(sql, new Object[]{email}, BLACKLIST_ROW_MAPPER);
    }

    public List<BlacklistEntry> findAll() {
        String sql = "SELECT firstname, lastname, email FROM blacklist";
        return jdbcTemplate.query(sql, BLACKLIST_ROW_MAPPER);
    }
}
