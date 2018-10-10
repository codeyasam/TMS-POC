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
        case C.EDIT_APPLICATION:
            const updatedApplication = action.payload
            return state.filter(application => application.id !== updatedApplication.id ? application : updatedApplication)
        case C.DELETE_APPLICATION:
            const entriesToDelete = action.payload
            const entriesIdList = entriesToDelete.map(entry => {
                return entry.id
            })
            return state.filter(entry => entriesIdList.indexOf(entry.id) === -1)
        default:
            return state
    }
}

export const selectedApplicationEntries = (state=[], action) => {
    switch(action.type) {
        case C.RETRIEVE_SELECTED_APPLICATIONS:
            return action.payload
        case C.SELECT_APPLICATION_ENTRY: 
            return [...state, action.payload]
        case C.UNSELECT_APPLICATION_ENTRY: 
            return state.filter(application => application.id !== action.payload)
        case C.CLEAR_APPLICATION_ENTRY:
            return [] 
        default: 
            return state
    }
}

// Add Application

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
        
// Edit Application        
        
export const editApplicationFormVisibility = (state=false, action) => 
    (action.type === C.EDIT_APPLICATION_FORM_VISIBILITY) ? 
        action.payload :
        state
        
export const editingApplication = (state=false, action) => 
    (action.type === C.EDITING_APPLICATION) ?
        action.payload :
        state

export const successfullyEditedApplication = (state=false, action) => 
    (action.type === C.SUCCESSFULLY_EDITED_APPLICATION) ?
        action.payload :
        state

export const hasErrorOnEditingApplication = (state=false, action) => 
    (action.type === C.HAS_ERROR_ON_EDITING_APPLICATION) ?
        action.payload :
        state

// delete Application
export const deleteApplicationFormVisibility = (state=false, action) => 
    (action.type === C.DELETE_APPLICATION_FORM_VISIBILITY) ?
        action.payload :
        state
        
export const deletingApplication = (state=false, action) =>
    (action.type === C.DELETING_APPLICATION) ?
        action.payload :
        state
        
export const successfullyDeletedApplication = (state=false, action) => 
    (action.type === C.SUCCESSFULLY_DELETED_APPLICATION) ?
        action.payload :
        state
        
export const hasErrorOnDeletingApplication = (state=false, action) =>
    (action.type === C.HAS_ERROR_ON_DELETING_APPLICATION) ?
        action.payload :
        state
        
// pagination
export const paginationTotal = (state=0, action) => 
    (action.type === C.PAGINATION_TOTAL) ?
        action.payload :
        state

export const paginationPage = (state=1, action) => {
    switch(action.type) {
        case C.PAGINATION_PAGE: 
            return action.payload
        case C.PAGINATION_NEXT_PAGE:
            return state + 1
        case C.PAGINATION_PREVIOUS_PAGE:
            return state - 1
        default:
            return state
    }
}
        
export const paginationSize = (state=5, action) =>
    (action.type === C.PAGINATION_SIZE) ?
        action.payload :
        state
        
export const paginationNavSize = (state=5, action) => 
    (action.type === C.PAGINATION_NAV_SIZE) ?
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
        }),
        editApplication: combineReducers({
            editApplicationFormVisibility,
            editingApplication,
            successfullyEditedApplication,
            hasErrorOnEditingApplication
        }),
        deleteApplication: combineReducers({
            deleteApplicationFormVisibility,
            deletingApplication,
            successfullyDeletedApplication, 
            hasErrorOnDeletingApplication
        }),
        pagination: combineReducers({
            paginationTotal,
            paginationPage,
            paginationSize,
            paginationNavSize
        })
    })
})