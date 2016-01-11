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

package org.formiz.input.fods;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlfield.core.exception.XmlFieldParsingException;

public class FodsHelperTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(FodsHelperTest.class);

	@Test
	public void automaticStylesTest() throws XmlFieldParsingException {
		Workbook fods = FodsHelper.read(FodsHelperTest.class.getResourceAsStream("sample.fods"));

		Assert.assertTrue(fods.getAutomaticStyles().length > 0);
	}

	@Test
	public void displayFileTest() throws XmlFieldParsingException {

		Workbook fods = FodsHelper.read(FodsHelperTest.class.getResourceAsStream("sample.fods"));

		LOGGER.info(" Styles : " + fods.getStyles().length);
		for (Style s : fods.getStyles()) {
			LOGGER.info(s.getName() + " " + s.getDisplayName());
		}

		LOGGER.info(" Automatic Styles : " + fods.getAutomaticStyles().length);
		for (AutomaticStyle ms : fods.getAutomaticStyles()) {
			LOGGER.info(ms.getName() + " " + " " + ms.getParentName() + " " + ms.getMasterPageName());
		}

		LOGGER.info(" Tables : " + fods.getSheets().length);
		for (Sheet ms : fods.getSheets()) {
			LOGGER.info("Table " + ms.getName() + " (" + FodsHelper.getTopParentStyleName(fods, ms) + ")");
			for (Row rows : ms.getRows()) {
				StringBuilder sb = new StringBuilder();
				for (Cell c : rows.getCells()) {
					sb.append(c.getText());
					if (FodsHelper.getTopParentStyleName(fods, c) != null) {
						sb.append("@" + FodsHelper.getTopParentStyleName(fods, c));
					}
					sb.append("|");

				}
				LOGGER.info(sb.toString());
			}
		}

	}

	@Test
	public void masterStylesTest() throws XmlFieldParsingException {
		Workbook fods = FodsHelper.read(FodsHelperTest.class.getResourceAsStream("sample.fods"));

		List<String> displayNames = new ArrayList<String>();
		for (Style ms : fods.getMasterStyles()) {
			displayNames.add(ms.getDisplayName());
		}

		Assert.assertTrue(displayNames.contains("FORMIZ_FLOW"));
		Assert.assertTrue(displayNames.contains("FORMIZ_VALUES"));
		Assert.assertTrue(displayNames.contains("FORMIZ_PAGE"));
		Assert.assertTrue(displayNames.contains("FORMIZ_TYPE"));

	}

	@Test
	public void pageFlowTest() throws XmlFieldParsingException {
		Workbook fods = FodsHelper.read(FodsHelperTest.class.getResourceAsStream("sample.fods"));

		Sheet pageFlow = null;
		// Get Sheet named "Page flow"
		for (Sheet ms : fods.getSheets()) {
			if (ms.getName().equals("Page flow")) {
				pageFlow = ms;
				break;
			}
		}

		// Check sheet style
		Assert.assertEquals("FORMIZ_FLOW", FodsHelper.getTopParentStyleName(fods, pageFlow));

		// Check cell style
		Assert.assertEquals("FORMIZ_FLOW_ID",
				FodsHelper.getTopParentStyleName(fods, pageFlow.getRows()[0].getCells()[0]));
	}

	@Test
	public void stylesTest() throws XmlFieldParsingException {
		Workbook fods = FodsHelper.read(FodsHelperTest.class.getResourceAsStream("sample.fods"));

		List<String> displayNames = new ArrayList<String>();
		for (Style ms : fods.getStyles()) {
			displayNames.add(ms.getDisplayName());
		}

		Assert.assertTrue(displayNames.contains("FORMIZ_FLOW_ID"));
		Assert.assertTrue(displayNames.contains("FORMIZ_FLOW_PAGE"));
		Assert.assertTrue(displayNames.contains("FORMIZ_FLOW_LABEL"));
		Assert.assertTrue(displayNames.contains("FORMIZ_FLOW_VISIBILITY"));
		Assert.assertTrue(displayNames.contains("FORMIZ_FLOW_DESCRIPTION"));
		Assert.assertTrue(displayNames.contains("FORMIZ_FLOW_ON_INIT"));
		Assert.assertTrue(displayNames.contains("FORMIZ_FLOW_ON_SAVE"));
		Assert.assertTrue(displayNames.contains("FORMIZ_FLOW_COUNT"));
		Assert.assertTrue(displayNames.contains("FORMIZ_FLOW_LOOP"));
		Assert.assertTrue(displayNames.contains("FORMIZ_FLOW_OBJECT"));
		Assert.assertTrue(displayNames.contains("FORMIZ_ELEMENT_ID"));
		Assert.assertTrue(displayNames.contains("FORMIZ_ELEMENT_FIELD"));
		Assert.assertTrue(displayNames.contains("FORMIZ_ELEMENT_LABEL"));
		Assert.assertTrue(displayNames.contains("FORMIZ_ELEMENT_MANDATORY"));
		Assert.assertTrue(displayNames.contains("FORMIZ_ELEMENT_TYPE"));
	}

}
