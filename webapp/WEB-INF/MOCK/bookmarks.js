var knownEntryTypes = new Array();
knownEntryTypes[0] = "folder";
knownEntryTypes[1] = "bookmark";


/***** Public Methods *****/
function toggleFolder(contextPath, namespace, folderIdPath) {
    var folderImg = getNamespacedElement(namespace, "folderImg_" + folderIdPath);
    
    if (isElementHidden(namespace, "children_" + folderIdPath)) {
        showElementBlock(namespace, "children_" + folderIdPath);
        folderImg.src = contextPath + "/img/folder-opened.gif";
    }
    else {
        hideElement(namespace, "children_" + folderIdPath);
        folderImg.src = contextPath + "/img/folder-closed.gif";
    }
}

function toggleEditMode(enableEdit, namespace) {
	var editSpans = document.getElementsByName(namespace + "_entryEditUI");
	for (var index = 0; index < editSpans.length; index++) {
		if (enableEdit) {
			editSpans[index].style.display = "inline";
		}
		else {
			editSpans[index].style.display = "none";
		}
	}
	
	if (enableEdit) {
		hideElement(namespace, "editLink");
		showElementInline(namespace, "cancelLink");
	}
	else {
		hideElement(namespace, "cancelLink");
		showElementInline(namespace, "editLink");
	}
}

function newEntry(type, namespace) {
    setupForm(type, "new", namespace);
    getNamespacedElement(namespace, "folderAction").innerHTML = "Move to folder:";
    
    //Ensure all the Folder options are enabled
    var form = getForm(namespace);
    var folderOpts =  form.elements["folderPath"].options;
    for (var index = 0; index < folderOpts.length; index++) {
        folderOpts[index].disabled = false;
    }
    
    showForm(namespace);
}

function cancelEntry(namespace) {
	toggleEditMode(false, namespace);
    hideForm(namespace);
}

function editEntry(type, namespace, parentFolderIdPath, entryIdPath) {
    setupForm(type, "edit", namespace);

    var form = getForm(namespace);
    
    form.elements["idPath"].value = entryIdPath;
    form.elements["type"].value = type;

    form.elements["name"].value = getNamespacedElement(namespace, "name_" + entryIdPath).innerHTML;
    form.elements["note"].value = getNamespacedElement(namespace, "note_" + entryIdPath).innerHTML;;
    
    if (type == "bookmark") {
        var entryUrl = getNamespacedElement(namespace, "url_" + entryIdPath);
        form.elements["url"].value = entryUrl.href;
        form.elements["newWindow"].checked = (entryUrl.target != "");
    }
    
    getNamespacedElement(namespace, "folderAction").innerHTML = "Create in folder:";

    //Select the folder the entry is in
    var folderOpts =  form.elements["folderPath"].options;
    for (var index = 0; index < folderOpts.length; index++) {
        if (folderOpts[index].value == parentFolderIdPath) {
            folderOpts[index].selected = true;
        }
        else {
            folderOpts[index].selected = false;
        }

        if (folderOpts[index].value.indexOf(entryIdPath) == 0) {
            folderOpts[index].disabled = true;
        }
        else {
            folderOpts[index].disabled = false;
        }
    }
    
    showForm(namespace);
}

function deleteEntry(type, namespace, name, url) {
    var confirmMessage = "";

    if (type == "bookmark") {
    	confirmMessage = confirmMessage + "Are you sure you want to delete the \'";
    	confirmMessage = confirmMessage + name;
        confirmMessage = confirmMessage + "\' Bookmark?";
    }
    else {
    	confirmMessage = confirmMessage + "Are you sure you want to delete the \'";
    	confirmMessage = confirmMessage + name;
        confirmMessage = confirmMessage + "\' Folder?\nAll children bookmarks and folders will be deleted as well.";
    }
    
    var shouldDelete = confirm(confirmMessage);
    
    if (shouldDelete) {
        location.href = url;
    }
}



/***** Internal Methods *****/
function getForm(namespace) {
    return document.forms[namespace + "bookmarksForm"];
}
function getNamespacedElement(namespace, elementId) {
    return document.getElementById(namespace + elementId);
}
function hideElement(namespace, elementId) {
    var targetElement = getNamespacedElement(namespace, elementId);
    targetElement.style.display = "none";
}
function isElementHidden(namespace, elementId) {
    var targetElement = getNamespacedElement(namespace, elementId);
    return targetElement.style.display == "none";
}
function showElementBlock(namespace, elementId) {
    showElement(namespace, elementId, "block");
}
function showElementInline(namespace, elementId) {
    showElement(namespace, elementId, "inline");
}
function showElementTableRow(namespace, elementId) {
    showElement(namespace, elementId, "");
}
function showElement(namespace, elementId, displayType) {
    var targetElement = getNamespacedElement(namespace, elementId);
    targetElement.style.display = displayType;
}

function setupForm(type, action, namespace) {
    var form = getForm(namespace);
    form.reset();
    
    //Reset doesn"t seem to affect hidden fields: 
    form.elements["idPath"].value = "";
    form.elements["type"].value = "";
    form.elements["action"].value = "";

    if (type == "bookmark") {
        if (action == "new") {
            form.elements["action"].value = "newBookmark";
        }
        else {
            form.elements["action"].value = "editBookmark";
        }
        
        form.elements["url"].disabled = false;
        form.elements["newWindow"].disabled = false;
        showElementTableRow(namespace, "urlRow");
        showElementTableRow(namespace, "newWindowRow");
    }
    else {
        if (action == "new") {
            form.elements["action"].value = "newFolder";
        }
        else {
            form.elements["action"].value = "editFolder";
        }
        
        form.elements["url"].disabled = true;
        form.elements["newWindow"].disabled = true;
        hideElement(namespace, "urlRow");
        hideElement(namespace, "newWindowRow");
    }
}

function showForm(namespace) {
    showElementBlock(namespace, "bookmarksDiv");
    
    var form = getForm(namespace);
    form.elements["name"].focus();
}

function hideForm(namespace) {
    hideElement(namespace, "bookmarksDiv");
    getForm(namespace).reset();
}
