import C from '../constants/applicationConstants'
import fetch from 'isomorphic-fetch'

export const fetchApplications = (searchText, currentPage, pageSize) => dispatch => {
    console.log(currentPage)
    fetch(`/api/applications/searchByText?input=${encodeURIComponent(searchText)}&page=${encodeURIComponent(currentPage)}&size=${encodeURIComponent(pageSize)}`, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }        
    }).then(response => response.json())
        .then(jsonResponse => {
            console.log(jsonResponse)
            if (jsonResponse.status === 200) {
                console.log(currentPage + ": current page.")
                dispatch(retrieveApplications(jsonResponse.data))
                dispatch(setPaginationTotal(jsonResponse.total))
                dispatch(setPaginationPage(currentPage))
                
                if (jsonResponse.data.length === 0) {
                    let totalPages = Math.ceil(jsonResponse.total / pageSize)
                    if (totalPages > 0) {
                        dispatch(fetchApplications(searchText, totalPages, pageSize))
                    }
                }
            }
    })
}

export const retrieveApplications = applications => {
    return {
        type: C.RETRIEVE_APPLICATIONS,
        payload: applications
    }
}

export const addApplicationRequest = (application, searchText, currentPage, pageSize) => dispatch => {
    if (!application.name) {
        dispatch(showErrorOnAddingApplication())
        return
    } 
    dispatch(addingApplication())
    fetch('/api/applications/', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },        
        body: JSON.stringify(application)
    }).then(response => response.json()) 
        .then(jsonResponse => {
            console.log(jsonResponse)
            if (jsonResponse.status === 201) {
                //dispatch(addApplication(jsonResponse.data))
                dispatch(hideAddApplicationForm())
                dispatch(successfullyAddApplication())
                fetchApplications(searchText, currentPage, pageSize)
            }
        dispatch(completeAddingApplication())
    })
}

export const addApplication = application => {
    return {
        type: C.ADD_APPLICATION,
        payload: application
    }
}

export const addingApplication = () => {
    return {
        type: C.ADDING_NEW_APPLICATION,
        payload: true
    }
}

export const editingApplication = () => {
    return {
        type: C.EDITING_APPLICATION,
        payload: true
    }
}

export const deletingApplication = () => {
    return {
        type: C.DELETING_APPLICATION,
        payload: true
    }
}

export const completeAddingApplication = () => {
    return {
        type: C.ADDING_NEW_APPLICATION,
        payload: false
    }
}

export const completeEditingApplication = () => {
    return {
        type: C.EDITING_APPLICATION,
        payload: false
    }
}

export const successfullyAddApplication = () => {
    return {
        type: C.SUCCESSFULLY_ADDED_APPLICATION,
        payload: true
    }
}

export const successfullyEditApplication = () => {
    return {
        type: C.SUCCESSFULLY_EDITED_APPLICATION,
        payload: true
    }
}

export const resetSuccessfullyAddApplication = () => {
    return {
        type: C.SUCCESSFULLY_ADDED_APPLICATION,
        payload: false
    }
}

export const resetSuccessfullyEditApplication = () => {
    return {
        type: C.SUCCESSFULLY_EDITED_APPLICATION,
        payload: false
    }
}

export const resetSuccessfullyDeleteApplication = () => {
    return {
        type: C.SUCCESSFULLY_DELETED_APPLICATION,
        payload: false
    }
}

export const editApplicationRequest = application => dispatch => {
    if (!application.name) {
        dispatch(showErrorOnEditingApplication())
        return 
    }
    
    dispatch(editingApplication())
    fetch('/api/applications/', {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },        
        body: JSON.stringify(application)
    }).then(response => response.json())
        .then(jsonResponse => {
            if (jsonResponse.status === 200) {
                dispatch(editApplication(jsonResponse.data))
                dispatch(hideEditApplicationForm())
                dispatch(successfullyEditApplication())
            } 
            dispatch(completeEditingApplication())
    })
    
}

export const deleteApplicationRequest = (entriesToDelete, searchText, currentPage, pageSize) => dispatch => {
    console.log("request started.")
    dispatch(deletingApplication())
    fetch('/api/applications/deleteApplications', {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(entriesToDelete)
    }).then(response => {
        console.log("request completed.")
        console.log(response)
        if (response.status === 200) {
            // dispatch(deleteApplication(entriesToDelete))
            dispatch(hideDeleteApplicationForm())
            dispatch(successfullyDeletedApplication())
            dispatch(clearSelectedApplicationEntries())
            dispatch(fetchApplications(searchText, currentPage, pageSize))
        }
        dispatch(completeDeletingApplication())
    })
}

export const showDeleteApplicationForm = () => {
    return {
        type: C.DELETE_APPLICATION_FORM_VISIBILITY,
        payload: true
    }
}

export const hideDeleteApplicationForm = () => {
    return {
        type: C.DELETE_APPLICATION_FORM_VISIBILITY,
        payload: false
    }
}

export const successfullyDeletedApplication = () => {
    return {
        type: C.SUCCESSFULLY_DELETED_APPLICATION,
        payload: true
    }
}

export const completeDeletingApplication = () => {
    return {
        type: C.DELETING_APPLICATION,
        payload: false
    }
}

export const deleteApplication = entriesToDelete => {
    return {
        type: C.DELETE_APPLICATION,
        payload: entriesToDelete
    }
}

export const editApplication = application => {
    return {
        type: C.EDIT_APPLICATION,
        payload: application
    }
}

export const showAddApplicationForm = () => {
    return {
        type: C.ADD_APPLICATION_FORM_VISIBILITY,
        payload: true
    }
}

export const hideAddApplicationForm = () => {
    return {
        type: C.ADD_APPLICATION_FORM_VISIBILITY,
        payload: false
    }
}

export const showErrorOnAddingApplication = () => {
    return {
        type: C.HAS_ERROR_ON_ADDING_APPLICATION,
        payload: true
    }
}

export const hideErrorOnAddingApplication = () => {
    return {
        type: C.HAS_ERROR_ON_ADDING_APPLICATION,
        payload: false
    }
}

export const selectApplicationEntry = application => {
    return {
        type: C.SELECT_APPLICATION_ENTRY,
        payload: application
    }
}

export const unselectApplicationEntry = application => {
    return {
        type: C.UNSELECT_APPLICATION_ENTRY,
        payload: application.id
    }
}
    
export const showEditApplicationForm = () => {
    return {
        type: C.EDIT_APPLICATION_FORM_VISIBILITY,
        payload: true
    }
}

export const hideEditApplicationForm = () => {
    return {
        type: C.EDIT_APPLICATION_FORM_VISIBILITY,
        payload: false
    }
}

export const showErrorOnEditingApplication = () => {
    return {
        type: C.HAS_ERROR_ON_EDITING_APPLICATION,
        payload: true
    }
}

export const hideErrorOnEditingApplication = () => {
    return {
        type: C.HAS_ERROR_ON_EDITING_APPLICATION,
        payload: false
    }
}

export const showErrorOnDeletingApplication = () => {
    return {
        type: C.HAS_ERROR_ON_DELETING_APPLICATION,
        payload: true
    }
}

export const hideErrorOnDeletingApplication = () => {
    return {
        type: C.HAS_ERROR_ON_DELETING_APPLICATION,
        payload: false
    }
}

export const clearSelectedApplicationEntries = () => {
    return {
        type: C.CLEAR_APPLICATION_ENTRY
    }
}

export const clearSelectedApplicationEntriesByPage = page => {
    return {
        type: C.CLEAR_APPLICATION_ENTRY_BY_PAGE,
        payload: page
    }
}

// pagination

export const setPaginationTotal = total => {
    return {
        type: C.PAGINATION_TOTAL,
        payload: total
    }
}

export const setPaginationPageRequest = (searchText, page, pageSize) => dispatch => {
    dispatch(fetchApplications(searchText, page, pageSize))
}

export const setPaginationPage = page => {
    return {
        type: C.PAGINATION_PAGE,
        payload: page
    }
}

export const setApplicationSearchText = searchText => {
    return {
        type: C.SET_APPLICATION_SEARCH_TEXT,
        payload: searchText
    }
}
