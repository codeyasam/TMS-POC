import C from '../constants/moduleConstants'
import fetch from 'isomorphic-fetch'

export const fetchModules = (searchText, currentPage, pageSize) => async(dispatch) => {
    let response = await fetch(`/api/modules/searchByText?input=${encodeURIComponent(searchText)}&page=${encodeURIComponent(currentPage)}&size=${encodeURIComponent(pageSize)}`)
    let jsonResponse = await response.json()
    if (jsonResponse.status === 200) {
        dispatch(retrieveModules(jsonResponse.data))
    }
}

export const retrieveModules = modules => {
    return {
        type: C.RETRIEVE_MODULES,
        payload: modules
    }
}