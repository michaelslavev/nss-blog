const initState = {
    loggedIn: sessionStorage.getItem("loggedin") || false,
    user: JSON.parse(sessionStorage.getItem("user")) || null
}

const rootReducer = (state = initState, action) => {

    if(action.type == 'LOGGING_IN'){
        sessionStorage.setItem("user",JSON.stringify(action.user));
        sessionStorage.setItem("loggedin", true);
        return{
            ...state,
            loggedIn: true,
            user:action.user
        }
    } else if(action.type == 'LOGOUT'){
        sessionStorage.clear();
        return {
            ...state,
            loggedIn:false,
            user:null
        }
    }

    return state;
}

export default rootReducer;