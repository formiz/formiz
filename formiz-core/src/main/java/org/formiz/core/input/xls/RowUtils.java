/**
 *  Copyright SCN Guichet Entreprises, Capgemini and contributors (2014-2016)
 * <p>
 * This software is a computer program whose purpose is to [describe
 * functionalities and technical features of your software].
 * <p>
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 * <p>
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 * <p>
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
 * <p>
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 */

package org.formiz.core.input.xls;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

public class RowUtils {

	private static final DataFormatter FORMATTER = new DataFormatter();

	/**
	 * Get the number of columns in this row.
	 *
	 * @return number of columns.
	 */
	public static int columnsCount(Row ligneTitres) {
		Cell cell;
		int count = 0;
		Iterator<Cell> cellIterator = ligneTitres.cellIterator();
		while (cellIterator.hasNext()) {
			cell = cellIterator.next();
			if (StringUtils.isNotBlank(cell.getStringCellValue())) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Check is row is not blank.
	 *
	 * @return true if row is not blank.
	 */
	public static boolean isNotBlank(Row row) {
		boolean nonBlankRowFound = false;
		for (int i = row.getFirstCellNum(); i <= row.getLastCellNum(); i++) {
			Cell cell = row.getCell(i);
			if (cell != null && row.getCell(i).getCellType() != HSSFCell.CELL_TYPE_BLANK) {
				nonBlankRowFound = true;
				break;
			}
		}
		return nonBlankRowFound;
	}

	/**
	 * Create a map with header/row data.
	 * @param target
	 *            data will be added to this map.
	 */
	public static void toMap(Row header, Row row, Map<String, String> target) {
		Cell cell;
		for (int i = 0; i < columnsCount(header); i++) {
			cell = row.getCell(i);
			if (cell == null) {
				target.put(header.getCell(i).getStringCellValue(), StringUtils.EMPTY);
				continue;
			}
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				target.put(header.getCell(i).getStringCellValue(), cell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				target.put(header.getCell(i).getStringCellValue(), FORMATTER.formatCellValue(cell));
				break;
			case Cell.CELL_TYPE_BLANK:
				target.put(header.getCell(i).getStringCellValue(), StringUtils.EMPTY);
				break;
			default:
				target.put(header.getCell(i).getStringCellValue(), StringUtils.EMPTY);
				break;
			}

		}

	}

	private RowUtils() {
	}

}
