import C from './constants'
import fetch from 'isomorphic-fetch'

export const login = credentials => dispatch => {  
    fetch(`/perform_login?username=${encodeURIComponent(credentials.username)}&password=${encodeURIComponent(credentials.password)}`, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
    }).then(response => response)
        .then(loginResponse => {
        console.log(loginResponse.url)
        if (loginResponse.url.indexOf("login?error") !== -1) {
            console.log("login error");
        } else {
            dispatch({
                type: C.LOGIN,
                payload: credentials.username
            })
            dispatch(hideLoginForm())
            window.location.href = loginResponse.url
        }
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
    fetch('http://localhost:9080/principal/currentUser', {
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
        dispatch({ type: C.LOGIN, payload: username })
        dispatch(invokeAuthentication())
        dispatch(hideLoginForm())
    }
} 

export const clearAuthentication = (route, router, dispatch) => {
    console.log(route)
    if (route.pathname !== "/login") {
        router.push("/login")
    } else {
        dispatch(revokeAuthentication())
        dispatch(showLoginForm())        
    }
}