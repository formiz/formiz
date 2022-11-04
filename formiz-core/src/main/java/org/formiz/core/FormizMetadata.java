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
package org.formiz.core;

import java.io.IOException;
import java.util.List;

import org.formiz.core.expr.IParser;

/**
 * Formiz model repository.
 * <p>
 * This repository stores all elements and rules which will be used by formiz
 * and your application. These elements should extend SimpleElement and add all
 * required additional data and expressions.
 * <p>
 * For instance, formiz-form creates several elements types used to describe a
 * web form.
 *
 * <p>
 * The default repository is not persistant : sources are the standard way to
 * populate the repository. All sources are run on {@link #init()}. If the
 * repository implementation stores and restores elements, sources are not
 * necessary and can remain empty.
 * <p>
 * The application can use one or several repositories for better isolation.
 *
 * @author Nicolas Richeton
 *
 */
public interface FormizMetadata {

	/**
	 * Add an element to the repository.
	 *
	 * @param el
	 *            - element to add. <code>SimpleElement</code>
	 */
	void addElement(SimpleElement el);

	/**
	 * Retreive an element based on its group and name.
	 *
	 * @return the unique SimpleElement with this group and id.
	 */
	SimpleElement getElement(String group, String id);

	/**
	 * Retreive the all elements of type className, sharing the same id.
	 *
	 * @return list of SimpleElements.
	 */
	List<SimpleElement> getElementOfType(String className, String id);

	/**
	 * Returns all elements for a single group
	 * @return list of all elements in this group.
	 */
	List<SimpleElement> getElementsByGroup(String group);

	/**
	 * Returns the current parser for expressions contained in elements. This
	 * allows to share the same parser for performance reasons.
	 *
	 * @return current parser.
	 */
	IParser getParser();

	/**
	 * Perform initialization, especially running all input sources to populate
	 * the repository.
	 *
	 * @see #setInputSources(InputSource...)
	 */
	void init() throws IOException;

	/**
	 * Remove an element from the repository.
	 *
	 * @param el
	 *            - element to remove. <code>SimpleElement</code>
	 */
	void removeElement(SimpleElement el);

	/**
	 * Defines the list of classes which will inject elements in this repository
	 * on {@link #init()}.
	 *
	 * @param source
	 *            List of InputSource implementations.
	 */
	void setInputSources(InputSource... source);

	/**
	 * Set the parser to use when reading expressions.
	 */
	void setParser(IParser parser);

}
