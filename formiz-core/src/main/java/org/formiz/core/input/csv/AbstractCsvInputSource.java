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

package org.formiz.core.input.csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.CharUtils;
import org.formiz.core.input.AbstractInputSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Base InputSource for CSV loading.
 *
 */
public abstract class AbstractCsvInputSource extends AbstractInputSource {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCsvInputSource.class);

	/**
	 * Create a map with header/row data.
	 *
	 * @param header
	 *            header line
	 * @param data
	 *            data line.
	 * @return
	 */
	protected static Map<String, String> toMap(String[] header, String[] data) {
		Map<String, String> ret = new HashMap<String, String>();
		StringBuilder firstColumn = new StringBuilder();

		if (header.length == 0) {
			return ret;
		}

		// Prevent encoding issues, Skip UTF8 BOM.
		for (char c : header[0].toCharArray()) {
			if (CharUtils.isAsciiPrintable(c)) {
				firstColumn.append(c);
			}
		}
		ret.put(firstColumn.toString(), data[0]);

		for (int i = 1; i < header.length; i++) {
			ret.put(header[i], data[i]);
		}
		return ret;
	}

	/**
	 * Get value at index.
	 *
	 * @param line
	 * @param index
	 * @return value
	 */
	protected static String value(String[] line, int index) {
		if (index < line.length) {
			return line[index];
		}
		return null;
	}

	/**
	 * Create generic CSV input source.
	 *
	 * @param resource
	 *            Resource path. Lookup is performed first on the classpath. If
	 *            no file matches, resources is open as a file system resource.
	 * @throws IOException
	 */
	public AbstractCsvInputSource(String classpathResource) throws IOException {
		super(classpathResource);
	}

	/**
	 * Process a row.
	 * <p>
	 * This method must be implemented by sub-classes.
	 *
	 * @param header
	 *            header line
	 * @param row
	 *            data line
	 */
	protected abstract void addElement(String[] header, String[] row) throws IOException;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.formiz.core.input.AbstractInputSource#load()
	 */
	@Override
	public void load() throws IOException {
		super.load();

		InputStreamReader inputStreamReader = null;
		CSVReader reader = null;
		InputStream is = getIs();
		try {

			inputStreamReader = new InputStreamReader(is, Charset.forName("UTF-8"));
			reader = new CSVReader(inputStreamReader, ';');
			String[] readLine;

			// Get first line
			String[] columnTitle = reader.readNext();

			// Get data.
			readLine = reader.readNext();
			while (readLine != null) {
				this.addElement(columnTitle, readLine);
				readLine = reader.readNext();
			}
		} finally {

			// Close streams.
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
			IOUtils.closeQuietly(inputStreamReader);
			IOUtils.closeQuietly(is);
		}
	}

}
