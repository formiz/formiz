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

package org.formiz.core.input.xls;

import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.formiz.core.input.AbstractInputSource;

/**
 * Base InputSource for Excel XLS loading.
 *
 */
public abstract class AbstractXlsInputSource extends AbstractInputSource {

	private HSSFSheet sheet;

	private int sheetIndex;

	/**
	 * Create generic CSV input source.
	 *
	 * @param resource
	 *            Resource path. Lookup is performed first on the classpath. If
	 *            no file matches, resources is open as a file system resource.
	 * @throws IOException
	 */
	public AbstractXlsInputSource(String classpathResource) throws IOException {
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
	protected abstract void addElement(Row header, Row row);

	@Override
	public void load() throws IOException {
		super.load();

		if (sheet == null) {
			HSSFWorkbook workbook = new HSSFWorkbook(getIs());
			sheet = workbook.getSheetAt(sheetIndex);
		}

		Iterator<Row> rowIterator = sheet.iterator();
		Row row;

		// Skip first line.
		Row ligneTitres = rowIterator.next();

		// Add other lines.
		while (rowIterator.hasNext()) {
			row = rowIterator.next();
			if (RowUtils.isNotBlank(row)) {
				this.addElement(ligneTitres, row);
			}
		}

	}

	/**
	 * Set the sheet to load.
	 * <p>
	 * This method should only be used for optimization purposes, when the
	 * document has already be open with Apache POI.
	 *
	 * @param sheet
	 *            Sheet to load.
	 */
	@Deprecated
	public void setSheet(HSSFSheet sheet) {
		this.sheet = sheet;
	}

	/**
	 * Set the sheet index to load.
	 *
	 * @param sheetIndex
	 */
	public void setSheet(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}

}
