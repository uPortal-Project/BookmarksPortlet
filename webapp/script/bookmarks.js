var knownEntryTypes = new Array()
knownEntryTypes[0] = 'folder';
knownEntryTypes[1] = 'bookmark';


/***** Public Methods *****/
function newEntry(type, namespace) {
    setupForm(type, 'new', namespace);
    getNamespacedElement(namespace, 'folderAction').innerHTML = "Create in folder:";
    
    //Ensure all the Folder options are enabled
    var form = getForm(namespace);
    var folderOpts =  form.elements['folderPath'].options;
    for (var index = 0; index < folderOpts.length; index++) {
        folderOpts[index].disabled = false;
    }
    
    showForm(namespace);
}

function cancelEntry(namespace) {
    hideForm(namespace);
}

function editEntry(type, namespace, parentFolderIndexPath, entryIndexPath) {
    setupForm(type, 'edit', namespace);

    var form = getForm(namespace);
    
    form.elements['indexPath'].value = entryIndexPath;
    form.elements['type'].value = type;

    form.elements['name'].value = getNamespacedElement(namespace, 'name_' + entryIndexPath).innerHTML;
    form.elements['note'].value = getNamespacedElement(namespace, 'note_' + entryIndexPath).innerHTML;;
    
    if (type == 'bookmark') {
        var entryUrl = getNamespacedElement(namespace, 'url_' + entryIndexPath);
        form.elements['url'].value = entryUrl.href;
        form.elements['newWindow'].checked = (entryUrl.target != "");
    }
    
    getNamespacedElement(namespace, 'folderAction').innerHTML = "Move to folder:";

    //Select the folder the entry is in
    var folderOpts =  form.elements['folderPath'].options;
    for (var index = 0; index < folderOpts.length; index++) {
        if (folderOpts[index].value == parentFolderIndexPath) {
            folderOpts[index].selected = true;
        }
        else {
            folderOpts[index].selected = false;
        }

        if (folderOpts[index].value.indexOf(entryIndexPath) == 0) {
            folderOpts[index].disabled = true;
        }
        else {
            folderOpts[index].disabled = false;
        }
    }
    
    showForm(namespace);
}

function deleteEntry(type, namespace, name, url) {
    var confirmMessage = "Are you sure you want to delete the '" + name + "' ";

    if (type == 'bookmark') {
        confirmMessage = confirmMessage + "Bookmark?";
    }
    else {
        confirmMessage = confirmMessage + "Folder?\n" + 
            "All children bookmarks and folders will be deleted as well.";
    }
    
    var shouldDelete = confirm(confirmMessage);
    
    if (shouldDelete) {
        location.href = url;
    }
}



/***** Internal Methods *****/
function getForm(namespace) {
    return document.forms[namespace + 'bookmarksForm'];
}
function getNamespacedElement(namespace, elementId) {
    return document.getElementById(namespace + elementId);
}
function hideElement(namespace, elementId) {
    var element = getNamespacedElement(namespace, elementId);
    element.style.display = 'none';
}
function showDiv(namespace, elementId) {
    var element = getNamespacedElement(namespace, elementId);
    element.style.display = 'block';
}
function showTableRow(namespace, elementId) {
    var element = getNamespacedElement(namespace, elementId);
    element.style.display = '';
}

function setupForm(type, action, namespace) {
    var form = getForm(namespace);
    form.reset();
    
    //Reset doesn't seem to affect hidden fields: 
    form.elements['indexPath'].value = "";
    form.elements['type'].value = "";
    form.elements['action'].value = "";

    if (type == 'bookmark') {
        if (action == 'new') {
            form.elements['action'].value = 'newBookmark';
        }
        else {
            form.elements['action'].value = 'editBookmark';
        }
        
        form.elements['url'].disabled = false;
        form.elements['newWindow'].disabled = false;
        showTableRow(namespace, 'urlRow');
        showTableRow(namespace, 'newWindowRow');
    }
    else {
        if (action == 'new') {
            form.elements['action'].value = 'newFolder';
        }
        else {
            form.elements['action'].value = 'editFolder';
        }
        
        form.elements['url'].disabled = true;
        form.elements['newWindow'].disabled = true;
        hideElement(namespace, 'urlRow');
        hideElement(namespace, 'newWindowRow');
    }
}

function showForm(namespace) {
    showDiv(namespace, 'bookmarksDiv');
    
    var form = getForm(namespace);
    form.elements['name'].focus();
}

function hideForm(namespace) {
    hideElement(namespace, 'bookmarksDiv');
    getForm(namespace).reset();
}
