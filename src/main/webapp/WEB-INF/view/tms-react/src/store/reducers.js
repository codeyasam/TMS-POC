import C from '../constants.js'
import { combineReducers } from 'redux'

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

export const applicationList = (state=[], action) => {
    switch(action.type) {
        case C.RETRIEVE_APPLICATIONS:
            return action.payload
        case C.ADD_APPLICATION:
            return [...state, action.payload]
        default:
            return state
    }
}

export const selectedApplicationEntries = (state=[], action) => {
    switch(action.type) {
        case C.RETRIEVE_SELECTED_APPLICATIONS:
            return action.payload
        default: 
            return state
    }
}

export const addApplicationFormVisibility = (state=false, action) => 
    (action.type === C.ADD_APPLICATION_FORM_VISIBILITY) ?
        action.payload :
        state

export const addingNewApplication = (state=false, action) => 
    (action.type === C.ADDING_NEW_APPLICATION) ?
        action.payload :
        state

export const successfullyAddedApplication = (state=false, action) => 
    (action.type === C.SUCCESSFULLY_ADDED_APPLICATION) ?
        action.payload :
        state

export const hasErrorOnAddingApplication = (state=false, action) => 
    (action.type === C.HAS_ERROR_ON_ADDING_APPLICATION) ?
        action.payload :
        state

export default combineReducers({
    auth: combineReducers({
        username,
        loginFormVisibility,
        isAuthenticated,
        errorMessage,
        logingIn
    }),
    application: combineReducers({
        applicationList,
        selectedApplicationEntries,
        addApplication: combineReducers({
            addApplicationFormVisibility,
            addingNewApplication,
            successfullyAddedApplication,
            hasErrorOnAddingApplication
        })
    })
})