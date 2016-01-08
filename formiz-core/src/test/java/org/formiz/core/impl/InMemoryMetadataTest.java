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

package org.formiz.core.impl;

import java.io.IOException;

import org.formiz.core.expr.impl.ElContext;
import org.formiz.core.expr.impl.ElExpressionParser;
import org.formiz.core.expr.impl.FrenchExpressionParser;
import org.junit.Assert;
import org.junit.Test;

public class InMemoryMetadataTest {

	@Test
	public void emptyRepositoryTest() throws IOException {
		InMemoryMetadata m = new InMemoryMetadata();
		m.setParser(new FrenchExpressionParser(new ElExpressionParser()));
		m.init();

		// Test Add
		m.addElement(new ExpressionElementBuilder(m).init("id1", "group1").expression("1 est égal à 1").build());

		// Test get
		ExpressionElement e = (ExpressionElement) m.getElement("group1", "id1");
		Assert.assertTrue((Boolean) e.getExpression().getValue(new ElContext()));

		// Test get By group
		e = (ExpressionElement) m.getElementsByGroup("group1").get(0);
		Assert.assertTrue((Boolean) e.getExpression().getValue(new ElContext()));

	}

}
