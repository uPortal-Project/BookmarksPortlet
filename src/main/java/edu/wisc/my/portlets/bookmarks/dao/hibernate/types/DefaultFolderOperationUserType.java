/* Copyright 2006 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package edu.wisc.my.portlets.bookmarks.dao.hibernate.types;

import edu.wisc.my.portlets.bookmarks.domain.Preferences;

/**
 * UserType for persisting {@link Preferences.DefaultFolderOperation}s.
 * 
 * @author Eric Dalquist <a href="mailto:eric.dalquist@doit.wisc.edu">eric.dalquist@doit.wisc.edu</a>
 * @version $Revision: 12179 $
 */
public class DefaultFolderOperationUserType extends EnumUserType<Preferences.DefaultFolderOperation> { 
    public DefaultFolderOperationUserType() { 
        super(Preferences.DefaultFolderOperation.class); 
    }
}
