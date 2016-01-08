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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.formiz.core.FormizMetadata;
import org.formiz.core.InputSource;
import org.formiz.core.SimpleElement;
import org.formiz.core.expr.IParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This {@link FormizMetadata} implementation stores elements in memory.
 * <p>
 * Some index are build for fast access to elements.
 * <p>
 * This implementation is not persistant.
 *
 */
public class InMemoryMetadata implements FormizMetadata {

	private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryMetadata.class);
	private final Map<String, SimpleElement> elements;
	private final Map<String, List<SimpleElement>> elementsByClass;
	private final Map<String, List<SimpleElement>> elementsByGroup;
	private String javascriptTemplate = "recupererValeur('$1')"; //$NON-NLS-1$
	private IParser parser = null;
	private InputSource[] sources;

	/**
	 * Constructor.
	 */
	public InMemoryMetadata() {
		this.elements = new HashMap<String, SimpleElement>();
		this.elementsByGroup = new HashMap<String, List<SimpleElement>>();
		this.elementsByClass = new HashMap<String, List<SimpleElement>>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.formiz.core.FormizMetadata#addElement(org.formiz.core.SimpleElement)
	 */
	@Override
	public void addElement(SimpleElement el) {
		String elementKey = el.getGroup() + "-" + el.getId();

		if (!this.elements.containsKey(elementKey)) {
			this.elements.put(elementKey, el);
			// Index by group
			List<SimpleElement> elementsOfGroup = this.elementsByGroup.get(el.getGroup());
			if (elementsOfGroup == null) {
				elementsOfGroup = new ArrayList<SimpleElement>();
				this.elementsByGroup.put(el.getGroup(), elementsOfGroup);
			}
			elementsOfGroup.add(el);

			// Index by class.
			String className = el.getClass().getName();
			List<SimpleElement> elementsOfClass = this.elementsByClass.get(className);
			if (elementsOfClass == null) {
				elementsOfClass = new ArrayList<SimpleElement>();
				this.elementsByClass.put(className, elementsOfClass);
			}
			elementsOfClass.add(el);
		} else {
			LOGGER.warn("L'element id:{} du groupe :{} est en doublon il n'as pas été inséré dans le modèle",
					el.getId(), el.getGroup());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.formiz.core.FormizMetadata#getElement(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public SimpleElement getElement(String group, String id) {
		return this.elements.get(group + "-" + id); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.formiz.core.FormizMetadata#getElementOfType(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<SimpleElement> getElementOfType(String className, String id) {
		List<SimpleElement> aElement = this.elementsByClass.get(className);

		if (aElement == null) {
			return null;
		}

		List<SimpleElement> result = new ArrayList<SimpleElement>();

		for (SimpleElement fe : aElement) {
			if (StringUtils.equals(id, fe.getId())) {
				result.add(fe);
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.formiz.core.FormizMetadata#getElementsByGroup(java.lang.String)
	 */
	@Override
	public List<SimpleElement> getElementsByGroup(String group) {
		return this.elementsByGroup.get(group);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.gipge.core.form.FormMetadata#getJavascriptTemplate()
	 */
	@Override
	public String getJavascriptTemplate() {
		return this.javascriptTemplate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.formiz.core.FormizMetadata#getParser()
	 */
	@Override
	public IParser getParser() {
		return this.parser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.formiz.core.FormizMetadata#init()
	 */
	@SuppressWarnings("boxing")
	@Override
	public void init() throws IOException {
		if (sources != null) {
			for (InputSource source : this.sources) {
				source.setFormizMetadata(this);
				source.load();
			}
		}
		LOGGER.info("Init success: {} elements", this.elements.size()); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.formiz.core.FormizMetadata#setInputSources(org.formiz.core.
	 * InputSource[])
	 */
	@Override
	public void setInputSources(InputSource... sources) {
		this.sources = sources;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.formiz.core.FormizMetadata#setJavascriptTemplate(java.lang.String)
	 */
	@Override
	public void setJavascriptTemplate(String jsTemplate) {
		this.javascriptTemplate = jsTemplate;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.formiz.core.FormizMetadata#removeElement(org.formiz.core.
	 * SimpleElement)
	 */
	@Override
	public void removeElement(SimpleElement el) {
		this.elements.remove(el);
		this.elementsByClass.get(el.getClass().getName()).remove(el);
		this.elementsByGroup.get(el.getGroup()).remove(el);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.formiz.core.FormizMetadata#setParser(org.formiz.core.expr.IParser)
	 */
	@Override
	public void setParser(IParser parser) {
		this.parser = parser;

	}

}
