import C from './constants'
import fetch from 'isomorphic-fetch'

export const performLogin = credentials => dispatch => {  
    fetch(`/perform_login?username=${encodeURIComponent(credentials.username)}&password=${encodeURIComponent(credentials.password)}`, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
    }).then(response => response)
        .then(loginResponse => {
        console.log(loginResponse.url)
        dispatch(logingIn(false))
        if (loginResponse.url.indexOf("login?error") !== -1) {
            let errorMessage = "Incorrect username or password!"
            dispatch(setAuthenticationErrorMessage(errorMessage))
        } else {
            dispatch(login(credentials.username))
            dispatch(hideLoginForm())
            dispatch(clearAuthenticationErrorMessage())
            window.location.href = loginResponse.url
        }
    }) 
}

export const login = username => {
    return {
        type: C.LOGIN,
        payload: username
    }
}

export const performLogout = () => dispatch => {
    fetch('/logout', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then(response => response)
        .then(logoutResponse => {
        dispatch(logout())
        dispatch(showLoginForm())
        dispatch(revokeAuthentication())
        window.location.href = logoutResponse.url
    })
}

export const logout = () => {
    return {
        type: C.LOGOUT
    }
}

export const showLoginForm = () => {
    console.log("show login form")
    return {
        type: C.LOGIN_FORM_VISIBILITY,
        payload: true
    }
}

export const hideLoginForm = () => {
    return {
        type: C.LOGIN_FORM_VISIBILITY,
        payload: false
    }
}

export const invokeAuthentication = () => {
    return {
        type: C.IS_AUTHENTICATED,
        payload: true
    }
}

export const revokeAuthentication = () => {
    return {
        type: C.IS_AUTHENTICATED,
        payload: false
    }
}

export const validateAuthentication = (route, router) => dispatch => {
    fetch('/principal/currentUser', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    }).then (response => response.json())
        .then(jsonResponse => {
        console.log(jsonResponse)
        if (jsonResponse.status === 200) {
           let username = jsonResponse.data.username;
           authenticateUser(route, router, username, dispatch)    
        } else {
           console.log("before clear authentication")
           clearAuthentication(route, router, dispatch)  
        }
    })    
}

export const authenticateUser = (route, router, username, dispatch) => {
    if (route.pathname !== "/") {
        router.push("/")
    } else {
        dispatch(login(username))
        dispatch(invokeAuthentication())
        dispatch(hideLoginForm())
    }
} 

export const clearAuthentication = (route, router, dispatch) => {
    console.log(route)
    if (route.pathname !== "/login") {
        router.push("/login")
    } else {
        dispatch(logout())
        dispatch(clearAuthenticationErrorMessage())
        dispatch(revokeAuthentication())
        dispatch(showLoginForm()) 
    }
}

export const setAuthenticationErrorMessage = msg => {
    return {
        type: C.AUTHENTICATION_ERROR_MESSAGE,
        payload: msg
    }
}

export const clearAuthenticationErrorMessage = () => {
    return {
        type: C.AUTHENTICATION_ERROR_MESSAGE,
        payload: ""
    }
}

export const logingIn = isLogingIn => {
    return {
        type: C.LOGING_IN,
        payload: isLogingIn
    }
}

export const fetchApplications = () => dispatch => {
    fetch('/applications/?page=1&size=10', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }        
    }).then(response => response.json())
        .then(jsonResponse => {
            console.log(jsonResponse)
            if (jsonResponse.status === 200) {
                dispatch(retrieveApplications(jsonResponse.data))
            }
    })
}

export const retrieveApplications = applications => {
    return {
        type: C.RETRIEVE_APPLICATIONS,
        payload: applications
    }
}

export const addApplicationRequest = application => dispatch => {
    if (!application.name) {
        dispatch(showErrorOnAddingApplication())
        return
    } 
    dispatch(addingApplication())
    fetch('/applications/', {
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
                dispatch(addApplication(jsonResponse.data))
                dispatch(hideAddApplicationForm())
                dispatch(successfullyAddApplication())
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
    fetch('/applications/', {
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

export const deleteApplicationRequest = entriesToDelete => dispatch => {
    console.log("request started.")
    dispatch(deletingApplication())
    fetch('/applications/deleteApplications', {
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
            dispatch(deleteApplication(entriesToDelete))
            dispatch(hideDeleteApplicationForm())
            dispatch(successfullyDeletedApplication())
            dispatch(clearSelectedApplicationEntries())
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