import C from '../constants/authConstants'

export const username = (state="", action) => {
    switch(action.type) {
        case C.LOGIN:
            return action.payload
        case C.LOGOUT:
            return ""
        default:
            return state
    }  
}

export const loginFormVisibility = (state=false, action) => 
    (action.type === C.LOGIN_FORM_VISIBILITY) ? 
      action.payload :
      state

export const isAuthenticated = (state=false, action) => 
    (action.type === C.IS_AUTHENTICATED) ?
      action.payload :
      state

export const errorMessage = (state="", action) => 
    (action.type === C.AUTHENTICATION_ERROR_MESSAGE) ?
      action.payload :
      state

export const logingIn = (state=false, action) => 
    (action.type === C.LOGING_IN) ?
      action.payload :
      state
