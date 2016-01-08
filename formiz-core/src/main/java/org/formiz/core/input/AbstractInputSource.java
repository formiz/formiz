/**
 *  Copyright SCN Guichet Entreprises, Capgemini and contributors (2014-2016)
 *
 * This software is a computer program whose purpose is to [describe
 * functionalities and technical features of your software].
 *
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 */

package org.formiz.core.input;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.formiz.core.FormizMetadata;
import org.formiz.core.InputSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for {@link InputSource}.
 * <p>
 * Sub classes must implement {@link #load()}
 *
 */
public abstract class AbstractInputSource implements InputSource {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractInputSource.class);

	private FormizMetadata metadata;
	private String resource;

	/**
	 * Create generic input source.
	 *
	 * @param resource
	 *            Resource path. Lookup is performed first on the classpath. If
	 *            no file matches, resources is open as a file system resource.
	 * @throws IOException
	 */
	public AbstractInputSource(String resource) throws IOException {
		this.resource = resource;
	}

	/**
	 * Get data input stream.
	 * <p>
	 * A new stream is open for each call, and should be closed after read.
	 *
	 * @return data input stream
	 * @throws FileNotFoundException
	 */
	protected InputStream getIs() throws FileNotFoundException {
		InputStream isClasspath = getClass().getResourceAsStream(resource);
		if (isClasspath == null) {
			isClasspath = new FileInputStream(resource);
		}
		return isClasspath;
	}

	/**
	 * Get the target metadata instance.
	 *
	 * @return the target instance.
	 */
	public FormizMetadata getMetadata() {
		return metadata;
	}

	@Override
	public void load() throws IOException {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Loading {}", resource == null ? "no name" : resource);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.formiz.core.InputSource#setFormizMetadata(org.formiz.core.
	 * FormizMetadata)
	 */
	@Override
	public void setFormizMetadata(FormizMetadata _fm) {
		metadata = _fm;
	}
}
