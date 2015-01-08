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
package edu.wisc.my.portlets.bookmarks.dao.file;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessResourceFailureException;

import edu.wisc.my.portlets.bookmarks.dao.BookmarkStore;
import edu.wisc.my.portlets.bookmarks.domain.BookmarkSet;

/**
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12150 $
 */
public class FileSystemBookmarkStore implements BookmarkStore {
    protected final Log logger = LogFactory.getLog(this.getClass());
    
    private String baseStorePath = null;
    
    
    /**
     * @return Returns the baseStorePath.
     */
    public String getBaseStorePath() {
        return this.baseStorePath;
    }
    /**
     * @param baseStorePath The baseStorePath to set.
     */
    public void setBaseStorePath(String baseStorePath) {
        this.baseStorePath = baseStorePath;
    }
    
    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.BookmarkStore#getBookmarkSet(java.lang.String, java.lang.String)
     */
    public BookmarkSet getBookmarkSet(String owner, String name) {
        final File storeFile = this.getStoreFile(owner, name);
        
        //Ok if the file doesn't exist, the user hasn't stored one yet.
        if (!storeFile.exists()) {
            return null;
        }
        
        try {
            final FileInputStream fis = new FileInputStream(storeFile);
            final BufferedInputStream bis = new BufferedInputStream(fis);
            final XMLDecoder d = new XMLDecoder(bis);

            try {
                final BookmarkSet bs = (BookmarkSet)d.readObject();
                return bs;
            }
            finally {
                d.close();
            }
        }
        catch (FileNotFoundException fnfe) {
            final String errorMsg = "Error reading BookmarkSet for owner='" + owner + "', name='" + name + "' from file='" + storeFile + "'";
            logger.error(errorMsg, fnfe);
            throw new DataAccessResourceFailureException(errorMsg);
        }
    }

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.BookmarkStore#storeBookmarkSet(edu.wisc.my.portlets.bookmarks.domain.BookmarkSet)
     */
    public void storeBookmarkSet(BookmarkSet bookmarkSet) {
        if (bookmarkSet == null) {
            throw new IllegalArgumentException("AddressBook may not be null");
        }
        
        final File storeFile = this.getStoreFile(bookmarkSet.getOwner(), bookmarkSet.getName());
        
        try {
            final FileOutputStream fos = new FileOutputStream(storeFile);
            final BufferedOutputStream bos = new BufferedOutputStream(fos); 
            final XMLEncoder e = new XMLEncoder(bos);
            try {
                e.writeObject(bookmarkSet);
            }
            finally {
                e.close();
            }
        }
        catch (FileNotFoundException fnfe) {
            final String errorMsg = "Error storing BookmarkSet='" + bookmarkSet + "' to file='" + storeFile + "'";
            logger.error(errorMsg, fnfe);
            throw new DataAccessResourceFailureException(errorMsg, fnfe); 
        }
    }

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.BookmarkStore#removeBookmarkSet(java.lang.String, java.lang.String)
     */
    public void removeBookmarkSet(String owner, String name) {
        final File storeFile = this.getStoreFile(owner, name);
        storeFile.delete();
    }
    

    /**
     * @see edu.wisc.my.portlets.bookmarks.dao.BookmarkStore#createBookmarkSet(java.lang.String, java.lang.String)
     */
    public BookmarkSet createBookmarkSet(String owner, String name) {
        final BookmarkSet bookmarkSet = new BookmarkSet();
        bookmarkSet.setOwner(owner);
        bookmarkSet.setName(name);
        bookmarkSet.setCreated(new Date());
        bookmarkSet.setModified(bookmarkSet.getCreated());

        this.storeBookmarkSet(bookmarkSet);
        
        return bookmarkSet;
    }
    
    /**
     * Generates the file name String for an owner and book name.
     * 
     * @param owner The owner of the bookmark set.
     * @param name The name of the bookmark set.
     * @return The file name for the owner and name.
     */
    protected String getStoreFileName(String owner, String name) {
        final StringBuilder fileNameBuff = new StringBuilder();
        
        fileNameBuff.append(owner != null ? "_" + owner : "null");
        fileNameBuff.append("_");
        fileNameBuff.append(name != null ? "_" + name : "null");
        fileNameBuff.append(".bms.xml");
        
        return fileNameBuff.toString();
    }

    /**
     * Generates the {@link File} object to use for storing, retrieving
     * and deleting the bookmark set.
     * 
     * @param owner The owner of the bookmark set.
     * @param name The name of the bookmark set.
     * @return The File for the owner and name.
     */
    protected File getStoreFile(String owner, String name) {
        final String fileStoreName = this.getStoreFileName(owner, name);
        final File basePath = this.getStoreDirectory();
        final File storeFile = new File(basePath, fileStoreName);
        return storeFile;
    }
    
    /**
     * @return The directory to store AddressBooks in.
     */
    protected File getStoreDirectory() {
        return (this.baseStorePath == null ? new File(".") : new File(this.baseStorePath));
    }
}
