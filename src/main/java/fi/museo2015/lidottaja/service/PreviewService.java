package fi.museo2015.lidottaja.service;

/*
 * An interface for a  service which generates previews using a third-party generator
 */
public interface PreviewService {
	public void pushPreview(String name, String lido);

	public String getPreview(String name);
}