import C from '../constants.js'
import { combineReducers } from 'redux'

export const username = (state=null, action) => {
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

export default combineReducers({
    auth: combineReducers({
        username,
        loginFormVisibility,
        isAuthenticated
    })
})