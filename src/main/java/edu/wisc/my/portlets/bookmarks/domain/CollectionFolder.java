/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package edu.wisc.my.portlets.bookmarks.domain;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.wisc.my.portlets.bookmarks.domain.support.IntegerSetThreadLocal;

/**
 * <p>CollectionFolder class.</p>
 *
 * @author cbeach
 * @version $Id: $Id
 */
public class CollectionFolder extends Entry implements CollapsibleEntry {
    private static final long serialVersionUID = 1L;
    
	private static Logger log = LoggerFactory.getLogger(CollectionFolder.class);

    private static IntegerSetThreadLocal equalsVisitedFolder = new IntegerSetThreadLocal();
    private static IntegerSetThreadLocal hashCodeVisitedFolder = new IntegerSetThreadLocal();
    private static IntegerSetThreadLocal toStringVisitedFolder = new IntegerSetThreadLocal();

    private Map<Long, Entry> children;
    private boolean minimized = false;
    private String type;
    private String url;


    /**
     * <p>Getter for the field <code>children</code>.</p>
     *
     * @return Returns the children, will never return null.
     */
    public Map<Long, Entry> getChildren() {
    	// TODO
    	return new HashMap<Long, Entry>();
    }

    /**
     * <p>isMinimized.</p>
     *
     * @return Returns the minimized.
     */
    public boolean isMinimized() {
        return this.minimized;
    }

    /** {@inheritDoc} */
    public void setMinimized(boolean minimized) {
        this.minimized = minimized;
    }
    
    /**
     * Returns an immutable sorted view of the values of the children Map. The sorting is done
     * using the current childComparator. Warning, this is has a time cost of 2n log(n)
     * on every call.
     *
     * @return An immutable sorted view of the folder's children.
     */
    public List<Entry> getSortedChildren() {
    	List<Entry> children = new ArrayList<Entry>();
    	log.debug("children: " + children.size());

		HttpClient client = new HttpClient();
		GetMethod get = null;

		try {

			log.debug("getting url " + url);
			get = new GetMethod(url);
			int rc = client.executeMethod(get);
			if (rc != HttpStatus.SC_OK) {
				log.error("HttpStatus:" + rc);
			}

			DocumentBuilderFactory domBuilderFactory = DocumentBuilderFactory
				.newInstance();
			DocumentBuilder builder = domBuilderFactory.newDocumentBuilder();

			InputStream in = get.getResponseBodyAsStream();
			builder = domBuilderFactory.newDocumentBuilder();
			Document doc = builder.parse(in);
			get.releaseConnection();
			
			Element e = (Element) doc.getElementsByTagName("rdf:RDF").item(0);
			log.debug("got root " + e);
			NodeList n = e.getElementsByTagName("item");
			log.debug("found items " + n.getLength());
			for (int i = 0; i < n.getLength(); i++) {
				Bookmark bookmark = new Bookmark();
				Element l = (Element) n.item(i);
				bookmark.setName(((Element) l.getElementsByTagName("title").item(0)).getTextContent());
				bookmark.setUrl(((Element) l.getElementsByTagName("link").item(0)).getTextContent());
				if (l.getElementsByTagName("description").getLength() > 0) {
					bookmark.setNote(((Element) l.getElementsByTagName("description").item(0)).getTextContent());
				}
				children.add(bookmark);
				log.debug("added bookmark " + bookmark.getName() + " " + bookmark.getUrl());
			}

		} catch (HttpException e) {
			log.error("Error parsing delicious", e);
		} catch (IOException e) {
			log.error("Error parsing delicious", e);
		} catch (ParserConfigurationException e) {
			log.error("Error parsing delicious", e);
		} catch (SAXException e) {
			log.error("Error parsing delicious", e);
		} finally {
			if (get != null)
				get.releaseConnection();
		}

    	log.debug("children: " + children.size());
    	return children;
    }

	/**
	 * <p>Getter for the field <code>url</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * <p>Setter for the field <code>url</code>.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 */
	public void setUrl(String url) {
		this.url = url;
	}
    
    
    
}
