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

export const addApplicationRequest = (applicationName) => dispatch => {
    if (!applicationName) {
        dispatch(showErrorOnAddingApplication())
        return
    } 
    
    let application = {}
    application.name = applicationName
    fetch('/applications/', {
        method: 'POST',
        body: application
    }).then(response => response.json()) 
        .then(jsonResponse => {
            console.log(jsonResponse)
            if (jsonResponse.status === 200) {
                dispatch(addApplication(jsonResponse.data))
            }
    })
}

export const addApplication = application => {
    return {
        type: C.ADD_APPLICATION,
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
    console.log("hide application form")
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