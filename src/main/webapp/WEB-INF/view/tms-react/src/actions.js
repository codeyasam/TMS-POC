import C from './constants'
import fetch from 'isomorphic-fetch'

export const login = credentials => dispatch => {
    
    fetch('/perform_login', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json' 
        },
        body: JSON.stringify(credentials)
    }).then(response => response.json())
      .then(jsonResponse => {
        console.log(jsonResponse)
        dispatch({
            type: C.LOGIN,
            payload: credentials
        })        
    })
    

    
}