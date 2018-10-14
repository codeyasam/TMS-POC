import C from '../constants/moduleConstants'


export const moduleList = (state=[], action) => 
    (action.type === C.RETRIEVE_MODULES) ?
        action.payload :
        state

export const moduleSearchText = (state="", action) => 
    (action.type === C.SET_MODULE_SEARCH_TEXT) ?
        action.payload :
        state