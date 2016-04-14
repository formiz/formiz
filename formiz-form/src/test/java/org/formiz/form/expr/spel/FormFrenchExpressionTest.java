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

package org.formiz.form.expr.spel;

import org.formiz.core.expr.IContext;
import org.formiz.core.expr.IExpression;
import org.formiz.core.expr.impl.FrenchExpressionParser;
import org.formiz.core.expr.spel.ElContext;
import org.formiz.core.expr.spel.ElExpressionParser;
import org.formiz.form.expr.IServerSideExpression;
import org.formiz.form.expr.spel.model.Address;
import org.formiz.form.expr.spel.model.User;
import  org.junit.Assert;
import org.junit.Test;

public class FormFrenchExpressionTest {

	@Test
	public void addressTest() {

		String exprText = "form[company] est VRAI";
		FormExpressionParser parser = new FormExpressionParser(new FrenchExpressionParser(new ElExpressionParser()));
		parser.setObjectName("form");
		parser.init();

		IExpression e = parser.parseExpression(exprText);

		// Check expression
		Address a = new Address();

		IContext context = new ElContext();
		context.setObject(a, null);

		a.setCompany(false);
		Assert.assertFalse((Boolean) e.getValue(context));

		a.setCompany(true);
		Assert.assertTrue((Boolean) e.getValue(context));

		// Check dependencies
		Assert.assertTrue(((IServerSideExpression) e).getDependencies().contains("company"));

	}

	@Test
	public void userAddressTest() {
		FormExpressionParser parser = new FormExpressionParser(new FrenchExpressionParser(new ElExpressionParser()));
		parser.setObjectName("user");
		parser.init();

		User u = new User();
		u.setAddress(new Address());

		// Context
		IContext context = new ElContext();
		context.setObject(u, null);
		
		// Check expression
		String exprText = "user[address.company] est VRAI";

		IExpression e = parser.parseExpression(exprText);

		u.getAddress().setCompany(false);
		Assert.assertFalse((Boolean) e.getValue(context));

		u.getAddress().setCompany(true);
		Assert.assertTrue((Boolean) e.getValue(context));

		// Check dependencies
		Assert.assertTrue(((IServerSideExpression) e).getDependencies().contains("address.company"));
	}

}
