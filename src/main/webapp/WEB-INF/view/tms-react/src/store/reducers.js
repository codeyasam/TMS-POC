import C from '../constants.js'
import { combineReducers } from 'redux'

export const login = (state=null, action) => 
    (action.type === C.LOGIN) ?
      action.payload :
      state

export default combineReducers({
    login
})