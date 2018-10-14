import { username, loginFormVisibility, isAuthenticated, errorMessage, logingIn } from './authreducers'
import { applicationList, selectedApplicationEntries, addApplicationFormVisibility, addingNewApplication, successfullyAddedApplication, hasErrorOnAddingApplication, editApplicationFormVisibility, editingApplication, successfullyEditedApplication, hasErrorOnEditingApplication, deleteApplicationFormVisibility, deletingApplication, successfullyDeletedApplication, hasErrorOnDeletingApplication, fetchingApplication, applicationSearchText, paginationTotal, paginationPage, paginationSize, paginationNavSize } from './applicationreducers'
import { moduleList, moduleSearchText } from './modulereducers'
import { combineReducers } from 'redux'

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
        retrieveApplication: combineReducers({
            fetchingApplication,
            applicationSearchText
        }),
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
    }),
    module: combineReducers({
        moduleList,
        retrieveModule: combineReducers({
            moduleSearchText
        })
    })
})