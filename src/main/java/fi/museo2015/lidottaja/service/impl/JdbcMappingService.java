package fi.museo2015.lidottaja.service.impl;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.xml.xpath.XPathExpression;
import org.xml.sax.SAXException;

import fi.museo2015.lidottaja.model.Mapping;
import fi.museo2015.lidottaja.service.MappingService;

/**
 * A JDBC implementation of the mapping repository.
 */
@Service("mappingService")
@Repository
public class JdbcMappingService implements MappingService {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	private XPathExpression splitExpression;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Mapping findMapping(String id) {
		return (Mapping) this.jdbcTemplate.queryForObject(
				"select xml from t_mapping where id = ?", new Object[] { id },
				new MappingRowMapper());
	}

	class MappingRowMapper implements RowMapper<Mapping> {
		public Mapping mapRow(ResultSet rs, int rowNum) throws SQLException {
			String xml = rs.getString("xml");
			String name = rs.getString("id");
			Mapping mapping;
			try {
				mapping = new Mapping(xml);
				mapping.setName(name);
				mapping.setSaved(true);
				return mapping;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Mapping> findMappings(String username) {
		Collection<Mapping> mappings = this.jdbcTemplate.query(
				"select id, xml from t_mapping where username = ?",
				new Object[] { username }, new MappingRowMapper());
		return new ArrayList<Mapping>(mappings);
	}

	@Transactional
	public void persistMapping(Mapping mapping, String username) {
		if (mapping.isSaved())
			this.jdbcTemplate.update(
					"update t_mapping set xml = ? where id = ?", new Object[] {
							mapping.getXML(), mapping.getName() });
		else
			this.jdbcTemplate
					.update("insert into t_mapping (id, xml, username) values (?, ?, ?)",
							new Object[] { mapping.getName(), mapping.getXML(),
									username });
	}

	@Transactional
	public void removeMapping(String name) {
		this.jdbcTemplate.update("delete from t_mapping where id = ?",
				new Object[] { name });
	}
}