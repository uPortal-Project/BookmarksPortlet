//Create a global (static) array to store data for the bookmarks portlets in.
var bookmarkPortletsData = init();
function init() {
    if (typeof bookmarkPortletsData == "undefined") {
        return new Array();
    }
    else {
        return bookmarkPortletsData;
    }
}

//Object to track all important data in a BookmarksPortlet rendered instance
function BookmarksPortletData(  namespace,
                                bookmark_form_newWindow,
                                bookmark_form_url,
                                bookmark_forms_empty,
                                bookmark_forms_error,
                                bookmark_reference_url,
                                bookmarksTreeAndForm,
                                entry_childFolderPrefix,
                                entry_edit_buttons,
                                entry_edit_cancelLink,
                                entry_edit_editLink,
                                entry_form_action_editBookmark,
                                entry_form_action_editFolder,
                                entry_form_action_newBookmark,
                                entry_form_action_newFolder,
                                entry_form_folderPath,
                                entry_form_folderPathLabel,
                                entry_form_name,
                                entry_form_note,
                                entry_imagePrefix,
                                entry_reference_folderPath,
                                entry_reference_name,
                                entry_reference_note,
                                folder_forms_empty,
                                folder_forms_error,
                                folder_image_closed,
                                folder_image_open,
                                options_forms_empty,
                                options_forms_error,
                                options_showLink,
                                messages_folder_create,
                                messages_folder_move,
                                messages_delete_bookmark_prefix,
                                messages_delete_bookmark_suffix,
                                messages_delete_folder_prefix,
                                messages_delete_folder_suffix) {

    this.namespace = namespace;

    this.bookmark_form_newWindow = bookmark_form_newWindow;
    this.bookmark_form_url = bookmark_form_url;
    this.bookmark_forms_empty = bookmark_forms_empty;
    this.bookmark_forms_error = bookmark_forms_error;
    this.bookmark_reference_url = bookmark_reference_url;
    this.bookmarksTreeAndForm = bookmarksTreeAndForm;
    this.entry_childFolderPrefix = entry_childFolderPrefix;
    this.entry_edit_buttons = entry_edit_buttons;
    this.entry_edit_cancelLink = entry_edit_cancelLink;
    this.entry_edit_editLink = entry_edit_editLink;
    this.entry_form_action_editBookmark = entry_form_action_editBookmark;
    this.entry_form_action_editFolder = entry_form_action_editFolder;
    this.entry_form_action_newBookmark = entry_form_action_newBookmark;
    this.entry_form_action_newFolder = entry_form_action_newFolder;
    this.entry_form_folderPath = entry_form_folderPath;
    this.entry_form_folderPathLabel = entry_form_folderPathLabel;
    this.entry_form_name = entry_form_name;
    this.entry_form_note = entry_form_note;
    this.entry_imagePrefix = entry_imagePrefix;
    this.entry_reference_folderPath = entry_reference_folderPath;
    this.entry_reference_name = entry_reference_name;
    this.entry_reference_note = entry_reference_note;
    this.folder_forms_empty = folder_forms_empty;
    this.folder_forms_error = folder_forms_error;
    this.folder_image_closed = folder_image_closed;
    this.folder_image_open = folder_image_open;
    this.options_forms_empty = options_forms_empty;
    this.options_forms_error = options_forms_error;
    this.options_showLink = options_showLink;
    this.messages_folder_create = messages_folder_create;
    this.messages_folder_move = messages_folder_move;
    this.messages_delete_bookmark_prefix = messages_delete_bookmark_prefix;
    this.messages_delete_bookmark_suffix = messages_delete_bookmark_suffix;
    this.messages_delete_folder_prefix = messages_delete_folder_prefix;
    this.messages_delete_folder_suffix = messages_delete_folder_suffix;


    //Handy function for debugging
    function toString() {
        var stringVal = "BookmarksPortletData[";
        var hasProperties = false;

        for (var property in this) {
            hasProperties = true;

            if ((this[property] + "").indexOf("function ") != 0) {
                stringVal += property + "=" + this[property] + ", ";
            }
        }

        if (hasProperties) {
            stringVal = stringVal.substring(0, stringVal.length - 2);
        }

        stringVal += "]";

        return stringVal;
    }
    this.toString = toString;

    //Object registers itself with the global array of data objects
    bookmarkPortletsData[namespace] = this;
}



/***** Public Methods *****/
function showOptionsForm(namespace) {
    resetForm(namespace, bookmarkPortletsData[namespace].options_forms_empty);

    hideElement(namespace, bookmarkPortletsData[namespace].bookmarksTreeAndForm);
    hideElement(namespace, bookmarkPortletsData[namespace].options_showLink);
    showForm(namespace, bookmarkPortletsData[namespace].options_forms_empty);
}

function cancelOptionsForm(namespace, formName) {
    hideElement(namespace, formName);
    showElementBlock(namespace, bookmarkPortletsData[namespace].bookmarksTreeAndForm);
    showElementInline(namespace, bookmarkPortletsData[namespace].options_showLink);

    resetForm(namespace, formName);
}

function toggleFolder(namespace, folderIdPath, imgContextPath) {
    var folderImg = getNamespacedElement(namespace, bookmarkPortletsData[namespace].entry_imagePrefix + folderIdPath);

    if (isElementHidden(namespace, bookmarkPortletsData[namespace].entry_childFolderPrefix + folderIdPath)) {
        showElementBlock(namespace, bookmarkPortletsData[namespace].entry_childFolderPrefix + folderIdPath);
        folderImg.src = imgContextPath + bookmarkPortletsData[namespace].folder_image_open;
    }
    else {
        hideElement(namespace, bookmarkPortletsData[namespace].entry_childFolderPrefix + folderIdPath);
        folderImg.src = imgContextPath + bookmarkPortletsData[namespace].folder_image_closed;
    }
}

function toggleEditMode(namespace, enableEdit) {
    var editSpans = getNamespacedElements(namespace, bookmarkPortletsData[namespace].entry_edit_buttons);
    for (var index = 0; index < editSpans.length; index++) {
        if (enableEdit) {
            editSpans[index].style.display = "inline";
        }
        else {
            editSpans[index].style.display = "none";
        }
    }

    if (enableEdit) {
        hideElement(namespace, bookmarkPortletsData[namespace].entry_edit_editLink);
        showElementInline(namespace, bookmarkPortletsData[namespace].entry_edit_cancelLink);
    }
    else {
        hideElement(namespace, bookmarkPortletsData[namespace].entry_edit_cancelLink);
        showElementInline(namespace, bookmarkPortletsData[namespace].entry_edit_editLink);
    }
}

function newBookmark(namespace) {
    cancelFolder(namespace, bookmarkPortletsData[namespace].folder_forms_empty);

    var form = resetForm(namespace, bookmarkPortletsData[namespace].bookmark_forms_empty);

    form.elements["idPath"].value = "";
    form.elements["type"].value = "";
    form.elements["action"].value = bookmarkPortletsData[namespace].entry_form_action_newBookmark;

    getNamespacedElement(namespace, bookmarkPortletsData[namespace].entry_form_folderPathLabel).innerHTML = bookmarkPortletsData[namespace].messages_folder_create;

    setupFolderOptions(form.elements[bookmarkPortletsData[namespace].entry_reference_folderPath], form.elements[bookmarkPortletsData[namespace].entry_form_folderPath]);

    showForm(namespace, bookmarkPortletsData[namespace].bookmark_forms_empty, bookmarkPortletsData[namespace].entry_form_name);
}

function cancelBookmark(namespace, formName) {
    hideElement(namespace, formName);
    resetForm(namespace, formName);
}

function newFolder(namespace) {
    cancelBookmark(namespace, bookmarkPortletsData[namespace].bookmark_forms_empty);

    var form = resetForm(namespace, bookmarkPortletsData[namespace].folder_forms_empty);

    form.elements["idPath"].value = "";
    form.elements["type"].value = "";
    form.elements["action"].value = bookmarkPortletsData[namespace].entry_form_action_newFolder;

    getNamespacedElement(namespace, bookmarkPortletsData[namespace].entry_form_folderPathLabel).innerHTML = bookmarkPortletsData[namespace].messages_folder_create;

    setupFolderOptions(form.elements[bookmarkPortletsData[namespace].entry_reference_folderPath], form.elements[bookmarkPortletsData[namespace].entry_form_folderPath]);

    showForm(namespace, bookmarkPortletsData[namespace].folder_forms_empty, bookmarkPortletsData[namespace].entry_form_name);
}

function cancelFolder(namespace, formName) {
    hideElement(namespace, formName);
    resetForm(namespace, formName);
}

function editEntry(namespace, type, parentIdPath, entryIdPath) {
    var form = resetForm(namespace, bookmarkPortletsData[namespace].bookmark_forms_empty);

    form.elements["idPath"].value = entryIdPath;
    form.elements["type"].value = type;
    if (type == "bookmark") {
        form.elements["action"].value =  bookmarkPortletsData[namespace].entry_form_action_editBookmark;
    }
    else {
        form.elements["action"].value =  bookmarkPortletsData[namespace].entry_form_action_editFolder;
    }

    //Set common fields
    form.elements[bookmarkPortletsData[namespace].entry_form_name].value = getNamespacedElement(namespace, bookmarkPortletsData[namespace].entry_reference_name + entryIdPath).innerHTML;
    form.elements[bookmarkPortletsData[namespace].entry_form_note].value = getNamespacedElement(namespace, bookmarkPortletsData[namespace].entry_reference_note + entryIdPath).innerHTML;
    getNamespacedElement(namespace, bookmarkPortletsData[namespace].entry_form_folderPathLabel).innerHTML = bookmarkPortletsData[namespace].messages_folder_move;

    //Set Entity sub-class specific fields
    if (type == "bookmark") {
        var entryUrl = getNamespacedElement(namespace, bookmarkPortletsData[namespace].bookmark_reference_url + entryIdPath);
        form.elements[bookmarkPortletsData[namespace].bookmark_form_url].value = entryUrl.href;
        form.elements[bookmarkPortletsData[namespace].bookmark_form_newWindow].checked = (entryUrl.target != "");
    }

    setupFolderOptions(form.elements[bookmarkPortletsData[namespace].entry_reference_folderPath], form.elements[bookmarkPortletsData[namespace].entry_form_folderPath], parentIdPath, entryIdPath);
    showForm(namespace, bookmarkPortletsData[namespace].bookmark_forms_empty);
}

function deleteEntry(namespace, type, entryIdPath, deleteUrl) {
    var confirmMessage = "";
    var name = getNamespacedElement(namespace, bookmarkPortletsData[namespace].entry_reference_name + entryIdPath).innerHTML;

    if (type == "bookmark") {
        confirmMessage = confirmMessage + bookmarkPortletsData[namespace].messages_delete_bookmark_prefix;
        confirmMessage = confirmMessage + name;
        confirmMessage = confirmMessage + bookmarkPortletsData[namespace].messages_delete_bookmark_suffix;
    }
    else {
        confirmMessage = confirmMessage + bookmarkPortletsData[namespace].messages_delete_folder_prefix;
        confirmMessage = confirmMessage + name;
        confirmMessage = confirmMessage + bookmarkPortletsData[namespace].messages_delete_folder_suffix;
    }

    var shouldDelete = confirm(confirmMessage);
    if (shouldDelete) {
        window.location = deleteUrl;
    }
}

/***** Internal Methods *****/
function getNamespacedElement(namespace, elementId) {
    return document.getElementById(namespace + elementId);
}
function getNamespacedElements(namespace, elementId) {
    return document.getElementsByName(namespace + elementId);
}

function resetForm(namespace, name) {
    var targetForm = getForm(namespace, name);
    targetForm.reset();
    return targetForm;
}
function getForm(namespace, name) {
    return document.forms[namespace + name];
}

function hideElement(namespace, elementId) {
    var targetElement = getNamespacedElement(namespace, elementId);
    targetElement.style.display = "none";
}
function showElement(namespace, elementId, displayType) {
    var targetElement = getNamespacedElement(namespace, elementId);
    targetElement.style.display = displayType;
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
function isElementHidden(namespace, elementId) {
    var targetElement = getNamespacedElement(namespace, elementId);
    return targetElement.style.display == "none";
}

function showForm(namespace, formName) {
    showForm(namespace, formName, null)
}
function showForm(namespace, formName, focusedInput) {
    showElementBlock(namespace, formName);

    var form = getForm(namespace, formName);
    if (!(typeof focusedInput == "undefined")) {
        form.elements[focusedInput].focus();
    }
    else if (form.elements.length > 0) {
        form.elements[0].focus();
    }
}

function setupFolderOptions(sourceSelect, targetSelect) {
    setupFolderOptions(sourceSelect, targetSelect, null, null);
}
function setupFolderOptions(sourceSelect, targetSelect, selectedOptionValue, excludedOptionValue) {
    targetSelect.length = 0;

    for (var index = 0; index < sourceSelect.options.length; index++) {
        var sourceOption = sourceSelect.options[index];

        if (typeof excludedOptionValue == "undefined" || sourceOption.value.indexOf(excludedOptionValue) != 0) {
            targetSelect.options[index] = new Option(sourceOption.text, sourceOption.value);
            targetSelect.options[index].className = sourceOption.className;

            if (!(typeof selectedOptionValue == "undefined") && targetSelect.options[index].value == selectedOptionValue) {
                targetSelect.options[index].selected = true;
            }
        }
    }
}

