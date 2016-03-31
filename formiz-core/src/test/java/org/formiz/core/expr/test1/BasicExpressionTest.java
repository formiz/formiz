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

package org.formiz.core.expr.test1;

import org.formiz.core.expr.IContext;
import org.formiz.core.expr.IExpression;
import org.formiz.core.expr.impl.ParseException;
import org.formiz.core.expr.spel.ElContext;
import org.formiz.core.expr.spel.ElExpressionParser;
import org.formiz.core.expr.test1.model.Address;
import org.junit.Assert;
import org.junit.Test;

public class BasicExpressionTest {
	ElExpressionParser parser = new ElExpressionParser();

	@Test
	public void addressTest() {
		String exprText = "company == true";
		IExpression e = parser.parseExpression(exprText);

		// Context
		Address a = new Address();

		IContext context = new ElContext();
		context.setObject(a, null);

		a.setCompany(false);
		Assert.assertFalse((Boolean) e.getValue(context));

		a.setCompany(true);
		Assert.assertTrue((Boolean) e.getValue(context));
	}

	@Test
	public void parsingTest() {
		String exprText = "company ds == true";
		try {
			parser.parseExpression(exprText);
			Assert.fail();
		} catch (ParseException e) {
			Assert.assertEquals(exprText, e.getOriginalExpression());
			Assert.assertEquals(exprText, e.getExpression());
		}

	}
}
