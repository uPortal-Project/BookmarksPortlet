package edu.wisc.my.portlets.bookmarks.web;

import java.util.Date;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;

import edu.wisc.my.portlets.bookmarks.domain.BookmarkSet;
import edu.wisc.my.portlets.bookmarks.domain.CollectionFolder;
import edu.wisc.my.portlets.bookmarks.domain.Entry;
import edu.wisc.my.portlets.bookmarks.domain.Folder;
import edu.wisc.my.portlets.bookmarks.domain.support.FolderUtils;
import edu.wisc.my.portlets.bookmarks.domain.support.IdPathInfo;

public class NewCollectionFormController extends BaseEntryFormController {
    /**
     * @see org.springframework.web.portlet.mvc.SimpleFormController#onSubmitAction(javax.portlet.ActionRequest, javax.portlet.ActionResponse, java.lang.Object, org.springframework.validation.BindException)
     */
    @Override
    protected void onSubmitAction(ActionRequest request, ActionResponse response, Object command, BindException errors) throws Exception {
        final String targetParentPath = StringUtils.defaultIfEmpty(request.getParameter("folderPath"), null);
        
        //User edited bookmark
        final CollectionFolder commandBookmark = (CollectionFolder)command;
        
        //Get the BookmarkSet from the store
        final BookmarkSet bs = this.bookmarkSetRequestResolver.getBookmarkSet(request);
        
        //Ensure the created & modified dates are set correctly
        commandBookmark.setCreated(new Date());
        commandBookmark.setModified(commandBookmark.getCreated());
        
        final Folder targetParent;
        if (targetParentPath != null) {
            //Get the target parent folder
            final IdPathInfo targetParentPathInfo = FolderUtils.getEntryInfo(bs, targetParentPath);
            if (targetParentPathInfo == null || targetParentPathInfo.getTarget() == null) {
                throw new IllegalArgumentException("The specified parent Folder does not exist. BaseFolder='" + bs + "' and idPath='" + targetParentPath + "'");
            }
            
            targetParent = (Folder)targetParentPathInfo.getTarget();
        }
        else {
            targetParent = bs;
        }
        
        final Map<Long, Entry> targetChildren = targetParent.getChildren();
        
        //Add the new bookmark to the target parent
        targetChildren.put(commandBookmark.getId(), commandBookmark);
        
        //Persist the changes to the BookmarkSet 
        this.bookmarkStore.storeBookmarkSet(bs);
    }

}
