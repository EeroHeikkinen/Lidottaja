package fi.museo2015.lidottaja.service;

import java.util.List;

import fi.museo2015.lidottaja.model.Mapping;

/**
 * A service interface for accessing mappings in a backing repository.
 */
public interface MappingService {

	/**
	 * Find a mapping by identifier
	 * 
	 * @param name
	 *            the mapping's name
	 * @return the mapping, or null if not found
	 */
	public Mapping findMapping(String name);

	/**
	 * Find all mappings by specified user
	 * 
	 * @param username
	 *            the user's name
	 * @return list of mappings, or an empty list if none found
	 */
	public List<Mapping> findMappings(String username);

	/**
	 * Persists the specified mapping with the given username
	 * 
	 * @param mapping
	 *            the mapping to persist
	 * @param username
	 *            the mapper's username
	 * @return void
	 */
	public void persistMapping(Mapping mapping, String username);

	/**
	 * Removes the specified mapping
	 * 
	 * @param name
	 *            the identifier of the mapping to delete
	 * @return void
	 */
	public void removeMapping(String name);
}
