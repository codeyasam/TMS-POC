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
        console.log(loginResponse)
    }) 
//      .then(jsonResponse => {
//        console.log(jsonResponse)
//        if (jsonResponse.status === 200) {
//            dispatch({
//                type: C.LOGIN,
//                payload: credentials.username
//            })
//        }
//    })
    
//    fetch('/perform_login', {
//        method: 'POST',
//        headers: {
//            'Accept': 'application/json',
//            'Content-Type': 'application/json' 
//        },
//        body: JSON.stringify(credentials)
//    }).then(response => response.json())
//      .then(jsonResponse => {
//        console.log(jsonResponse)
//        console.log("after the inner fetch")
//        
////        dispatch({
////            type: C.LOGIN,
////            payload: credentials.username
////        })        
////        
//        return fetch('/principal/username')
//    }).then(response => response.json())
//      .then(jsonResponse => console.log(jsonResponse))
    

    
}