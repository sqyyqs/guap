package com.sqy.guap.repository;

import com.sqy.guap.domain.Pupil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PupilRepository {
    private static final Logger logger = LoggerFactory.getLogger(PupilRepository.class);
    private static final String SQL_SELECT_PUPIL_BY_ID = "select * from pupil where pupil_id = :id";

    private final NamedParameterJdbcTemplate npjTemplate;

    public PupilRepository(NamedParameterJdbcTemplate npjTemplate) {
        this.npjTemplate = npjTemplate;
    }

    public Pupil getPupilById(long id) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", id);

            return npjTemplate.queryForObject(SQL_SELECT_PUPIL_BY_ID, namedParameters, getPupilRowMapper());
        } catch (DataAccessException ex) {
            logger.error("Invoke getPupilById({}) with exception.", id, ex);
        }
        return null;
    }

    private static RowMapper<Pupil> getPupilRowMapper() {
        return ((rs, rowNum) -> new Pupil(
                rs.getLong("pupil_id"),
                rs.getString("name")));
    }
}
