package fi.museo2015.lidottaja.service;

import fi.museo2015.lidottaja.model.Mapping;

/**
 * An interface for the documentation service
 */
public interface DocumentationService {
	public void setDocFolder(String docFolder);

	public void setXoccoFolder(String xoccoFolder);

	public void generateDocumentation(Mapping mapping);

	public String getDocumentation(Mapping mapping);

	public String getDocumentation(String name);

	public String getSource(String name);
}